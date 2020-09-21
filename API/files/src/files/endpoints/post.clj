(ns files.endpoints.post
  (:require [clojure.java.io :as io]
            [files.lib.validator :refer [isValidGameID, isValidType, isValidFile]]
            [files.lib.converter :refer [hexify]]))


(defn- hasFolder [gameID] 
  (.exists (io/file (str "/uploads/" gameID))))

(defn- createFolder [gameID] 
  (.mkdir (io/file (str "/uploads/" gameID)))
  (.mkdir (io/file (str "/uploads/" gameID "/main")))
  (.mkdir (io/file (str "/uploads/" gameID "/resources"))))

(defn- saveFile [gameID type file]
  (if (hasFolder gameID) nil (createFolder gameID))
  (let [input (get file :tempfile)
        filenameAsHex (hexify (get file :filename))
        filePath (str "/uploads/" gameID "/" type "/" filenameAsHex)
        output (java.io.File. filePath)]
    (io/copy input output)
    (str "<h1>Success</h1>")))

;; TODO:
;; Return error messages as JSON
;; Return success no body 200
(defn postFile [gameID type file]
  (cond
    (not (isValidGameID gameID)) (str "<h1>Invalid gameID</h1>")
    (not (isValidType type)) (str "<h1>Invalid type</h1>")
    (not (isValidFile file)) (str "<h1>Invalid file</h1>")
    :else (saveFile gameID type file)))