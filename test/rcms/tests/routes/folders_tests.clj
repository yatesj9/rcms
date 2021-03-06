(ns rcms.tests.routes.folders-tests
  (:require [rcms.handler :refer [app]]
            [cheshire.custom :refer [decode]]
            [rcms.tests.helper :refer [folder-data]]
            [rcms.models.folders :as fl]
            [rcms.tests.helper :refer [build-req]])
  (:use [midje.sweet ]
        [ring.mock.request :refer [request]]))


(facts "Facts about folder Resource, GET/POST/PUT/DELETE"
  (fact "GET - Should return json map in body of all folders"
    (seq? (decode (:body (app (build-req (request :get "/folders")))) true))
     => true)

  (fact "GET - Should return map of all folders"
    (decode (:body (app (build-req (request :get "/folders")))) true)
     => (fl/get-folders))

  (fact "POST - Should return status of 201"
    (:status (app (assoc (build-req (request :post "/folders"))
                          :params {:name "Post test"})))
    => 201)

  (fact "PUT - Should return status of 201"
    (:status (app (assoc (build-req (request :put "/folders/Post%20test"))
                          :params {:newName "posttestnew"})))
    => 201)

  (fact "DELETE - Should return status of 204"
    (:status (app (build-req (request :delete "/folders/posttestnew"))))
    => 204))

