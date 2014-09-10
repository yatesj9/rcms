(ns rcms.tests.models.links-tests
  (:require [rcms.models.links :refer [get-links
                                       get-link
                                       save-link
                                       remove-link]]
            [rcms.tests.helper :refer [initialize-test-connection
                                       link-schema
                                       link-data]]
            [rcms.db :refer [drop-table!
                             create-table!
                             populate]]
            [rcms.config :refer [get-settings
                                 set-mode!]])
  (:use [midje.sweet]))

(facts "Test Fact"
  (fact "89 * 123 = 10947"
    (* 89 123) => 10947))

(def initialize-db
  (do (set-mode! :test)
    (let [c (initialize-test-connection)
          _ (drop-table! c (:table link-schema))
          _ (create-table! c (:table link-schema) (:fields link-schema))
          _ (populate c :links link-data)])))

(def new-link
  {:name "Casino Rama"
   :href "http://casinorama.com"
   :link_order 2})

(facts "Facts about links"
  (fact "Return all links in db"
    (get-links) => (map #(assoc %1 :id %2) link-data (range 1 4)))

  (fact "Should save link to db"
    (save-link new-link)
    (dissoc (first (get-link "Casino Rama")) :id) => new-link)

  (fact "Should remove link from db"
    (remove-link "Casino Rama") => {:msg "Link removed"}))
