(defproject rcms "0.1.0-SNAPSHOT"
  :description "Responsive Content Management System"
  :url "https://github.com/yatesj9/rcms"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 [lib-noir "0.8.6"]
                 [expectations "2.0.9"]
                 [me.raynes/fs "1.4.6"]
                 [log4j/log4j "1.2.17"]
                 [org.slf4j/slf4j-log4j12 "1.7.7"]
                 [org.clojure/tools.logging "0.3.0"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [com.jolbox/bonecp "0.8.0.RELEASE"]
                 [com.h2database/h2 "1.4.181"]
                 [cheshire "4.0.1"]
                 [ring/ring-json "0.3.1"]]
  :plugins [[lein-ring "0.8.10"]
            [lein-autoexpect "1.2.2"]]
  :ring {:handler rcms.handler/app
         :init rcms.handler/init
         :destroy rcms.handler/destroy}
  :aot :all
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}})
