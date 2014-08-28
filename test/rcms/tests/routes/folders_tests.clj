(ns rcms.tests.routes.folders-tests
  (:require [rcms.routes.folders :refer [folder-routes]]
            [cheshire.core :refer [generate-string]])
  (:use [expectations :refer [expect]]
        [ring.mock.request :refer [request]]))

;Test folder routes
(expect {:status 200
         :headers {}
         :body "Test-Handler"} (folder-routes (request :get "/folders")))


