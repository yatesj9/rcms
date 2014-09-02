(ns rcms.tests.routes.files-tests
  (:require [rcms.routes.files :refer [file-routes]]
            [rcms.models.files :as fl]
            [cheshire.custom :refer [decode]])
  (:use [midje.sweet]
        [ring.mock.request :refer [request]]))

(facts "Midje Test"
  (fact "3 * 3 = 9"
    (* 3 3) => 9))

(facts "Facts about files Resource, GET/DELETE"
  (fact "GET - Should return json map in body of all files"
    (decode (:body (file-routes (request :get "/files"))) true)
     => (fl/get-all-files))

  (fact "GET - Should return json map in body of all files in specific folder"
     (decode (:body (file-routes (request :get "/files/People"))) true)
     => (fl/get-files "People"))

  (fact "GET - Should return single file map"
    (decode (:body (file-routes (request :get "/files/People/my_mash.jpg"))) true)
    => (fl/get-file "my_mash.jpg"))

  (fact "DELETE - Should delete file from DB"
    (:status (file-routes (request :delete "/files/People/my_mash.jpg")))
    => 204))
