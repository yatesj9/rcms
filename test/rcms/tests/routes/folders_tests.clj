(ns rcms.tests.routes.folders-tests
  (:require [rcms.routes.folders :refer [folder-routes]]
            [cheshire.custom :refer [encode decode]]
            [rcms.tests.helper :refer [folder-data]]
            [rcms.models.folders :as fl])
  (:use [midje.sweet ]
        [ring.mock.request :refer [request]]))


(facts "Facts about folder Resource, GET/POST/PUT/DELETE"
  (fact "GET - Should return json map in body of all folders"
    (seq? (decode (:body (folder-routes (request :get "/folders"))) true))
     => true)

  (fact "GET - Should return map of all folders"
    (decode (:body (folder-routes (request :get "/folders"))) true)
     => (fl/get-folders))

  (fact "POST - Should return status of 201"
    (:status (folder-routes (assoc (request :post "/folders")
                          :params {:name "Post test"})))
    => 201)

  (fact "PUT - Should return status of 201"
    (:status (folder-routes (assoc (request :put "/folders/Post%20test")
                          :params {:new-name "posttestnew"})))
    => 201)

  (fact "DELETE - Should return status of 204"
    (:status (folder-routes (request :delete "/folders/posttestnew")))
    => 204))

