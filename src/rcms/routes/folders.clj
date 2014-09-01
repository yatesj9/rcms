(ns rcms.routes.folders
  (:require [compojure.core :refer [defroutes ANY]]
            [rcms.models.folders :as fl]
            [liberator.core :refer [resource]]
            [cheshire.custom :refer (encode)]))

(def ^:private folders-resource
  (resource
    :available-media-types ["application/json"]
    :allowed-methods [:get :post :put :delete]
    :post! (fn [{{:keys [params]}:request}]
               (do (fl/add-folder params)
                 (fl/create-directory (:name params))))
    :put! (fn [{{:keys [params]}:request}]
              (do (fl/rename-folder (:id params) (:new-name params))
                (fl/rename-directory params)))
    :delete! (fn [{{:keys [params]}:request}]
                 (do (fl/remove-folder (:id params))
                   (fl/remove-directory (:name params))))
    :handle-ok (fn [{{:keys [route-params]}:request}]
                   (do (let [folder-name (fl/get-folder (:name route-params))]
                         (if folder-name
                           folder-name
                           (fl/get-folders)))))))

(defn folders
  [{params :params :as request}]
  (-> request
      folders-resource))

(defroutes folder-routes
  (ANY "/folders" request (folders request))
  (ANY "/folders/:name" request (folders request)))
