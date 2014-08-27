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

; --- Folders DB---------------------------------------------------------------

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
