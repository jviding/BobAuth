(ns files.endpoints.get
  (:require
   [clojure.java.io :as io]
   [files.lib.converter :refer [hexify, unhexify]]
   [files.lib.validator :refer [isValidGameID, isValidType, isValidFilename]]
   [ring.util.response :refer [file-response, response, bad-request]]))


(defn- listFilenames [gameID type]
  (let [fNames (.list (io/file (str "/uploads/" gameID "/" type)))]
    (map unhexify fNames)))


(defn getFilesInfo [gameID]
  (cond
    (not (isValidGameID gameID)) (bad-request {:message "Invalid gameID"})
    (not (.isDirectory (io/file "/uploads/" gameID))) (bad-request {:message "Unknown gameID"})
    :else (let [main (listFilenames gameID "main")
                resources (listFilenames gameID "resource")]
            (response {:main main :resources resources}))))

(defn getFile [gameID type filename]
  (cond
    (not (isValidGameID gameID)) (bad-request {:message "Invalid gameID"})
    (not (isValidType type)) (bad-request {:message "Invalid type"})
    (not (isValidFilename filename)) (bad-request {:message "Invalid filename"})
    (not (.exists (io/file (str "/uploads/" gameID "/" type "/" (hexify filename)))))
        (bad-request {:message "File not found"})
    :else (file-response (str "/uploads/" gameID "/" type "/" (hexify filename)))))
