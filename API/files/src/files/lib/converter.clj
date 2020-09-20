(ns files.lib.converter)

(defn hexify [s]
  (format "%x" (new java.math.BigInteger (.getBytes s))))

(defn unhexify [h]
  (apply str
         (map
          (fn [[x y]] (char (Integer/parseInt (str x y) 16)))
          (partition 2 h))))