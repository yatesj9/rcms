(ns rcms.tests.models.folders-tests
  (:require [rcms.models.folders :refer [get-directories
                                         create-directory
                                         remove-directory
                                         rename-directory]]
            [rcms.tests.helper :refer [initialize-test-connection
                                       folder-schema
                                       folder-data]]
            [rcms.db           :refer [drop-table!
                                       create-table!
                                       populate]])
  (:use [expectations :refer [expect]]))


(defn initialize-db
  {:expectations-options :before-run}
  []
  (let [c (initialize-test-connection)
        - (drop-table! c (:table folder-schema))
        _ (create-table! c (:table folder-schema) (:fields folder-schema))
        _ (populate c :folders folder-data)]) )

(def resource-path
  "resources/files/tests")

;Create directory, return true
(expect true (create-directory resource-path))

;Error if directory exists, return error
(expect {:error "Directory already exists"} (create-directory resource-path))

;Delete directory, return true
(expect true (remove-directory resource-path))

;Delete directory that does not exist, return error
(expect {:error "Directory does not exist"} (remove-directory resource-path))

;Create and Rename directory, than remove new named directory
(expect true (create-directory resource-path))
(expect true (rename-directory resource-path "resources/files/tests2"))

;Create directory, return list of all directories
(expect true (create-directory resource-path))
(expect '("tests2" "tests") (get-directories))

;CLEAN remove directories
(expect true (remove-directory resource-path))
(expect true (remove-directory "resources/files/tests2"))
