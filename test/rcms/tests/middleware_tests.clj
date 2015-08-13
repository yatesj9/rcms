(ns rcms.tests.middleware-tests
  (:require [rcms.middleware :as md])
  (:use [midje.sweet]
        [ring.mock.request :refer [request]]))

(facts "Adding"
  (fact (+ 1 2) => 3))

(facts "javascript to clojure"
  (fact ))
