(ns rcms.tests.models.tags-tests
  (:require [rcms.models.tags :refer [get-all-tags
                                      get-folder-tags
                                      save-tag
                                      get-folder-tag-name
                                      remove-tag]]
            [rcms.tests.helper :refer [initialize-test-connection
                                       tag-schema
                                       tag-data]]
            [rcms.db :refer [drop-table!
                             create-table!
                             populate]]
            [rcms.config :refer [get-settings
                                 set-mode!]])
  (:use [midje.sweet]))

(facts "Test Fact"
  (fact "10  2 = 5"
   (/ 10 2) => 5))

(def initialize-db
  (do (set-mode! :test)
    (let [c (initialize-test-connection)
          _ (drop-table! c (:table tag-schema))
          _ (create-table! c (:table tag-schema) (:fields tag-schema))
          _ (populate c :tags tag-data)])))

(def new-tag
     {:name "Aug 2014"
      :folder-name "People"})

(facts "Facts about tags"
  (fact "Should return all tags"
    (get-all-tags) => (map #(assoc %1 :id %2) tag-data (range 1 4)))

  (fact "Should return all tags for specific folder_name"
    (get-folder-tags "Smoke Signals") => (list {:folder_name "Smoke Signals"
                                         :id 1
                                         :name "July 2014"}))

  (fact "Should return specific tag from db"
    (get-folder-tag-name {:name "July 2014"
                          :folder-name "Smoke Signals"}) => (list {:folder_name "Smoke Signals"
                                                                   :id 1
                                                                   :name "July 2014"}))

  (fact "Should save tag to db"
    (save-tag new-tag)
    (dissoc (first (get-folder-tag-name new-tag)) :id) => {:folder_name "People"
                                                           :name "Aug 2014"})

  (fact "Should remove tag from db"
    (remove-tag "Aug 2014") => {:msg "Tag removed"}))
