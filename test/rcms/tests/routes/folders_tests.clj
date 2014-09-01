(ns rcms.tests.routes.folders-tests
  (:require [rcms.routes.folders :refer [folder-routes]]
            [cheshire.custom :refer [encode decode]]
            [rcms.tests.helper :refer [folder-data]]
            [rcms.models.folders :refer [get-folders]])
  (:use [midje.sweet ]
        [ring.mock.request :refer [request]]))


(facts "Facts about folder Resource, GET/POST/PUT/DELETE"
  (fact "GET - Should return json map in body of all folders"
    (seq? (decode (:body (folder-routes (request :get "/folders"))) true))
     => true)

  (fact "POST - "
    (:status (folder-routes (assoc (request :post "/folders")
                          :params {:name "Post test" :folder "posttest"})))
    => 201)

  (fact "PUT -"
    (:status (folder-routes (assoc (request :put "/folders")
                          :params {:id 4 :current-name "posttest" :new-name "posttestnew"})))
    => 201)

  (fact "DELETE -"
    (:status (folder-routes (assoc (request :delete "/folders")
                                  :params {:id 4 :folder "posttestnew"})))
    => 204))



