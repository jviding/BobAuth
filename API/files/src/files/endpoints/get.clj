(ns files.endpoints.get
  (:require [ring.util.response :refer [file-response]]))

;; gameID and name to hex for sanitization

(defn getFilesInfo [gameID] 
  (str "<h1>Describe " gameID "</h1>"))

(defn getFile [gameID, type, fileName]
  (file-response
   (str "/uploads/" gameID "/" type "/" fileName)))