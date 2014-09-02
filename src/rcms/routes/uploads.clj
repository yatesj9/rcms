(ns rcms.routes.uploads
  (:require [compojure.core :refer [defroutes ANY]]
            [noir.io :refer [upload-file]]
            [liberator.core :refer [resource]]
            [noir.response :refer [redirect]]
            [rcms.config :refer [get-settings]]
            [rcms.models.files :refer [save-file]]))

(defn resource-path
  "Returns path fron settings, adds folder if supplied"
  ([]
   (get-settings :resource :path))
  ([folder]
   (str (get-settings :resource :path) folder)))

(defn handle-upload
  "Takes folder and filename, saves to resource-path from config"
  [folder filename]
  (upload-file (resource-path folder) filename))

(def ^:private upload-resource
     (resource
        :allowed-methods [:post :get]
        :available-media-types ["application/json"]
        :post! (fn [{{:keys [params]}:request}]
                   (do (handle-upload (:folder params) (:file params)))
                       (save-file {:folder-name (:folder params)
                                   :file-name (:filename (:file params))
                                   :description (:description params)}))
        :handle-created (fn [{{:keys [params]}:request}]
                            (:filename (:file params)))))

(def ^:private multi-upload-resource
     (resource
         :allowed-methods [:post]
         :available-media-types ["application/json"]
         :post! (fn [{{:keys [params]}:request}]
                    (doseq [x (:file params)]
                             (handle-upload (:folder params) x)
                             (save-file {:folder-name (:folder params)
                                         :file-name (:filename x)
                                         :description (:description params)})))
         :handle-created (fn [{{:keys [params]}:request}]
                             (map #(:filename %) (:file params)))))
(defn upload
  [request]
  (-> request
      upload-resource))

(defn multi-upload
  [request]
  (-> request
      multi-upload-resource))

(defroutes upload-routes
  (ANY "/upload" request (upload request))
  (ANY "/multi-upload" request (multi-upload request)))

