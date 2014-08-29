(ns rcms.tests.config-tests
  (:require [rcms.config :refer [get-mode
                                 set-mode!
                                 get-settings]])
  (:use [midje.sweet]))

(facts "Facts about setting and getting configs"
  (fact "Should set mode"
    (set-mode! :test) => :test)

  (fact "Should return database connection information"
    (get-settings :database :connection) => {:url "jdbc:h2:mem:testdb"
                                             :min-connections 1
                                             :max-connections 1
                                             :partitions 1
                                             :log-statements? true})

  (fact "Should return resource path"
    (get-settings :resource :path) => "resources/files/"))
