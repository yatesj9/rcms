(ns rcms.tests.helper
  (:require [rcms.db :as db]
            [rcms.config :refer [set-mode!
                                 get-settings]]))

; --- Init Test DB connection--------------------------------------------------
(defonce connection (atom nil))

(defn initialize-test-connection
  []
  (do
    (set-mode! :test)
    (when (nil? @connection)
      (db/set-connection!
        (reset! connection
           (db/pooled-datasource (get-settings :database :connection)))))
  @connection))

; --- Folders Table-------------------------------------------------------------

(def folder-schema
  {:table :folders
    :fields ["id int not null auto_increment,
              name varchar(50) not null,
              resource varchar(50) not null"]})

(def folder-data
  [{:id nil
    :name "People"
    :resource "resources/files/people"}
   {:id nil
    :name "Smoke Signals"
    :resource "resources/files/smokesignals"}])

; --- Files Table--------------------------------------------------------------

(def file-schema
  {:table :files
   :fields ["id int not null auto_increment,
             file_id varchar(50) not null
             file_name varchar(50) not null
             description varchar(50) not null
             created_at bigint not null"]})

(def file-data
  [{:id nil
    :file_id "GUID"
    :file_name "my_mash.jpg"
    :description "A bunch of cool pictures mached together"
    :created_at (System/currentTimeMillis)}
   {:id nil
    :file_id "GUID"
    :file_name "passwords.txt"
    :description "Super secret passwords"
    :created_at (System/currentTimeMillis)}])
