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

;-------------------------------------------------------------------------------
; --- Database Directory TESTS
;-------------------------------------------------------------------------------

(facts "Facts about FILESYSTEM directories"
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
    (get-directories) => '("dir1","dir2")
    (remove-directory "dir1")
    (remove-directory "dir2")))

;-------------------------------------------------------------------------------
; --- Database Folder TESTS
;-------------------------------------------------------------------------------

(facts "Facts about DATABASE folders"
  (fact "Should return map of folders in DB"
    (get-folders) => (map #(assoc %1 :id %2) folder-data (range 1 3)))

  (fact "Should add new folder to DB"
    (add-folder {:name "Folder 1" :folder "folder1"}) => {:msg "Folder added"}
    (get-folders) => (map #(assoc %1 :id %2)
                          (conj folder-data {:id nil
                                        :name "Folder 1"
                                        :folder "folder1"}) (range 1 4)))

  (fact "Should remove the folder from DB, and verify"
    (remove-folder 3) => {:msg "Folder removed"}
    (get-folders) => (map #(assoc %1 :id %2) folder-data (range 1 3)))

  (fact "Should rename current folder in DB"
    (rename-folder 1 "PeoplePower") => {:msg "Folder renamed"}
    (get-folder "PeoplePower") => {:id 1
                                   :folder "people"
                                   :name "PeoplePower"}))
