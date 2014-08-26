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

