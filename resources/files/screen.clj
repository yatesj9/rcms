; ssh 10.2.1.222
; sdspass1

; stopping the job
; sy -s | grep sal
; su -c kill <pid>
; canadians

; starting the job
; su sds –c /usr/projects/sds/1,3/sal –d9
; sdscr

; monitoring
;less  /usr/projects/sds/2,5/SDT.log
;

(require '[gamekeeper.test.consumers.adept-db-tests :reload true])

(require '[clojure.string :as str])
(require '[clojure.java.jdbc :as sql])

(require '[rama.connections.bone :as bone])

(def csql {
        :class-for-name "com.microsoft.sqlserver.jdbc.SQLServerDriver"
        :url "jdbc:sqlserver://slotalertdev:1433;database=slotDispatchSysCR"
        :username "rama_gk_listener"
        :password "rama_gk_listener"
        :log-statements? false
        :min-connections 1
        :max-connections 2
        :partitions 2})

(def conn (bone/make-connection csql))



(sql/with-connection conn
  (sql/with-connection conn
    (sql/do-prepared "{call addName (?, ?)}"
                     [5633 "SD"]
                     [1099 "TETS1"])))

(require '[gamekeeper.consumers.common-functions :reload true])

(def cas400 {:class-for-name "com.ibm.as400.access.AS400JDBCDriver"
             :url "jdbc:as400://as400prod"
            :username "MKTODBC"
             :password "SQUIRREL"
             :min-connections 2
             :max-connections 3
             :log-statements? false
             :partitions 2})

(def conn (bone/make-connection cas400))

(defn to-account
  [r]
  {:id (int (:acctcn r))
   :card-type (str/trim (:mtypmm r))
   :first-name (str/trim (:pfnmcn r))
   :last-name (str/trim (:plnmcn r))})

(sql/with-connection conn
  (sql/with-query-results rs
    [sql 10029 100]
    (map to-account (doall rs))))

(defn get-accounts-from-db
  [ids]
  (let [sql (str "select acctcn, pfnmcn, plnmcn, mtypmm "
                 "from lsicasdtap.cfldpcn1 join lsicasdtap.cflclmm1 "
                 "on acctcn=acctmm "
                 "where acctcn in ("
                 (str/join "," (repeat (count ids) "?"))
                 ")")
        parms (vec (cons sql ids))]
    (sql/with-connection conn
      (sql/with-query-results rs parms
        (map to-account (doall rs))))))

(get-accounts-from-db ["916646"])



(str/join "," (repeat 5 "?"))

;; accounts
[
 {:id 1M :first-name "ROBERT" :last-name "SAROLI"}
 {:id 2M :first-name "BURT" :last-name "ABRAJAM"}
 {:id 3M :first-name "BLAITTALAH" :last-name "ALIAS"}
 {:id 10029M :first-name "DMTEST" :last-name "SMITH"}]

(def c
  {:listener {:port 9876}
   :consumers {
               :redis {:conn-pool {:max-active 3}
                       :conn-spec {:host "127.0.0.1" :port 6379 :db 1}
                       :name :test}
               :adept-db {:queue :adept-db
                          :pop-count 5
                          :recurrence 20
                          :expiry (* 1000 1) ; 1 second
                          :alerts {:read-stored-proc nil
                                   :write-stored-proc nil
                                   :cache :adept-dbi-alertable-ids
                                   :cache-for 10 ; 10 seconds
                                   }
                          :patrons {:table "patrons"
                                    }}
               :exception-counts {:queue :exception-counts
                                  :pop-count 5
                                  :recurrence 20}
               :gamekeeper-db {:queue :gamekeeper-db
                               :pop-count 5
                               :recurrence 20
                               :db {:table "tblTransactLog"
                                    }}}})
(map #(:queue (last %))
     (select-keys (:consumers c)
                  [:adept-db :exception-counts :gamekeeper-db]))


(require '[taoensso.carmine :as c])

(require '[gamekeeper.redis :as r :reload true])

(r/set-pool! {:max-active 3})

((r/set-pool! {:max-active 3})r/set-connection! :d
  (r/make-connection {:db 1 :port 6379 :host "10.2.1.149"}))

(def p (c/make-conn-pool :max-active 3))
(def c (c/make-conn-spec :db 1
                         :port 6379
                         :host "10.2.1.149"))

(def m (c/with-conn p c (c/get "adept-alertable-ids")))

(r/pop-some :adept-db 10 :d)

(r/get-values [] :d)

(println m)

(c/with-conn p c
  (c/rpush "exception-counts" "gk10:533"))

(def l (c/with-conn p c (c/get "adept-alertable-ids")))
(c/with-conn p c (c/get "adept-alertable-ids"))

(c/with-conn p c (c/get "patrons:916646"))

(require '[clojure.string :as str])

(apply str (filter #(re-seq #"[0-9]" (str %)) (drop 2 "TEST5")))

(str/replace )
