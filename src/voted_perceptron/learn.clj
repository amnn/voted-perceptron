(ns voted-perceptron.learn
  (:require [voted-perceptron.vec :refer :all]
            [amalloy.ring-buffer :refer :all]
            [incanter.distributions :refer [draw]]))

(defn- sign [x] (if-not (neg? x) 1 -1))

(defn- classify*
  "Given the normal to a linear separator, and a sample in its vector space,
  classify it, (using a 1 or -1)."
  [w x] (sign (v* w x)))

(defn- majority [ys]
  (let [{pos 1 neg -1
         :or {pos 0 neg 0}}
        (frequencies ys)]
    (cond
      (= pos neg) (draw [-1 1])
      (< pos neg) -1
      (> pos neg)  1)))

(defn- update-separator
  "Given the normal to the current linear separator `w`, and the next sample
  point and its label, return an updated normal."
  [w [x y*]]
  (let [y (classify* w x)]
    (if (= y y*)
      w
      (v+ w (s* y* x)))))

(defn- update-separators
  "Given the normal to the last linear separator `last-w`, the queue of
  separators we're updating `ws`, the next sample point `x` and its label,
  update the latest separator and queue."
  [[last-w ws] xy]
  (let [new-w (update-separator last-w xy)]
    [new-w (conj ws new-w)]))

(defn classify
  "Given a sequence of linear separators `ws`, and a sample in their vector
  space `x`, classify it as the majority of the classifications of the
  separators in the sequence."
  [ws x] (majority (map #(classify* % x) ws)))

(defn learn
  "Return a function that when given a sample `sample` returns up to `T`
  linear separators of dimension `N`, by applying the voted perceptron
  algorithm on the sample, repeated `R` times."
  [N T R]
  (let [init-w (repeat N 0)
        ws     (conj (ring-buffer T) init-w)]
    (fn [sample]
      (->> (cycle sample)
           (take (* R (count sample)))
           (reduce update-separators
                   [init-w ws])
           last))))
