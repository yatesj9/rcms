(ns rcms.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.format :refer [wrap-restful-format]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [rcms.routes.uploads :refer [upload-routes]]
            [rcms.routes.folders :refer [folder-routes]]
            [rcms.routes.files :refer [file-routes]]
            [rcms.routes.tags :refer [tag-routes]]
            [rcms.routes.links :refer [link-routes]]))

(defn init []
  (println "rcms is starting"))

(defn destroy []
  (println "rcms is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes upload-routes
              folder-routes
              file-routes
              tag-routes
              link-routes
              app-routes)
      (handler/site)
      (wrap-restful-format)))
