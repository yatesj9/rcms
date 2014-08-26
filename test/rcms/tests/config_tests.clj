(ns rcms.tests.config-tests
  (:require [rcms.config :refer [get-mode
                                 set-mode!
                                 get-settings]])
  (:use [expectations :refer [expect]]))

;Return default mode
(expect :development (get-mode))

;Set and return DB settings for TEST, h2 DB
(expect :test (set-mode! :test))
(expect {:url "jdbc:h2:mem:testdb"
         :min-connections 1
         :max-connections 1
         :partitions 1
         :log-statements? true} (get-settings :database :connection))
(expect :development (set-mode! :development))
