(ns rcms.tests.models.files-tests
  (:require [rcms.models.files :refer [get-files
                                       get-file
                                       save-file]]
            [rcms.tests.helper :refer [initialize-test-connection
                                       file-schema
                                       file-data]]
            [rcms.db :refer [drop-table!
                             create-table!
                             populate]]
            [rcms.config :refer [get-settings
                                 set-mode!]])
  (:use [midje.sweet]))

(facts "Test Fact"
  (fact "1 + 1 should equal 2"
    (+ 1 1) => 2))

(def initialize-db
  (do (set-mode! :test)
   (let [c (initialize-test-connection)
        - (drop-table! c (:table file-schema))
        _ (create-table! c (:table file-schema) (:fields file-schema))
        _ (populate c :files file-data)])))

(def new-file
  {:folder-name "People"
   :file-name "testing.pdf"
   :description "File test"})

(facts "Facts about files"
  (fact "Return files from folder id 1"
    (map #(dissoc %  :description :updated_at ) (get-files "People"))
    => '({:file_name "my_mash.jpg" :folder_name "People" :id 1}))

  (fact "Should return single file using name"
    (dissoc (first (get-file "my_mash.jpg"))  :description :updated_at)
      =>  {:file_name "my_mash.jpg" :folder_name "People" :id 1})

  (fact "Should save file to DB"
    (save-file new-file)
    (dissoc (first (get-file "testing.pdf")) :updated_at :id)
      => {:description "File test"
          :file_name "testing.pdf"
          :folder_name "People"}))
