(ns voted-perceptron.core
  (:require [voted-perceptron.learn :refer :all]
            [voted-perceptron.sample :refer :all]))

(defn- avg [xs] (/ (reduce + xs) (double (count xs))))

(defn err
  "Calculate the error for the voted perceptron algorithm learnt from `R`
  repetitions of the `train`ing data, with `T` separate votes, and tested on
  the `test`ing data."
  [T R {:keys [test train]}]
  (let [ws ((learn 256 T R) train)]
    (avg (map (fn [[x y*]]
                (if-not (= y* (classify ws x))
                  1 0))
              test))))

(defn x-validate
  "Given a sample, calculate the error when training on all but one sample and
  then testing on the remaining sample, for every sample, and produce the
  average of those errors"
  [T R samples]
  (let [cs (count samples)]
    (->> (range cs)
         (map #(err T R (pick-test-set % samples)))
         avg)))
