(ns rcms.tests.routes.links-tests
  (:require [rcms.handler :refer [app]]
            [rcms.models.links :as ln]
            [cheshire.custom :refer [decode]]
            [rcms.tests.helper :refer [initialize-test-connection
                                       link-schema
                                       link-data
                                       build-req
                                       ]]
            [rcms.models.links :refer [get-links
                                       get-link
                                       save-link
                                       remove-link]]
            [rcms.db :refer [drop-table!
                             create-table!
                             populate]]
            [rcms.config :refer [get-settings
                                 set-mode!]])
  (:use [midje.sweet]
        [ring.mock.request :refer [request]]))

(def initialize-db
  (do (set-mode! :test)
    (let [c (initialize-test-connection)
          _ (drop-table! c (:table link-schema))
          _ (create-table! c (:table link-schema) (:fields link-schema))
          _ (populate c :links link-data)])))

(facts "Midje Test"
  (fact "2 * 45 = 90"
    (* 2 45) => 90))

(facts "Facts about link Resource, GET/PUT/POST/DELETE"
  (fact "GET - Should return all links from db"
    (decode (:body (app (build-req (request :get "/links"))))true)
      => (ln/get-links))

  (fact "POST - Should add link to db"
    (app (assoc (build-req (request :post "/links"))
                        :params {:name "crew" :href "http://crew.casinorama.com"}))
    (dissoc (last (ln/get-links)) :id)
      => {:name "crew" :href "http://crew.casinorama.com" :link_order nil})

  (fact "DELETE - Should remove link from db"
    (app (build-req (request :delete "/links/crew")))
    (ln/get-link "crew") => '()))
