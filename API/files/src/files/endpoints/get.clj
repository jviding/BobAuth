(ns files.endpoints.get
  (:require 
   [clojure.java.io :as io]
   [files.lib.converter :refer [hexify, unhexify]]
   [files.lib.validator :refer [isValidGameID, isValidType, isValidFilename]]
   [ring.util.response :refer [file-response]]))


(defn- listFilenames [gameID type] 
  (let [fNames (.list (io/file (str "/uploads/" gameID "/" type)))]
    (map unhexify fNames)))

;; TODO
;; Check if dir exists
(defn getFilesInfo [gameID] 
  (cond
    (not (isValidGameID gameID)) (str "<h1>Invalid gameID</h1>")
    :else (let [main (listFilenames gameID "main")
                resources (listFilenames gameID "resources")]
    (str "<h1>" {:main main :resources resources} "</h1>"))))

;; TODO
;; Check if file exists
(defn getFile [gameID, type, filename]
  (cond
    (not (isValidGameID gameID)) (str "<h1>Invalid gameID</h1>")
    (not (isValidType type)) (str "<h1>Invalid type</h1>")
    (not (isValidFilename filename)) (str "<h1>Invalid filename</h1>")
    :else (let [filenameHex (hexify filename)
                filePath (str "/uploads/" gameID "/" type "/" filenameHex)]
            (file-response filePath))))