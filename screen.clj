;Start rcms server onport 8080
(require '[rcms.repl :as rpl])
(rpl/start-server)

(rpl/stop-server)

(require '[me.raynes.fs :as fs])

(fs/directory? "resources/files/tests")

(fs/mkdir "resources/files/tests")

(fs/delete-dir "resources/files/tests")

(fs/copy-dir "resources/files/tests" "resources/files/tests2")
