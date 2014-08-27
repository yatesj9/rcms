(ns rcms.models.folders
  (:require [me.raynes.fs :as fs]
            [rcms.db :as db]
            [clojure.java.jdbc :as sql]))

; --- Database folder manipulation---------------------------------------------

(defn get-folders
  "Return entire folder list from DB"
  []
  (sql/query (db/get-connection) ["select * from folders"]))

(defn get-folder
  [name]
  (sql/query (db/get-connection) ["select * from folders where name = ?" name]))

(defn add-folder
  "Add single folder to DB, takes a map {:id nil, :name ###, :resource ###"
  [folder-map]
  (let [folder-record (get-folder (:name folder-map))]
    (when (empty? folder-record)
     (sql/insert! (db/get-connection) :folders folder-map))))

(defn remove-folder
  "Remove folder record from DB, takes folder-id"
  [folder-id]
  (sql/delete! (db/get-connection) :folders ["id = ?" folder-id]))

(defn rename-folder
  [folder-name new-name]
  (sql/update! (db/get-connection) :folders
               {:name new-name}
               ["name = ?" folder-name]))

; --- Direct folder manipulation-----------------------------------------------

(defn get-directories
  "Returns list of directories on disk"
  []
  (let [dir-seq (fs/iterate-dir "resources/files")]
   (map #(str %) (get (first dir-seq) 1))))

(defn create-directory
  "Creates folder as path, ie resources/files/folder/##"
  [name]
  (if (fs/directory? name)
    {:error "Directory already exists"}
    (fs/mkdir name)))

(defn remove-directory
  "Deletes folder, ie resources/files/folder/##"
  [name]
  (if (fs/directory? name)
   (fs/delete-dir name)
   {:error "Directory does not exist"}))

(defn rename-directory
  "Takes current folder path, renames to new path, ie resources/files/folder/##
      resources/files/folder/##_new"
  [name new-name]
  (if (fs/directory? name)
    (do
     (fs/copy-dir name new-name)
     (fs/delete-dir name))
    {:error "Directory does not exist"}))
