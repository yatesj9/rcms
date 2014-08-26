(ns rcms.config)

(def mode (atom :development))

(defn get-mode
  []
  @mode)

(defn set-mode!
  [new-mode]
  (reset! mode new-mode))

(def ^:private tsettings {
     :database {
      :connection {
        :url "jdbc:h2:mem:testdb"
        :min-connections 1
        :max-connections 1
        :partitions 1
        :log-statements? true}}})

(def opts {
  :test tsettings})

(defn get-settings
  [& selections]
  (let [choices (if ((first selections)opts)
                  selections
                  (cons @mode selections))]
    (get-in opts choices)))
