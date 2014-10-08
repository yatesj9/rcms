(ns rcms.routes.announcements
  (:require [compojure.core :refer [defroutes ANY]]
            [rcms.models.announcements :as an]
            [liberator.core :refer [resource]]))

(def ^:private announcements-resource
  (resource
    :available-media-types ["application/json"]
    :allowed-methods [:get :post :delete]
    :post! (fn [{{:keys [params]}:request}]
               (an/save-announcement params))
    :delete! (fn [{{:keys [route-params]}:request}]
                 (an/remove-announcement (:title route-params)))
    :handle-ok (fn [{{:keys [params route-params]}:request}]
                   (cond
                     (:title route-params) (an/get-announcement (:title route-params))
                     :else (an/get-all-valid-announcements)))))

(defn announcements
  [request]
  (-> request
      announcements-resource))

(defroutes announcement-routes
  (ANY "/announcements" request (announcements request))
  (ANY "/announcements/:title" request (announcements request)))
