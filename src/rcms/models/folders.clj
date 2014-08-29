(ns rcms.models.folders
  (:require [me.raynes.fs :as fs]
            [rcms.db :as db]
            [clojure.java.jdbc :as sql]
            [rcms.config :refer [get-settings]]))

; --- Database folder manipulation---------------------------------------------

(defn get-folders
  "Return entire folder list from DB"
  []
  (sql/query (db/get-connection) ["select * from folders"]))

(defn get-folder
  "Return single folder record from DB"
  [name]
  (sql/query (db/get-connection) ["select * from folders where name = ?" name]))

(defn add-folder
  "Add single folder to DB, takes a map {:name ###, :folder ###}"
  [folder-map]
  (let [folder-record (get-folder (:name folder-map))]
    (when (empty? folder-record)
     (sql/insert! (db/get-connection) :folders folder-map))))

(defn remove-folder
  "Remove folder record from DB, takes folder-id"
  [id]
  (sql/delete! (db/get-connection) :folders ["id = ?" id]))

(defn rename-folder
  "Takes current folder name, new name"
  [id new-name]
  (sql/update! (db/get-connection) :folders
               {:name new-name}
               ["id = ?" id]))

; --- Direct folder manipulation-----------------------------------------------

(defn get-directories
  "Returns list of directories on disk"
  []
  (let [dir-seq (fs/iterate-dir (str (get-settings :resource :path)))]
   (map #(str %) (get (first dir-seq) 1))))

(defn create-directory
  "Creates folder path in config"
  [name]
  (let [folder (str (get-settings :resource :path) name)]
   (if (fs/directory? folder)
    {:error "Directory already exists"}
    (fs/mkdir folder))))

(defn remove-directory
  "Deletes folder, at path in config"
  [name]
  (let [resource-path (get-settings :resource :path)]
   (if (fs/directory? (str resource-path name))
   (fs/delete-dir (str resource-path name))
   {:error "Directory does not exist"})))

(defn rename-directory
  "Takes map {:current-name ## :new-name ##}"
  [name-map]
  (let [resource-path "resources/files/"]
   (if (fs/directory? (str resource-path (:current-name name)))
    (do
     (fs/copy-dir (str resource-path (:current-name name-map))
                  (str resource-path (:new-name name-map)))
     (fs/delete-dir (str resource-path (:current-name name-map))))
    {:error "Directory does not exist"})))
