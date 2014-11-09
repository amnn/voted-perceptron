(ns voted-perceptron.sample
  (:require [clojure.java.io :refer [reader]]))

(defn- f-name [dir n]
  (str dir "/digit" n ".txt"))

(defn parse-line [digit line]
  [(read-string (str \[ line \])) digit])

(defn- read-chunked-digit-sample [n dir digit]
  (with-open [file (reader (f-name dir digit))]
    (->> file line-seq
         (map (partial parse-line
                       digit))
         (partition n)
         doall)))

(defn samples
  "Take a directory, `dir`, and read a sample contained in that directory into
  `n` chunks."
  [n dir]
  (->> (range 10)
       (map #(read-chunked-digit-sample n dir %))
       (apply map concat)
       vec))

(defn pick-test-set [ts samples]
  {:test  (samples ts)
   :train (lazy-seq
            (apply concat
                   (keep-indexed
                     #(when (not= % ts) %2)
                     samples)))})
