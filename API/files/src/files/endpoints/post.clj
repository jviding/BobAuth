(ns files.endpoints.post
  (:require [clojure.java.io :as io]
            [files.lib.validator :refer [isValidGameID, isValidType, isValidFile]]
            [files.lib.converter :refer [hexify]]
            [ring.util.response :refer [response, bad-request]]))


(defn- hasDir [gameID]
  (.exists (io/file (str "/uploads/" gameID))))

(defn- deleteMainFile [gameID]
  (let [files (.listFiles (io/file (str "/uploads/" gameID "/main/")))]
    (doseq [file files] (io/delete-file (.getPath file)))))

(defn- saveFile [gameID type file]
  (if (= type "main") (deleteMainFile gameID) false)
  (let [input (get file :tempfile)
        filenameAsHex (hexify (get file :filename))
        filePath (str "/uploads/" gameID "/" type "/" filenameAsHex)
        output (java.io.File. filePath)]
    (io/copy input output)
    {:message "Success"}))


(defn createDirectory [gameID]
  (cond
    (not (isValidGameID gameID)) (bad-request {:message "Invalid gameID"})
    (hasDir gameID) (bad-request {:message "Game already exists"})
    :else (do
            (.mkdir (io/file (str "/uploads/" gameID)))
            (.mkdir (io/file (str "/uploads/" gameID "/main")))
            (.mkdir (io/file (str "/uploads/" gameID "/resource")))
            (response {:message "Success"}))))

(defn postFile [gameID type file]
  (cond
    (not (isValidGameID gameID)) (bad-request {:message "Invalid gameID"})
    (not (isValidType type)) (bad-request {:message "Invalid type"})
    (not (isValidFile file)) (bad-request {:message "Invalid file"})
    (not (hasDir gameID)) (bad-request {:message "Unknown gameID"})
    :else (response (saveFile gameID type file))))
