;Start rcms server onport 8080
(require '[rcms.repl :as rpl])
(rpl/start-server)

(rpl/stop-server)

(require '[cheshire.core :as cheshire])

(require '[me.raynes.fs :as fs])

(fs/directory? "resources/files/tests")

(fs/mkdir "resources/files/tests")

(fs/delete-dir "resources/files/tests")

(fs/copy-dir "resources/files/tests" "resources/files/tests2")

(def get-dir
     (fs/iterate-dir "resources/files"))

(def dir-names
 (get (first (fs/iterate-dir "resources/files")) 1))

dir-names

(map #(str %) dir-names)

(apply merge (map #(hash-map (keyword (str %1)) %2) (range) dir-names))

(cheshire/decode (cheshire/generate-string (map #(str %) dir-names)))

(some #(= "tests3" %) dir-names)

(in? dir-names "tests2")

(defn in?
  "true if seq contains elm"
  [seq elm]
  (some #(= elm %) seq))

(first get-dir)

get-dir

(require '[rcms.tests.helper :refer [folder-schema
                                     folder-data] :reload true])

(:table folder-schema)

(:fields folder-schema)

(require '[rcms.config :refer [set-mode!
                               get-settings]])

(require '[rcms.db :as sql :reload true])

(do (set-mode! :test)
 (sql/set-connection! (sql/pooled-datasource (get-settings :database :connection))) )

(sql/create-table! (sql/get-connection) (:table folder-schema) (:fields folder-schema))

(sql/drop-table! (sql/get-connection) (:table folder-schema))

(sql/get-connection)

(sql/populate (sql/get-connection) :folders folder-data)

(require '[clojure.java.jdbc :as sqlc])

(sqlc/query (sql/get-connection) ["select * from folders"])

