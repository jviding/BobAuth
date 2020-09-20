(ns files.lib.validator)

;; TODO: Query games API to see if exists?
(defn isValidGameID [gameID]
  (not (nil? (re-matches #"^[a-zA-Z0-9]{20,30}$" gameID))))

(defn isValidType [type]
  (not (nil? (re-matches #"^main|resource$" type))))


;; FILE VALIDATION

;;TODO:
;; 1. Virus scan
;; 2. Content type validation -> actual content, don't trust header or extension

(defn- hasValidName [file]
  (not (nil? (re-matches #"^[a-zA-Z0-9]{1,25}\.[a-zA-Z0-9]{1,5}$" (get file :filename)))))

(defn- hasValidExtension [file] 
  (not (nil? (re-matches #"^.+\.(html|js|css)$" (get file :filename)))))

(defn- hasAllowedSize [file]
  (let [megabyte 1048576
        maxSize (* megabyte 5)
        fileSize (.length (get file :tempfile))]
    (< fileSize maxSize)))

(defn isValidFile [file] 
  (cond
    (not (hasValidName file)) false
    (not (hasValidExtension file)) false
    (not (hasAllowedSize file)) false
    :else true))