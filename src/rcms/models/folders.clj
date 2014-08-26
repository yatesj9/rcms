(ns rcms.models.folders
  (:require [me.raynes.fs :as fs]))

(defn create-directory
  [name]
  (if (fs/directory? name)
    {:error "Directory already exists"}
   (fs/mkdir name)))

(defn remove-directory
  [name]
  (if (fs/directory? name)
   (fs/delete-dir name)
   {:error "Directory does not exist"}))

(defn rename-directory
  [name new-name]
  (if (fs/directory? name)
    (do
     (fs/copy-dir name new-name)
     (fs/delete-dir name))
    {:error "Directory does not exist"}))
