(ns rcms.tests.handler-tests
  (:use [midje.sweet]
        [ring.mock.request :refer [request]]))

(facts "Subtrack"
  (fact (- 3 1) => 2))


