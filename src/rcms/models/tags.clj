(ns rcms.models.tags
  (:require [rcms.db :as db]
            [clojure.java.jdbc :as sql]
            [rcms.config :refer [get-settings]]
            [rcms.common :refer [kebab->snake]]))

(defn get-all-tags
  "Return all tags in DB"
  []
  (sql/query (db/get-connection) ["select * from tags"]))

(defn get-folder-tags
  "REturn all tags from specific folder"
  [folder-name]
  (sql/query (db/get-connection) ["select * from tags where folder_name = ?" folder-name]))

(defn get-folder-tag-name
  "Takes tag-map {:folder-name :name} returns it"
  [tag-map]
  (sql/query (db/get-connection) ["select * from tags where folder_name = ? and name = ?"
                                  (:folder-name tag-map) (:name tag-map)]))

(defn save-tag
  "Takes a map {:name '###' :folder-name '###'} saves to DB or updates record"
  [tag-map]
  (let [tag (-> tag-map
                (kebab->snake))
        tag-record (get-folder-tag-name tag-map)]
    (if (empty? tag-record)
      (sql/insert! (db/get-connection) :tags tag)
      (sql/update! (db/get-connection) :tags
                                       tag
                                       ["name = ?" (:name tag-map)]))))

(defn remove-tag
  [tag-name]
  (sql/delete! (db/get-connection) :tags ["name = ?" tag-name])
  {:msg "Tag removed"})
