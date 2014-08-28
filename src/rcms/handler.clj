(ns rcms.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.json :refer [wrap-json-response
                                          wrap-json-params]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [rcms.routes.uploads :refer [upload-routes]]
            [rcms.routes.folders :refer [folder-routes]]))

(defn init []
  (println "rcms is starting"))

(defn destroy []
  (println "rcms is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes upload-routes folder-routes app-routes)
      (handler/site)
      (wrap-json-response)
      (wrap-json-params)))
