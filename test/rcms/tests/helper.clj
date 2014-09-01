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
              name varchar(50) not null"]})

(def folder-data
  [{:id nil
    :name "People"}
   {:id nil
    :name "Smoke Signals"}])

; --- Files Table--------------------------------------------------------------

(def file-schema
  {:table :files
   :fields ["id int not null auto_increment,
             folder_name varchar(50) not null,
             file_name varchar(50) not null,
             description varchar(50) not null,
             updated_at bigint not null"]})

(def file-data
  [{:id nil
    :folder_name "People"
    :file_name "my_mash.jpg"
    :description "A bunch of cool pictures mached together"
    :updated_at (System/currentTimeMillis)}
   {:id nil
    :folder_name "Smoke Signals"
    :file_name "passwords.txt"
    :description "Super secret passwords"
    :updated_at (System/currentTimeMillis)}
   {:id nil
    :folder_name "Smoke Signals"
    :file_name "secret.doc"
    :description "Super secret documents"
    :updated_at (System/currentTimeMillis)}])
