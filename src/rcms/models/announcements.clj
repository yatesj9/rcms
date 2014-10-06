(ns rcms.models.announcements
  (:require [rcms.db :as db]
            [clojure.java.jdbc :as sql]
            [rcms.config :refer [get-settings]]
            [rcms.common :refer [kebab->snake]]))

(defn get-all-announcements
  "Return all announcements in DB"
  []
  (sql/query (db/get-connection) ["select * from announcements"]))

(defn get-all-valid-announcements
  "Return all annoucements that are not expired"
  []
  (sql/query (db/get-connection) ["select * from announcements where expires_at >= ?"
                                   (System/currentTimeMillis)]))

(defn get-announcement
  "Return single record with matching title"
  [title]
  (sql/query (db/get-connection) ["select * from announcements where title = ?"
                                  title]))

(defn save-announcement
  "Takes a map {:title 'title' :body 'body' :expires_at 'date'} saves to DB or updates"
  [ann-map]
  (let [ann (-> ann-map
                (assoc :created_at (System/currentTimeMillis))
                (kebab->snake))
        ann-record (get-announcement (:title ann-map))]
    (if (empty? ann-record)
      (sql/insert! (db/get-connection) :announcements ann)
      (sql/update! (db/get-connection) :announcements
                                       ann
                                       ["title = ?" (:title ann-map)]))))

(defn remove-announcement
  "Remove announcement from DB"
  [title]
  (sql/delete! (db/get-connection) :announcements ["title =? " title])
  {:msg "Announcement removed"})
