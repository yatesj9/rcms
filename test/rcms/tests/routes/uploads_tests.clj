(ns rcms.tests.routes.uploads-tests
    (:require [rcms.handler :refer [app]])
    (:use [midje.sweet]
          [ring.mock.request]))

(facts "Midje Test"
  (fact "A = A = True"
    (= "A" "A") => true))
