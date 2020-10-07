(ns files.endpoints.post
  (:require [clojure.java.io :as io]
            [files.lib.validator :refer [isValidGameID, isValidType, isValidFile]]
            [files.lib.converter :refer [hexify]]
            [ring.util.response :refer [response, bad-request]]))


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
    {:message "Success"}))


(defn postFile [gameID type file]
  (cond
    (not (isValidGameID gameID)) (bad-request {:message "Invalid gameID"})
    (not (isValidType type)) (bad-request {:message "Invalid type"})
    (not (isValidFile file)) (bad-request {:message "Invalid file"})
    :else (response (saveFile gameID type file))))