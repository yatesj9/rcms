(ns rcms.tests.routes.uploads-tests
    (:require [rcms.routes.uploads :refer [upload-routes]])
    (:use [expectations :refer [expect]]
          [ring.mock.request]))

;Expectations TEST - Should return 2
(expect 2 (+ 1 1))
