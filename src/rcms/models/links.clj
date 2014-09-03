(ns rcms.models.links
  (:require [rcms.db :as db]
            [clojure.java.jdbc :as sql]
            [rcms.config :refer [get-settings]]
            [rcms.common :refer [kebab->snake]]))

(defn get-links
  "Return all links in db"
  []
  (sql/query (db/get-connection) ["select * from links"]))

(defn get-link
  "Return matching link name"
  [link-name]
  (sql/query (db/get-connection) ["select * from links where name = ?" link-name]))

(defn save-link
  "Takes link-map, saves to DB or updates by Name"
  [link-map]
  (let [link (-> link-map
                 (kebab->snake))
        link-record (get-link (:name link-map))]
    (if (empty? link-record)
      (sql/insert! (db/get-connection) :links link)
      (sql/update! (db/get-connection) :links
                                       link
                                       ["name = ? " (:name link-map)]))))

(defn remove-link
  "Takes link-name, removes link from DB"
  [link-name]
  (sql/delete! (db/get-connection) :links ["name = ? " link-name])
  {:msg "Link removed"})
