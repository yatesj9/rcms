(ns rcms.tests.helper_tests
  (:require [rcms.tests.helper :refer [initialize-test-connection]])
  (:use [midje.sweet]))

(facts "Facts about helper functions"
  (fact "Should return a map for initializing test connection"
    (map? (initialize-test-connection))  => true))
