(ns rcms.tests.models.announcements-tests
  (:require [rcms.models.announcements :refer [get-all-announcements
                                               get-all-valid-announcements
                                               get-announcement
                                               save-announcement
                                               remove-announcement]]
            [rcms.tests.helper :refer [initialize-test-connection
                                       announcement-schema
                                       announcement-data]]
            [rcms.db :refer [drop-table!
                             create-table!
                             populate]]
            [rcms.config :refer [get-settings
                                 set-mode!]])
  (:use [midje.sweet]))

(facts "Test fact for Midje"
  (fact "2 * 5 = 10"
    (* 2 5) => 10))

(def initialize-db
  (do (set-mode! :test)
    (let [c (initialize-test-connection)
          _ (drop-table! c (:table announcement-schema))
          _ (create-table! c (:table announcement-schema) (:fields announcement-schema))
          _ (populate c :announcements announcement-data)])))

(facts "Facts about announcements"
  (fact "Should return all announcements"
    (get-all-announcements) => (map #(assoc %1 :id %2) announcement-data (range 1 4)))

  (fact "Should return single annoucement"
    (get-announcement "Welcome") => (list (assoc (first announcement-data) :id 1)))

  (fact "Should save annoucement to db"
    (let [new-announcement
          {:title "Save Test"
           :body "Some body"
           :expires_at (+ (System/currentTimeMillis) 3433)}]
      (save-announcement new-announcement)
      (dissoc (first (get-announcement "Save Test")) :id :created_at)
        => new-announcement))

  (fact "Should update annoucement in db"
    (let [update-announcement
          {:title "Save Test"
           :body "Updated body"
           :expires_at (+ (System/currentTimeMillis) 3436)}]
      (save-announcement update-announcement)
      (dissoc (first (get-announcement "Save Test")) :id :created_at)
        => update-announcement))

  (fact "Should remove announcement in db"
    (remove-announcement "Save Test") => {:msg "Announcement removed"}))



