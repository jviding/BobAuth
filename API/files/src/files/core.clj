(ns files.core
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :refer [defroutes, GET, POST, DELETE]]
            [compojure.route :as route]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-body]]
            [files.endpoints.getFile :refer [getFile]]
            [files.endpoints.postFile :refer [postFile]]
            [files.endpoints.deleteFile :refer [deleteFile]]))

(defroutes app
  (GET "/" [request] (getFile request))
  (POST "/" [request] (postFile request))
  (DELETE "/" [request] (deleteFile request))
  (route/not-found "<h1>Page not found</h1>"))


;;  {:status 200
;;   :headers {"Content-Type" "text/plain"}
;;   :body "Hello World"}

(def middleware
  (wrap-reload 
   (wrap-json-body app)))

(defn -main
  [& args]
  (jetty/run-jetty middleware {:port 9090}))