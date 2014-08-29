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
                             populate]]
            [rcms.config :refer [get-settings
                                 set-mode!]])
  (:use [midje.sweet]))

(def initialize-db
  (do (set-mode! :test)
   (let [c (initialize-test-connection)
        - (drop-table! c (:table folder-schema))
        _ (create-table! c (:table folder-schema) (:fields folder-schema))
        _ (populate c :folders folder-data)])))

(facts "Facts about adding"
  (fact "2 + 1 = 3"
   (+ 2 1) => 3))

(facts "Facts about create, remove, rename directory"
  (fact "Should create, error for existing, and remove"
    (create-directory "bob") => true
    (create-directory "bob") => {:error "Directory already exists"}
    (remove-directory "bob") => true)

  (fact "Should create, rename and remove"
    (create-directory "rename_test") => true
    (rename-directory {:current-name "rename_test" :new-name "rename"}) => true
    (remove-directory "rename") => true)

  (fact "Should get list of directorys"
    (create-directory "dir1")
    (create-directory "dir2")
    (get-directories) => '("dir1","dir2")))



(comment

;-------------------------------------------------------------------------------
; --- Database Folder TESTS
;-------------------------------------------------------------------------------

;Returns sequence of maps containg all folders in DB
(expect (map #(assoc %1 :id %2) folder-data (range 1 3)) (get-folders))

 ;Add new folder record to DB
(expect true (seq? (add-folder {:id nil
                                :name "TestFolder"
                                :folder "testfolder"})) )

;Remove folder record from DB
(expect '(1) (remove-folder 3))

;Returns sequence of maps matching helper schema
(expect (map #(assoc %1 :id %2) folder-data (range 1 3)) (get-folders))

;Rename folder in DB, add record, rename, verify
(expect true (seq? (add-folder { :name "TestFolder"
                                :folder "testfolder"})) )

(expect '(1) (rename-folder 1 "testfolder"))
;Return true for renamed folder
(expect true (some #(= "testfolder" (:name %)) (get-folders)))  )

