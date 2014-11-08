(ns voted-perceptron.sample
  (:require [clojure.java.io :refer [reader]]))

(defn- f-name [dir n]
  (str dir "/digit" n ".txt"))

(defn parse-line [digit line]
  [digit (read-string (str \[ line \]))])

(defn- read-digit-sample [dir digit]
  (with-open [file (reader (f-name dir digit))]
    (->> file line-seq
         (map (partial parse-line
                       digit))
         doall)))

(defn samples
  "Take a directory, `dir`, and read a sample in contained in that directory."
  [dir]
  (shuffle (mapcat (partial read-digit-sample dir)
                   (range 10))))
