(ns rcms.common
  (:require [clojure.set :refer [rename-keys]]))

(def ^:private kebab->snake-replacements
     {:file-name :file_name
      :folder-name :folder_name
      :updated-at :updated_at})

(defn kebab->snake
  [snake-case-map]
  (rename-keys snake-case-map kebab->snake-replacements))
