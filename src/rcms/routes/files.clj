(ns rcms.routes.files
  (:require [compojure.core :refer [defroutes ANY]]
            [rcms.models.files :as fl]
            [liberator.core :refer [resource]]))

(def ^:private files-resource
  (resource
    :available-media-types ["application/json"]
    :allowed-methods [:get :delete]
    :handle-ok (fn [{{:keys [params route-params]}:request}]
                   (cond
                     (:folder route-params) (fl/get-files (:folder route-params))
                     (:filename route-params)(fl/get-file (:filename route-params))
                     :else (fl/get-all-files)))
    :delete! (fn [{{:keys [route-params]}:request}]
                 (fl/remove-file (:filename route-params)))))

(defn files
  [request]
  (-> request
      files-resource))

(defroutes file-routes
  (ANY "/files" request (files request))
  (ANY "/files/:folder" request (files request))
  (ANY "/files/:folder/:filename" request (files request)))
