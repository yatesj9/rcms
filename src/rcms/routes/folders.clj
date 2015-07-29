(ns rcms.routes.folders
  (:require [compojure.core :refer [defroutes ANY]]
            [rcms.models.folders :as fl]
            [liberator.core :refer [resource]]))

(def ^:private folders-resource
  (resource
    :available-media-types ["application/json"]
    :allowed-methods [:get :post :put :delete]
    :post! (fn [{{:keys [params]}:request}]
               (do (fl/add-folder params)
                 (fl/create-directory (:name params))))
    :put! (fn [{{:keys [params route-params]}:request}]
              (do (fl/rename-folder (:name route-params) (:new-name params))
                (fl/rename-directory (assoc params :current-name (:name route-params)))))
    :delete! (fn [{{:keys [route-params]}:request}]
                 (do (fl/remove-folder (:name route-params))
                   (fl/remove-directory (:name route-params))))
    :handle-ok (fn [{{:keys [route-params]}:request}]
                   (cond
                     (:name route-params) (fl/get-folder (:name route-params))
                     :else (fl/get-folders)))))

(defn folders
  [{params :params :as request}]
  (-> request
      folders-resource))

(defroutes folder-routes
  (ANY "/folders" request (folders request))
  (ANY "/folders/:name" request (folders request)))
