(ns files.middleware.logger)

(defn- getTimestamp []
  (str "[" (.format (java.text.SimpleDateFormat. "dd/LLL/yyyy") (new java.util.Date)) "]"))

(defn- getRequestMethod [request]
  (let [method (:request-method request)]
    (cond
      (= method :get) "GET"
      (= method :post) "POST"
      (= method :put) "PUT"
      (= method :delete) "DELETE"
      :else method)))

(defn logger [handler]
  (fn [request]
    (let [response (handler request)
          timestamp (getTimestamp)
          method (getRequestMethod request)
          reqURI (:uri request)
          version (:protocol request)
          resCode (:status response)]
      (println (str timestamp " \"" method " " reqURI " " version "\" " resCode))
      response)))

