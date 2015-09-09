(ns rcms.config)

(def mode (atom :development))

(defn get-mode
  []
  @mode)

(defn set-mode!
  [new-mode]
  (reset! mode new-mode))

(def load-config
     (load-file "resources/custom_config.clj"))

(def ^:private tsettings {
     :resource {
      :path "resources/files/"}
     :token {
      :client-api-token "GUID"}
     :image {
      :size 1024
      :thumb-size 150}
     :database {
      :connection {
        :url "jdbc:h2:mem:testdb"
        :min-connections 1
        :max-connections 1
        :partitions 1
        :log-statements? true}}})

(def ^:private dsettings {
     :resource {
      :path (-> load-config
                :resource
                :dev-path)}
     :token {
       :client-api-token (-> load-config
                             :token
                             :dev-client-api-token)}
     :image {
       :size (-> load-config
                 :image
                 :dev-size)
       :thumb-size (-> load-config
                       :image
                       :dev-thumb-size)}
     :database {
      :connection {
        :url (-> load-config
                 :database
                 :dev-url)
        :username (-> load-config
                      :database
                      :dev-username)
        :password (-> load-config
                      :database
                      :dev-password)
        :min-connections 1
        :max-connections 1
        :partitions 1
        :log-statements? true}}})

(def ^:private psettings {
     :resource {
      :path (-> load-config
                :resource
                :prod-path)}
     :token {
       :client-api-token (-> load-config
                             :token
                             :prod-client-api-token)}
     :image {
       :size (-> load-config
                 :image
                 :prod-size)
       :thumb-size (-> load-config
                       :image
                       :prod-thumb-size)}
     :database {
      :connection {
        :url (-> load-config
                 :database
                 :prod-url)
        :username (-> load-config
                      :database
                      :prod-username)
        :password (-> load-config
                      :database
                      :prod-password)
        :min-connections 1
        :max-connections 1
        :partitions 1
        :log-statements? true}}})

(def opts {
  :test tsettings
  :development dsettings
  :production psettings})

(defn get-settings
  [& selections]
  (let [choices (if ((first selections)opts)
                  selections
                  (cons @mode selections))]
    (get-in opts choices)))
