(ns voted-perceptron.sample
  (:require [clojure.java.io :refer [reader]]))

(defn- f-name [dir n]
  (str dir "/digit" n ".txt"))

(defn- parse-line [digit line]
  [(read-string (str \[ line \])) digit])

(defn- read-chunked-digit-sample [n dir digit +ve?]
  (with-open [file (reader (f-name dir digit))]
    (->> file line-seq
         (map (partial parse-line
                       (if +ve? 1 -1)))
         (partition n)
         doall)))

(defn digit-pair-sample
  "Take a directory, `dir`, and read a sample contained in that directory into
  `n` chunks."
  [n dir i j]
  (->> [i j]
       (map #(read-chunked-digit-sample n dir % (= i %)))
       (apply map interleave)
       vec))

(defn pick-test-set
  "Picks a chunk `ts` in the samples to be the test set, and combines the rest
  into a single training set."
  [ts samples]
  {:test  (samples ts)
   :train (lazy-seq
            (apply concat
                   (keep-indexed
                     #(when (not= % ts) %2)
                     samples)))})
