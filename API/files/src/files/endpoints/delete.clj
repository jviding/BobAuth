(ns files.endpoints.delete
  (:require [clojure.java.io :as io]
            [files.lib.validator :refer [isValidGameID, isValidType]]
            [files.lib.converter :refer [hexify]]))

;; TODO
;; Write IOException to logs
(defn- delete [pathToFile] 
  (try 
      (io/delete-file pathToFile)
      (str "<h1>Deleted</h1>")
    (catch java.io.IOException e 
        (println "Caught exception: " (.getMessage e))
        (str "<h1>Couldn't delete</h1>"))))

(defn deleteDir [pathToDirectory]
  (let [dirContents (.listFiles (io/file pathToDirectory))
        files (filter #(.isFile %) dirContents)
        dirs (filter #(.isDirectory %) dirContents)]
    (doseq [file files] (delete (.getPath file)))
    (doseq [dir dirs] (deleteDir (.getPath dir)))
    (delete pathToDirectory)))


;; TODO
;; Return response as JSON
;; Return correct status code
(defn deleteFile [gameID type filename]
  (let [filenameHex (hexify filename)]
    (cond
      (not (isValidGameID gameID)) (str "<h1>Invalid gameID</h1>")
      (not (isValidType type)) (str "<h1>Invalid type</h1>")
      :else (delete (str "/uploads/" gameID "/" type "/" filenameHex)))))

(defn deleteDirectory [gameID]
  (cond
    (not (isValidGameID gameID)) (str "<h1>Invalid gameID</h1>")
    :else (deleteDir (str "/uploads/" gameID))))