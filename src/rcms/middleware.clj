(ns rcms.middleware
  (:require [clojure.set :refer [rename-keys]]
            [rcms.config :refer [get-settings]]))

(def ^:private json-clojure
     {:folderName :folder-name
      :fileName :file-name
      :newName :new-name
      :updatedAt :updated-at
      :employeeId :employee-id
      :folderOrder :folder-order
      :linkOrder :link-order
      :expiresAt :expires-at
      :createdAt :created-at})

(defn- json->clojure
  [map]
  (rename-keys map json-clojure))

(defn with-wrap-json->clojure
  [handler]
  (fn [req]
      (let [params (:params req)]
        (handler (assoc req :params (json->clojure params))))))

(defn- has-client-token?
  [{:keys [headers] :as request}]
  (let [convert (clojure.walk/keywordize-keys headers)]
    (= (:client-api-token convert) (get-settings :token :client-api-token))))

(defn with-authenticated-request
  [handler]
  (fn [request]
    (if (has-client-token? request)
      (handler request)
      {:status 401
       :body "client-api-token is missing or invalid"
       :headers {"Content-Type" "text/plain"}})))
