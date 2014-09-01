(ns rcms.models.files
  (:require [rcms.db :as db]
            [clojure.java.jdbc :as sql]
            [rcms.config :refer [get-settings]]
            [rcms.common :refer [kebab->snake]]))

(defn get-files
  [folder-name]
  (sql/query (db/get-connection) ["select * from files where folder_name = ?" folder-name]))

(defn get-file
  [file-name]
  (sql/query (db/get-connection) ["select * from files where file_name = ? " file-name]))

(defn save-file
  "Takes a file-map, saves to DB {:id nil :folder-name :file-name :description"
  [file-map]
  (let [file (-> file-map
                 (assoc :updated-at (System/currentTimeMillis))
                 (kebab->snake))
        file-record (get-file (:file-name file-map))]
    (if (empty? file-record)
        (sql/insert! (db/get-connection) :files file)
        (sql/update! (db/get-connection) :files
                                         file
                                         ["file_name = ?" (:file-name file-map)]))))
