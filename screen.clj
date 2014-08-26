;Start rcms server onport 8080
(require [rcms.repl :as rpl])
(rpl/start-server)

(rpl/stop-server)
