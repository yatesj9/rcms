(ns rcms.routes.folders
  (:require [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]
            [rcms.models.folders :as fl]
            [liberator.core :refer [resource request-method-in]]
            [cheshire.custom :refer (encode)]))

(defn test-get-handler
  []
  {:status 200
   :body "Test-Handler"})

(def ^:private folders-resource
  (resource
    :available-media-types ["application/json"]
    :allowed-methods [:get :post :put :delete]
    :post! (fn [{{:keys [params]}:request}]
               (do (fl/add-folder params)
                 (fl/create-directory (:folder params))))
    :put! (fn [{{:keys [params]}:request}]
              (do (fl/rename-folder (:name params)
                                    (:new-name params)
                                    (str "resources/files/" (:new-name params)))
                (println params)))
    :delete! (fn [{{:keys [params]}:request}]
                 (do (fl/remove-folder (:folder params))
                   (fl/remove-directory (:folder params))))
    :handle-ok (fn [_]
                   (encode (fl/get-folders)))))

(defn folders
  [{params :params :as request}]
  (-> request
      folders-resource))

(defroutes folder-routes
  (ANY "/folders" request (folders request)))
