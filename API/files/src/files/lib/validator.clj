(ns files.lib.validator)

;; TODO: 
;; Query games API to see if exists?
(defn isValidGameID [gameID]
  (not (nil? (re-matches #"^[a-zA-Z0-9]{20,30}$" gameID))))

(defn isValidType [type]
  (not (nil? (re-matches #"^main|resource$" type))))


;; FILE VALIDATION

;;TODO:
;; 1. Virus scan
;; 2. Content type validation -> actual content, don't trust header or extension

(defn- _isValidFilename [filename]
  (not (nil? (re-matches #"^[a-zA-Z0-9]{1,25}\.[a-zA-Z0-9]{1,5}$" filename))))

;; TODO:
;; Allow also other types, such as wasm etc.
(defn- hasValidExtension [filename] 
  (not (nil? (re-matches #"^.+\.(html|js|css)$" filename))))

(defn- hasAllowedSize [file]
  (let [megabyte 1048576
        maxSize (* megabyte 5)
        fileSize (.length (get file :tempfile))]
    (< fileSize maxSize)))

(defn isValidFilename [filename]
  (cond (not (_isValidFilename filename)) false
        (not (hasValidExtension filename)) false
        :else true))

(defn isValidFile [file] 
  (cond
    (not (isValidFilename (get file :filename))) false
    (not (hasAllowedSize file)) false
    :else true))