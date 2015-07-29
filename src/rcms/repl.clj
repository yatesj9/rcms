(ns rcms.repl
  (:require [rcms.config :refer [set-mode!
                                 get-settings]]
            [rcms.db :as sql])
  (:use rcms.handler
        ring.server.standalone
        [ring.middleware file-info file]))

(defonce server (atom nil))

(defn get-handler []
  ;; #'app expands to (var app) so that when we reload our code,
  ;; the server is forced to re-resolve the symbol in the var
  ;; rather than having its own copy. When the root binding
  ;; changes, the server picks it up without having to restart.
  (-> #'app
    ; Makes static assets in $PROJECT_DIR/resources/public/ available.
    (wrap-file "resources")
    ; Content-Type, Content-Length, and Last Modified headers for files in body
    (wrap-file-info)))

(defn start-server
  "used for starting the server in development mode from REPL"
  [mode & [port]]
  (let [port (if port (Integer/parseInt port) 8080)]
    (do (set-mode! mode)
         (sql/set-connection! (sql/pooled-datasource (get-settings :database :connection)))
    (reset! server
            (serve (get-handler)
                   {:port port
                    :init init
                    :auto-reload? true
                    :destroy destroy
                    :join true
                    :open-browser? false})))))

(defn stop-server []
  (.stop @server)
  (reset! server nil))
