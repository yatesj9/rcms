(ns rcms.routes.tags
  (:require [compojure.core :refer [defroutes ANY]]
            [rcms.models.tags :as tg]
            [liberator.core :refer [resource]]))

(def ^:private tags-resource
  (resource
    :available-media-types ["application/json"]
    :allowed-methods [:get :post :delete]
    :post! (fn [{{:keys [params]}:request}]
               (tg/save-tag params))
    :delete! (fn [{{:keys [route-params]}:request}]
                 (tg/remove-tag (:name route-params)))
    :handle-ok (fn [{{:keys [params route-params]}:request}]
                   (cond
                     (:folder route-params) (tg/get-folder-tags (:folder route-params))
                     :else (tg/get-all-tags)))))

(defn tags
  [request]
  (-> request
      tags-resource))

(defroutes tag-routes
  (ANY "/tags" request (tags request))
  (ANY "/tags/:folder" request (tags request))
  (ANY "/tags/:folder/:name" request (tags request)))
