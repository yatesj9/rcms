(ns rcms.tests.helper
  (:require [rcms.db :as db]
            [rcms.config :refer [set-mode!
                                 get-settings]]))

; --- Init Test DB connection---------------------------------------------------
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
              name varchar(50) not null"]})

(def folder-data
  [{:id nil
    :name "People"}
   {:id nil
    :name "Smoke Signals"}])

; --- Files Table---------------------------------------------------------------

(def file-schema
  {:table :files
   :fields ["id int not null auto_increment,
             folder_name varchar(50) not null,
             file_name varchar(50) not null,
             description varchar(50),
             tag varchar(50),
             updated_at bigint not null"]})

(def file-data
  [{:id nil
    :folder_name "People"
    :file_name "my_mash.jpg"
    :description "A bunch of cool pictures mached together"
    :tag nil
    :updated_at (System/currentTimeMillis)}
   {:id nil
    :folder_name "Smoke Signals"
    :file_name "passwords.txt"
    :description "Super secret passwords"
    :tag "July 2014"
    :updated_at (System/currentTimeMillis)}
   {:id nil
    :folder_name "Smoke Signals"
    :file_name "secret.doc"
    :description "Super secret documents"
    :tag "July 2014"
    :updated_at (System/currentTimeMillis)}])

; --- Tags Table---------------------------------------------------------------

(def tag-schema
  {:table :tags
   :fields ["id int not null auto_increment,
             name varchar(50) not null,
             folder_name varchar(50) not null"]})

(def tag-data
  [{:id nil
    :name "July 2014"
    :folder_name "Smoke Signals"}
   {:id nil
    :name "Benefits"
    :folder_name "People"}
   {:id nil
    :name "Policies"
    :folder_name "People"}])

; --- Links Table---------------------------------------------------------------

(def link-schema
  {:table :links
   :fields ["id int not null auto_increment,
             name varchar(50) not null,
             href varchar(100) not null"]})

(def link-data
  [{:id nil
    :name "Google"
    :href "http://google.com"}
   {:id nil
    :name "rcms"
    :href "https://github.com/yatesj9/rcms"}
   {:id nil
    :name "GameSpot"
    :href "http://www.gamespot.com"}])
