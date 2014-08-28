(ns rcms.routes.folders
  (:require [compojure.core :refer [defroutes GET POST PUT DELETE]]
            [rcms.models.folders :as fl]))

(defn test-get-handler
  []
  {:status 200
   :body "Test-Handler"})

(defn test-post-handler
  [param]
  (do (fl/add-folder param)
      (fl/create-directory (str (:name param)))
      {:status 201
       :body (str param)}))

(defroutes folder-routes
  (GET "/folders" [] (test-get-handler))
  (POST "/folders" {params :params} (test-post-handler params)))
