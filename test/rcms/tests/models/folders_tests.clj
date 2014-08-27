(ns rcms.tests.models.folders-tests
  (:require [rcms.models.folders :refer [get-directories
                                         create-directory
                                         remove-directory
                                         rename-directory
                                         get-folders
                                         get-folder
                                         add-folder
                                         remove-folder
                                         rename-folder]]
            [rcms.tests.helper :refer [initialize-test-connection
                                       folder-schema
                                       folder-data]]
            [rcms.db :refer [drop-table!
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

(defn clean-folders
  {:expectations-options :after-run}
  []
  (do (remove-directory resource-path)
      (remove-directory "resources/files/tests2")))


;-------------------------------------------------------------------------------
; --- Direct Folder TESTS
;-------------------------------------------------------------------------------

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


;-------------------------------------------------------------------------------
; --- Database Folder TESTS
;-------------------------------------------------------------------------------

;Returns sequence of maps containg all folders in DB
(expect (map #(assoc %1 :id %2) folder-data (range 1 3)) (get-folders))

;Add new folder record to DB
(expect true (seq? (add-folder {:id nil
                                :name "TestFolder"
                                :resource "resources/files/testfolder"})) )

;Remove folder record from DB
(expect '(1) (remove-folder 3))

;Returns sequence of maps matching helper schema
(expect (map #(assoc %1 :id %2) folder-data (range 1 3)) (get-folders))

;Rename folder in DB, add record, rename, verify
(expect true (seq? (add-folder {:id nil
                                :name "TestFolder"
                                :resource "resources/files/testfolder"})) )
(expect '(1) (rename-folder "TestFolder" "testfolder"))
;Return true for renamed folder
(expect true (some #(= "testfolder" (:name %)) (get-folders)))
