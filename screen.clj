;-------------------------------------------------------------------------------
;Start rcms server on port 8080
;-------------------------------------------------------------------------------

(require '[rcms.repl :as rpl])
(rpl/start-server :test)

(rpl/stop-server)

;-------------------------------------------------------------------------------
;Tests
;-------------------------------------------------------------------------------

(require '[rcms.routes.uploads :reload true]
         '[rcms.tests.routes.uploads-tests :reload true])

(require '[rcms.models.folders :reload true]
         '[rcms.routes.folders :reload true]
         '[rcms.tests.models.folders-tests :reload true]
         '[rcms.tests.routes.folders-tests :reload true])

(require '[rcms.models.files :reload true]
         '[rcms.routes.files :reload true]
         '[rcms.tests.models.files-tests :reload true]
         '[rcms.tests.routes.files-tests :reload true])

(require '[rcms.models.tags :reload true]
         '[rcms.routes.tags :reload true]
         '[rcms.tests.models.tags-tests :reload true]
         '[rcms.tests.routes.tags-tests :reload true])

(require '[rcms.models.links :reload true]
         '[rcms.routes.links :reload true]
         '[rcms.tests.models.links-tests :reload true]
         '[rcms.tests.routes.links-tests :reload true])

;-------------------------------------------------------------------------------
;Database
;-------------------------------------------------------------------------------

(require '[rcms.tests.helper :refer [folder-schema
                                     folder-data
                                     file-schema
                                     file-data
                                     tag-schema
                                     tag-data
                                     link-schema
                                     link-data] :reload true])
(require '[rcms.config :refer [set-mode!
                               get-settings
                               get-mode]])
(require '[rcms.db :as sql :reload true])

;Set connection and create tables
(sql/set-connection! (sql/pooled-datasource (get-settings :database :connection)))
(sql/create-table! (sql/get-connection) (:table folder-schema) (:fields folder-schema))
(sql/create-table! (sql/get-connection) (:table file-schema) (:fields file-schema))
(sql/create-table! (sql/get-connection) (:table tag-schema) (:fields tag-schema))
(sql/create-table! (sql/get-connection) (:table link-schema) (:fields link-schema))

;Drop tables
(sql/drop-table! (sql/get-connection) (:table folder-schema))
(sql/drop-table! (sql/get-connection) (:table file-schema))
(sql/drop-table! (sql/get-connection) (:table tag-schema))
(sql/drop-table! (sql/get-connection) (:table link-schema))

;Pre-populate with fixture data
(sql/populate (sql/get-connection) :folders folder-data)
(sql/populate (sql/get-connection) :files file-data)
(sql/populate (sql/get-connection) :tags tag-data)
(sql/populate (sql/get-connection) :links link-data)

(require '[clojure.java.jdbc :as sqlc])
(sqlc/query (sql/get-connection) ["select * from folders"])

;-------------------------------------------------------------------------------
;Misc
;-------------------------------------------------------------------------------

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

;Return millis
(System/currentTimeMillis)

(def me-map [ {:id nil :name "henry"} {:id nil :name "bob"}  ])

(map #(assoc %1 :id %2) me-map (range 1 10))

(cheshire/generate-string {:name "Security" :resource "resources/files/security"})
