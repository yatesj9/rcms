(ns rcms.tests.routes.tags-tests
  (:require [rcms.handler :refer [app]]
            [rcms.models.tags :as tg]
            [cheshire.custom :refer [decode]])
  (:use [midje.sweet]
        [ring.mock.request :refer [request]]))

(facts "Midje Test"
  (fact "10 - 6 = 4"
    (- 10 6) => 4))

(facts "Facts about tag Resource, GET/POST/PUT/DELETE"
  (fact "GET - Should return all tags from DB"
    (decode (:body (app (request :get "/tags"))) true)
      => (tg/get-all-tags))

  (fact "GET - Should return tags from specific folder"
    (decode (:body (app (request :get "/tags/People"))) true)
      => (tg/get-folder-tags "People"))

  (fact "POST - Should add tag to DB"
    (app (assoc (request :post "/tags")
                       :params {:name "Boring" :folderName "People"}))
    (dissoc (last (tg/get-folder-tags "People")) :id)
      => {:name "Boring" :folder_name "People"})

  (fact "DELETE - Should remove tag from DB"
    (app (request :delete "/tags/People/Boring"))
    (tg/get-folder-tag-name {:name "Boring" :folder "People"}) => '()))
