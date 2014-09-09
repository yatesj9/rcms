(ns rcms.tests.routes.links-tests
  (:require [rcms.handler :refer [app]]
            [rcms.models.links :as ln]
            [cheshire.custom :refer [decode]])
  (:use [midje.sweet]
        [ring.mock.request :refer [request]]))

(facts "Midje Test"
  (fact "2 * 45 = 90"
    (* 2 45) => 90))

(facts "Facts about link Resource, GET/PUT/POST/DELETE"
  (fact "GET - Should return all links from db"
    (decode (:body (app (request :get "/links")))true)
      => (ln/get-links))

  (fact "POST - Should add link to db"
    (app (assoc (request :post "/links")
                        :params {:name "crew" :href "http://crew.casinorama.com"}))
    (dissoc (last (ln/get-links)) :id)
      => {:name "crew" :href "http://crew.casinorama.com" :links_order nil})

  (fact "DELETE - Should remove link from db"
    (app (request :delete "/links/crew"))
    (ln/get-link "crew") => '()))
