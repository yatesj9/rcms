(ns rcms.routes.uploads
  (:require [compojure.core :refer [defroutes GET POST]]
            [noir.io :refer [upload-file]]
            [noir.response :refer [redirect]]))

(def resource-path
  "resources/files/")

(defn handle-upload
  [filename]
  (upload-file resource-path filename))

(defn handle-multi-upload
  [filenames]
  (println filenames))

(defroutes upload-routes
  (POST "/upload" {params :params}
        (handle-upload (:file params)))

  (POST "/multi-upload" {params :multipart-params}
        (handle-multi-upload params)))

