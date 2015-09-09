(ns rcms.models.files
  (:require [rcms.db :as db]
            [clojure.java.jdbc :as sql]
            [rcms.config :refer [get-settings]]
            [rcms.common :refer [kebab->snake]]
            [me.raynes.fs :as fs]
            [fivetonine.collage.util :as util]
            [fivetonine.collage.core :refer :all]))

(defn get-all-files
  "Return vector of all files in db"
  []
  (sql/query (db/get-connection) ["select * from files"]))

(defn get-files
  "Return all files in specific folder"
  [folder-name]
  (sql/query (db/get-connection) ["select * from files where folder_name = ?" folder-name]))

(defn get-file
  "Return specific file"
  [file-name folder-name]
  (sql/query (db/get-connection) ["select * from files where file_name = ?
                                  AND folder_name = ? " file-name folder-name]))

(defn file-exists?
  "Takes file-map, checks if file exists, matches name and folder"
  [{:keys [file-name folder-name]:as file-map}]
  (let [check-file (get-file file-name folder-name)]
    (and
     (= file-name (:file_name (first check-file)))
     (= folder-name (:folder_name (first check-file))))))

(defn save-file
  "Takes a file-map, saves to DB {:folder-name '##' :file-name '##' :updated-at '###}"
  [file-map]
  (let [file (-> file-map
                 (assoc :updated-at (System/currentTimeMillis))
                 (kebab->snake))
        file-record (file-exists? file-map)]
    (if-not file-record
        (sql/insert! (db/get-connection) :files file)
        {:msg "File exists"})))

(defn remove-file
  "Removes file from DB"
  [name]
  (sql/delete! (db/get-connection) :files ["file_name = ?" name])
  {:msg "File removed"})

(defn delete-file
  "Deletes file from filesystem, deletes thumb_ if exist for images"
  [folder name]
  (when (fs/file? (str (get-settings :resource :path) folder "/" name))
    (do (fs/delete (str (get-settings :resource :path) folder "/" name))
      (try
        (fs/delete (str (get-settings :resource :path) folder "/thumb_" name))))))

(def valid-images
    ["image/jpeg" "image/png"])

(defn image?
  [content-type]
  (> (.indexOf valid-images content-type) -1))

(defn- resize-image
  [folder image]
  (let [path (str (get-settings :resource :path) folder "/")]
   (with-image (str path image)
              (resize :width (get-settings :image :size))
              (util/save (str path image)))))

(defn- create-thumbnail
  [folder image]
  (let [path (str (get-settings :resource :path) folder"/")]
   (with-image (str path image)
              (resize :width (get-settings :image :thumb-size))
              (util/save (str path "thumb_" image)))))

(defn process-image
  [folder image]
  (.start (Thread.
   (do
    (resize-image folder image)
    (create-thumbnail folder image)))))

