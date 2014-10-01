(ns rcms.routes.files
  (:require [compojure.core :refer [defroutes ANY DELETE]]
            [rcms.models.files :as fl]
            [liberator.core :refer [resource]]
            [ring.util.response :refer [file-response]]
            [rcms.config :refer [get-settings]]))

(def ^:private files-resource
  (resource
    :available-media-types ["application/json"]
    :allowed-methods [:get :delete]
    :handle-ok (fn [{{:keys [params route-params]}:request}]
                   (cond
                     (:folder route-params) (fl/get-files (:folder route-params))
                     :else (fl/get-all-files)))
    :delete! (fn [{{:keys [route-params]}:request}]
                 (do (fl/remove-file (:filename route-params))
                  (fl/delete-file (:folder route-params) (:filename route-params))))))

(defn files
  [request]
  (-> request
      files-resource))

(defroutes file-routes
  (ANY "/files" request (files request))
  (ANY "/files/:folder" request (files request))
  (DELETE "/files/:folder/:filename" request (files request))
  (ANY "/files/:folder/:filename" [folder filename]
       (file-response (str (get-settings :resource :path) folder "/" filename))))
