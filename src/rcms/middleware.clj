(ns rcms.middleware
  (:require [clojure.set :refer [rename-keys]]))

(def ^:private json-clojure
     {:folderName :folder-name
      :fileName :file-name
      :newName :new-name})

(defn- json->clojure
  [map]
  (rename-keys map json-clojure))

(defn with-wrap-json->clojure
  [handler]
  (fn [req]
      (let [params (:params req)]
        (handler (assoc req :params (json->clojure params))))))


