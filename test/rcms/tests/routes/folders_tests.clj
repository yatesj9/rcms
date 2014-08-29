(ns rcms.tests.routes.folders-tests
  (:require [rcms.routes.folders :refer [folder-routes]]
            [cheshire.custom :refer [encode]])
  (:use [midje.sweet ]
        [ring.mock.request :refer [request]]))




