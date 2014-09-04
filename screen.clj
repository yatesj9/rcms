;Start rcms server onport 8080
(require '[rcms.repl :as rpl])
(rpl/start-server :test)

(rpl/stop-server)

(require '[rcms.routes.folders :reload true])

;Tests-------------------------------------------------------------------------

(require '[rcms.models.folders :reload true]
         '[rcms.tests.models.folders-tests :reload true])

;------------------------------------------------------------------------------
(require '[me.raynes.fs :as fs])

(fs/delete "resources/files/IT/chips.jpeg")

(def get-dir
     (fs/iterate-dir "resources/files"))

(def dir-names
 (get (first (fs/iterate-dir "resources/files")) 1))

dir-names

(map #(str %) dir-names)

(apply merge (map #(hash-map (keyword (str %1)) %2) (range) dir-names))

(cheshire/decode (cheshire/generate-string (map #(str %) dir-names)))

(some #(= "tests" %) dir-names)

(in? dir-names "tests2")

(defn in?
  "true if seq contains elm"
  [seq elm]
  (some #(= elm %) seq))

(require '[rcms.tests.helper :refer [folder-schema
                                     folder-data
                                     file-schema
                                     tag-schema] :reload true])
(require '[rcms.config :refer [set-mode!
                               get-settings
                               get-mode]])
(require '[rcms.db :as sql :reload true])

(get-mode)

(sql/set-connection! (sql/pooled-datasource (get-settings :database :connection)))
(sql/create-table! (sql/get-connection) (:table folder-schema) (:fields folder-schema))
(sql/create-table! (sql/get-connection) (:table file-schema) (:fields file-schema))
(sql/create-table! (sql/get-connection) (:table tag-schema) (:fields tag-schema))

(sql/drop-table! (sql/get-connection) (:table file-schema))

(sql/get-connection)

(sql/populate (sql/get-connection) :folders folder-data)

(require '[clojure.java.jdbc :as sqlc])
(sqlc/query (sql/get-connection) ["select * from tags"])

;Return millis
(System/currentTimeMillis)

(def me-map [ {:id nil :name "henry"} {:id nil :name "bob"}  ])

(map #(assoc %1 :id %2) me-map (range 1 10))

(cheshire/generate-string {:name "Security" :resource "resources/files/security"})

