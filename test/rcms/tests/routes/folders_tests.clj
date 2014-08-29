(ns rcms.tests.routes.folders-tests
  (:require [rcms.routes.folders :refer [folder-routes]]
            [cheshire.custom :refer [encode]])
  (:use [expectations :refer [expect]]
        [ring.mock.request :refer [request]]))

; --- GET returns json map of all foders in db
(expect 200  (-> (folder-routes (request :get "/folders"))
               :status))

(comment
 (expect {:status 201
         :headers {"Content-Type" "text/plain"}
         :body nil} (folder-routes (assoc (request :post "/folders")
                           :params {"name" "test1" "resource" "resources/files/test1"}))) )



