(ns rcms.routes.uploads
  (:require [compojure.core :refer [defroutes ANY]]
            [noir.io :refer [upload-file]]
            [liberator.core :refer [resource]]
            [noir.response :refer [redirect]]
            [rcms.config :refer [get-settings]]
            [rcms.models.files :refer [save-file]]
            [fivetonine.collage.util :as util]
            [fivetonine.collage.core :refer :all]))

(defn resource-path
  "Returns path fron settings, adds folder if supplied"
  ([]
   (get-settings :resource :path))
  ([folder]
   (str (get-settings :resource :path) folder)))

(def valid-images
     ["image/jpeg" "image/png"])

(defn image?
  [content-type]
  (> (.indexOf valid-images content-type) -1))

(defn resize-image
  [folder image]
  (let [path (str "resources/files/" folder "/")]
   (with-image (str path image)
              (resize :width 1024)
              (util/save (str path image)))))

(defn create-thumbnail
  [folder image]
  (let [path (str "resources/files/"folder"/")]
   (with-image (str path image)
              (resize :width 150)
              (util/save (str path "thumb_" image)))))

(defn process-image
  [folder image]
  (.start (Thread.
   (do
    (resize-image folder image)
    (create-thumbnail folder image)))))

(defn handle-upload
  "Takes folder and filename, saves to resource-path from config"
  [folder filename]
  (if (image? (:content-type filename))
   (do (upload-file (resource-path folder) filename)
       (process-image folder (:filename filename)))
   (upload-file (resource-path folder) filename)) )

(def ^:private upload-resource
     (resource
        :allowed-methods [:post :get]
        :available-media-types ["application/json"]
        :post! (fn [{{:keys [params]:as request}:request}]
                   (do (handle-upload (:folder params) (:file params)))
                       (save-file {:folder-name (:folder params)
                                   :file-name (:filename (:file params))
                                   :tag (:tag params)}))
        :handle-created (fn [{{:keys [params]}:request}]
                            (:filename (:file params)))))

(def ^:private multi-upload-resource
     (resource
         :allowed-methods [:post]
         :available-media-types ["application/json"]
         :post! (fn [{{:keys [params]:as request}:request}]
                    (doseq [x (:file params)]
                             (handle-upload (:folder params) x)
                             (save-file {:folder-name (:folder params)
                                         :file-name (:filename x)
                                         :tag (:tag params)})))
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

