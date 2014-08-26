(ns rcms.tests.models.folders-tests
  (:require [rcms.models.folders :refer [get-directories
                                         create-directory
                                         remove-directory
                                         rename-directory]])
  (:use [expectations :refer [expect]]))

(def resource-path
  "resources/files/tests")

;Create directory, return true
(expect true (create-directory resource-path))

;Error if directory exists, return error
(expect {:error "Directory already exists"} (create-directory resource-path))

;Delete directory, return true
(expect true (remove-directory resource-path))

;Delete directory that does not exist, return error
(expect {:error "Directory does not exist"} (remove-directory resource-path))

;Create and Rename directory, than remove new named directory
(expect true (create-directory resource-path))
(expect true (rename-directory resource-path "resources/files/tests2"))

(expect true (create-directory resource-path))
(expect '("tests2" "tests") (get-directories))

;CLEAN remove directories
(expect true (remove-directory resource-path))
(expect true (remove-directory "resources/files/tests2"))
