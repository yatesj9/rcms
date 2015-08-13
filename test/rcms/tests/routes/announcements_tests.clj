(ns rcms.tests.routes.announcements-tests
  (:require [rcms.handler :refer [app]]
            [rcms.models.announcements :as an]
            [rcms.config :refer [get-settings]]
            [cheshire.custom :refer [decode]]
            [rcms.tests.helper :refer [build-req]])
  (:use [midje.sweet]
        [ring.mock.request :refer [request]]))

(facts "Midje Tests"
  (fact "3 + 89 = 92"
    (+ 3 89) => 92))

(facts "Facts about announcement Resource, GET/POST/DELETE"
  (fact "GET - Should return all announcements in DB"
    (decode (:body (app (build-req (request :get "/announcements")))) true)
      => (an/get-all-valid-announcements))
(fact "GET - Should return specific annoucnement from DB"
    (decode (:body (app (build-req (request :get "/announcements/Welcome")))) true)
      => (an/get-announcement "Welcome"))
(fact "POST - Should add announcement to DB"
    (app (assoc (build-req (request :post "/announcements"))
                  :params {:title "More"
                           :body "Another more"
                           :expires_at (+ 203934939 (System/currentTimeMillis))}))
    (dissoc (last (an/get-all-valid-announcements)) :id :created_at :expires_at)
      => {:title "More"
          :body "Another more"})
(fact "DELETE - Should delete announcement from DB"
    (app (build-req (request :delete "/announcements/More")))
    (an/get-announcement "More") => '()))
