(ns voted-perceptron.vec)

(defn v+
  "Vector addition"
  [& xss ] (apply map + xss))

(defn v*
  "Vector dot product"
  [xs ys] (reduce + (map * xs ys)))

(defn s*
  "scalar/vector multiplication"
  [sf xs] (map #(* sf %) xs))

(defn v-avg
  "Take the average of the collection of vectors"
  [xs] (s* (/ 1.0 (count xs)) (reduce v+ xs)))
