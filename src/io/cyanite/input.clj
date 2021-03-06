(ns io.cyanite.input
  (:require [com.stuartsierra.component :as component]
            [io.cyanite.engine          :as engine]
            [io.cyanite.input.carbon    :as carbon]
            [io.cyanite.input.pickle    :as pickle]
            [io.cyanite.input.tcp       :refer [map->TCPInput]]
            [io.cyanite.utils           :refer [now!]]
            [clojure.tools.logging      :refer [info]]))

(defmulti build-input (comp (fnil keyword "carbon") :type))

(defmethod build-input :pickle
  [options]
  (component/using
   (map->TCPInput (assoc options
                         :port (or (:port options) 2004)
                         :pipeline pickle/pipeline))
   [:engine]))

(defmethod build-input :carbon
  [options]
  (component/using
   (map->TCPInput (assoc options
                         :port (or (:port options) 2003)
                         :pipeline carbon/pipeline))
   [:engine]))
