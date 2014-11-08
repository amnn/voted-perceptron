(ns voted-perceptron.learn
  (:require [voted-perceptron.vec :refer :all]
            [incanter.distributions :refer [draw]]))

(defn- sign [x] (if-not (neg? x) 1.0 -1.0))
(defn- classify* [w x] (sign (v* w x)))

(defn- majority [ys]
  (let [{pos 1.0 neg -1.0
         :or {pos 0 neg 0}}
        (frequencies ys)]
    (cond
      (= pos neg) (draw [-1.0 1.0])
      (< pos neg) -1.0
      (> pos neg)  1.0)))

(defn update-separator
  "Given the normal to the current linear separator `w`, and the next sample
  point and its label, return an updated normal."
  [w [x y*]]
  (let [y (classify* w x)]
    (if (= y y*)
      w
      (v+ w (s* y x)))))

(defn learn
  "Return a function that when given a sample `xys` returns up to `T` linear
  separators of dimension `N`."
  [N T]
  (let [init-w (repeat N 0)]
    (fn [xys]
      (let [len (count xys)
            T'  (max 0 (- len T))]
        (->> xys
             (reductions update-separator
                         init-w)
             (drop T'))))))

(defn classify
  "Given a sequence of classifiers, `ws`, and a sample point `x`, return
  a classification: -1.0 or 1.0."
  [ws x] (majority (map #(classify % x) ws)))
