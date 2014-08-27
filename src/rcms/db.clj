(ns rcms.db
  (:require [clojure.java.jdbc :as sql])
  (:import (com.jolbox.bonecp BoneCPConfig BoneCPDataSource)))

(def default-connection (atom nil))

(defn set-connection!
  [connection]
  (reset! default-connection connection))

(defn get-connection
  []
  @default-connection)

; --- Bone Connection-----------------------------------------------------------

(defn pooled-datasource
  "Return a map with 1 key :datasource associated to a connection
  object that can be used by clojure.java.jdbc/with-connection"
  [{:keys [url min-connections max-connections partitions log-statements?
           username password class-for-name]
     :or {min-connections 1 max-connections 1 log-statements? false partitions 1}}]
    (when class-for-name (Class/forName class-for-name))
    (let [config (doto (BoneCPConfig.)
                       (.setJdbcUrl url)
                       (.setMinConnectionsPerPartition min-connections)
                       (.setMaxConnectionsPerPartition max-connections)
                       (.setLogStatementsEnabled log-statements?)
                       (.setPartitionCount partitions))]
      (when username (.setUsername config username))
      (when password (.setPassword config password))
      {:datasource (BoneCPDataSource. config)}))

; --- Table Functions ---------------------------------------------------------

(defn create-table!
  [connection table params]
  (sql/db-do-commands connection
    (sql/create-table-ddl table params)))

(defn drop-table!
  [connection table]
  (try
    (sql/db-do-commands connection
    (sql/drop-table-ddl table))
    (catch Exception exc)))

(defn populate
  [connection table rows]
  (doseq [row rows]
   (sql/insert! connection table row)))
