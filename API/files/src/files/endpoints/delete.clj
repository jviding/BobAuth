(ns files.endpoints.delete
  (:require [clojure.java.io :as io]
            [files.lib.validator :refer [isValidGameID, isValidType, isValidFilename]]
            [files.lib.converter :refer [hexify]]
            [ring.util.response :refer [response, bad-request]]))


(defn- _deleteFile [pathToFile] (io/delete-file pathToFile))

(defn deleteDir [pathToDirectory]
  (let [dirContents (.listFiles (io/file pathToDirectory))
        files (filter #(.isFile %) dirContents)
        dirs (filter #(.isDirectory %) dirContents)]
    (doseq [file files] (_deleteFile (.getPath file)))
    (doseq [dir dirs] (deleteDir (.getPath dir)))
    (_deleteFile pathToDirectory)))


;; TODO
;; Write IOException to logs
(defn deleteFile [gameID type filename]
  (cond
    (not (isValidGameID gameID)) (bad-request {:message "Invalid gameID"})
    (not (isValidType type)) (bad-request {:message "Invalid type"})
    (not (isValidFilename filename)) (bad-request {:message "Invalid filename"})
    :else (let [pathToFile (str "/uploads/" gameID "/" type "/" (hexify filename))]
            (cond
              (not (.exists (io/file pathToFile))) (bad-request {:message "File not found"})
              :else (try 
                      (_deleteFile pathToFile)
                      (response {:message "Success"})
                      (catch java.io.IOException e
                        (println "Caught exception: " (.getMessage e))
                        (bad-request {:message "Something went wrong"})))))))

;; TODO
;; Write IOException to logs
(defn deleteDirectory [gameID]
  (cond
    (not (isValidGameID gameID)) (bad-request {:message "Invalid gameID"})
    :else (let [pathToDir (str "/uploads/" gameID)]
            (cond
              (not (.isDirectory (io/file pathToDir))) (bad-request {:message "Unknown gameID"})
              :else (try
                      (deleteDir pathToDir)
                      (response {:message "Success"})
                      (catch java.io.IOException e
                        (println "Caught exception: " (.getMessage e))
                        (bad-request {:message "Something went wrong"})))))))