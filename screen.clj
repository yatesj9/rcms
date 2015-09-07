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
                                     link-data
                                     announcement-schema
                                     announcement-data] :reload true])
(require '[rcms.config :refer [set-mode!
                               get-settings
                               get-mode] :reload true])
(require '[rcms.db :as sql :reload true])

(get-mode)

(get-settings :token :client-api-token)

(set-mode! :development)

;Set connection and create tables
(sql/set-connection! (sql/pooled-datasource (get-settings :database :connection)))

(sql/create-table! (sql/get-connection) (:table folder-schema) (:fields folder-schema))

(sql/create-table! (sql/get-connection) (:table file-schema) (:fields file-schema))

(sql/create-table! (sql/get-connection) (:table tag-schema) (:fields tag-schema))
(sql/create-table! (sql/get-connection) (:table link-schema) (:fields link-schema))
(sql/create-table! (sql/get-connection) (:table announcement-schema) (:fields announcement-schema))

;Drop tables
(sql/drop-table! (sql/get-connection) (:table folder-schema))

(sql/drop-table! (sql/get-connection) (:table file-schema))

(sql/drop-table! (sql/get-connection) (:table tag-schema))
(sql/drop-table! (sql/get-connection) (:table link-schema))
(sql/drop-table! (sql/get-connection) (:table announcement-schema))

;Pre-populate with fixture data
(sql/populate (sql/get-connection) :folders folder-data)
(sql/populate (sql/get-connection) :files file-data)
(sql/populate (sql/get-connection) :tags tag-data)
(sql/populate (sql/get-connection) :links link-data)
(sql/populate (sql/get-connection) :announcements announcement-data)

(require '[clojure.java.jdbc :as sqlc])
(sqlc/query (sql/get-connection) ["select * from files"])

(sqlc/update! (sql/get-connection) :files
                                  {:file_name "banner" :folder_name "Banner"}
                                  ["file_name = ? and folder_name = ?" "banner2" "Banner"])

;-------------------------------------------------------------------------------
;Misc
;-------------------------------------------------------------------------------

(require '[me.raynes.fs :as fs])

(fs/delete "resources/files/IT/chips.jpeg")

(def get-dir
     (fs/iterate-dir "resources/files"))

get-dir

(def dir-names
 (get (first (fs/iterate-dir "resources/files")) 1))

dir-names

(last (clojure.string/split (first (map #(str %) (fs/list-dir "resources/files/People"))) #"\/"))

(def folder
     (fs/list-dir "resources/files/Banner"))

(defn get-file-name
  "Takes list of files from fs/list-dir, returns all file names"
  [file-list]
  (->> folder
    (map #(str %));Converts to a string
    (map #(clojure.string/split % #"\/"));Splits by dashes
    (map #(last %))));Returns last entry for file name

(get-file-name folder)

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

(require '[rcms.models.files :reload true :as file])

(file/save-file {:folder-name "Banner" :file-name "Smoke2013.pdf" :tag "Test"})

(require '[rcms.models.tags :as tag])

(tag/save-tag {:name "Test" :folder-name "Smoke Signals"})

(def load-config
     (load-file "resources/custom_config.clj"))

(-> load-config
    :resource
    :prod-path)

;buddy

(require '[buddy.sign.jws :as jws])

(def sign-test
     (jws/sign {:employee-id 6694 :token "2342-234-234-dfsfjaskfdj-234234" :expires 234234324} "mysupersecretcode"))

(jws/unsign sign-test "mysupersecretcode")

(get-settings :token :client-api-token)

;; Image Collage

(require '[fivetonine.collage.util :as util])
(require '[fivetonine.collage.core :refer :all])

(defn resize-image
  [folder image]
  (let [path (str "resources/files/" folder "/")]
   (with-image (str path image)
              (resize :width 1024)
              (util/save (str path image)))))

(resize-image "LandGal" "MagicEarth.jpg")

(defn create-thumbnail
  [folder image]
  (let [path (str "resources/files/"folder"/")
        thumb_path (str "resources/files/"folder"/thumbnails/") ]
   (with-image (str path image)
              (resize :width 150)
              (util/save (str thumb_path "thumb_" image)))))

(create-thumbnail "LandGal" "MagicEarth.jpg")

(defn process-image
  [folder image]
  (do
    (resize-image folder image)
    (create-thumbnail folder image)))

(process-image "LandGal" "Book.png")

