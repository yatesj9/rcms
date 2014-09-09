(ns rcms.common
  (:require [clojure.set :refer [rename-keys]]))

(def ^:private kebab->snake-replacements
     {:file-name :file_name
      :folder-name :folder_name
      :new-name :new_name
      :updated-at :updated_at
      :employee-id :employee_id
      :folder-order :folder_order
      :links-order :links_order})

(defn kebab->snake
  [snake-case-map]
  (rename-keys snake-case-map kebab->snake-replacements))
