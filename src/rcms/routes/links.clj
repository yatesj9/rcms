(ns rcms.routes.links
  (:require [compojure.core :refer [defroutes ANY]]
            [rcms.models.links :as ln]
            [liberator.core :refer [resource]]))

(def ^:private link-resource
  (resource
    :available-media-types ["application/json"]
    :allowed-methods [:get :post :delete]
    :post! (fn [{{:keys [params]}:request}]
                (ln/save-link params))
    :delete! (fn [{{:keys [route-params]}:request}]
                 (ln/remove-link (:name route-params)))
    :handle-ok (fn [{{:keys [params]}:request}]
                   (ln/get-links))))

(defn links
  [request]
  (-> request
      link-resource))

(defroutes link-routes
  (ANY "/links" request (links request))
  (ANY "/links/:name" request (links request)))
