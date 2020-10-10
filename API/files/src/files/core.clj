;; Ring documentation
;; https://github.com/ring-clojure/ring/wiki
;; Compojure documentation
;; https://github.com/weavejester/compojure/wiki

(ns files.core
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :refer [defroutes, GET, POST, DELETE]]
            [compojure.route :as route]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-body, wrap-json-response]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [files.endpoints.get :refer [getFile, getFilesInfo]]
            [files.endpoints.post :refer [createDirectory, postFile]]
            [files.endpoints.delete :refer [deleteDirectory, deleteFile]]
            [files.middleware.logger :refer [logger]]))

;; TODO
;; Schema + validation
(defroutes app
  (GET "/:gameID" request
    (let [gameID (get (get request :route-params) :gameID)]
      (getFilesInfo gameID)))
  (GET "/:gameID/:type/:filename" request 
    (let [gameID (get (get request :route-params) :gameID)
          type (get (get request :route-params) :type)
          filename (get (get request :route-params) :filename)]
      (getFile gameID type filename)))
  (POST "/:gameID" request
    (let [gameID (get (get request :route-params) :gameID)]
      (createDirectory gameID)))
  (POST "/:gameID/:type" request
    (let [gameID (get (get request :route-params) :gameID)
          type (get (get request :route-params) :type)
          file (get (get request :multipart-params) "file")]
      (postFile gameID type file)))
  (DELETE "/:gameID" request
    (let [gameID (get (get request :route-params) :gameID)]
      (deleteDirectory gameID)))
  (DELETE "/:gameID/:type/:filename" request
    (let [gameID (get (get request :route-params) :gameID)
          type (get (get request :route-params) :type)
          filename (get (get request :route-params) :filename)]
      (deleteFile gameID type filename)))
  (route/not-found "<h1>Page not found</h1>"))

(def middleware
  (-> app
      wrap-multipart-params
      wrap-json-body
      wrap-json-response
      wrap-reload
      logger))

(defn -main
  []
  (jetty/run-jetty middleware {:port 9090}))