(ns rcms.tests.helper_tests
  (:require [rcms.tests.helper :refer [initialize-test-connection]])
  (:use [expectations :refer [expect]]))

(expect true (-> (initialize-test-connection)
              map?))
