(ns rcms.models.folders
  (:require [me.raynes.fs :as fs]))

; --- Database folder manipulation---------------------------------------------


; --- Direct folder manipulation-----------------------------------------------
(defn get-directories
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
