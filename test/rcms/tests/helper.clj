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

(defn build-req
  [request]
  (let [client-token {:client-api-token (get-settings :token :client-api-token)}]
    (assoc request :headers (merge (:headers request) client-token))))

; --- Folders Table-------------------------------------------------------------

(def folder-schema
  {:table :folders
    :fields ["id int not null identity(1,1),
             name varchar(50) not null,
             folder_order int,
             hidden bit"]})

(def folder-data
  [{:name "People"
    :folder_order 2
    :hidden false}
   {:name "Smoke Signals"
    :folder_order 1
    :hidden false}])

; --- Files Table---------------------------------------------------------------

(def file-schema
  {:table :files
   :fields ["id int not null identity(1,1),
             folder_name varchar(50) not null,
             file_name varchar(50) not null,
             tag varchar(50),
             updated_at bigint not null"]})

(def file-data
  [{:folder_name "People"
    :file_name "my_mash.jpg"
    :tag nil
    :updated_at (System/currentTimeMillis)}
   {:folder_name "Smoke Signals"
    :file_name "passwords.txt"
    :tag "July 2014"
    :updated_at (System/currentTimeMillis)}
   {:folder_name "Smoke Signals"
    :file_name "secret.doc"
    :tag "July 2014"
    :updated_at (System/currentTimeMillis)}])

; --- Tags Table---------------------------------------------------------------

(def tag-schema
  {:table :tags
   :fields ["id int not null identity(1,1),
             name varchar(50) not null,
             folder_name varchar(50) not null"]})

(def tag-data
  [{:name "July 2014"
    :folder_name "Smoke Signals"}
   {:name "Benefits"
    :folder_name "People"}
   {:name "Policies"
    :folder_name "People"}])

; --- Links Table---------------------------------------------------------------

(def link-schema
  {:table :links
   :fields ["id int not null identity(1,1),
             name varchar(50) not null,
             href varchar(100) not null,
             link_order int"]})

(def link-data
  [{:name "Google"
    :href "http://google.com"
    :link_order 2}
   {:name "rcms"
    :href "https://github.com/yatesj9/rcms"
    :link_order 3}
   {:name "GameSpot"
    :href "http://www.gamespot.com"
    :link_order 1}])

; --- Annoucnements Table-------------------------------------------------------

(def announcement-schema
  {:table :announcements
   :fields ["id int not null identity(1,1),
            title varchar(50) not null,
            body varchar(max) not null,
            expires_at bigint,
            created_at bigint"]})

(def announcement-data
  [{:title "Welcome"
    :body "Welcome to RCMS this is a body <h1>TITLE</h1>"
    :expires_at (+ 234234123123 (System/currentTimeMillis))
    :created_at (System/currentTimeMillis)}
   {:title "Another"
    :body "This is another announcement"
    :expires_at (+ 1934932342342 (System/currentTimeMillis))
    :created_at (System/currentTimeMillis)}
   {:title "Expired Announcement"
    :body "not important"
    :expires_at (- (System/currentTimeMillis) 30493 )
    :created_at (System/currentTimeMillis)}])
