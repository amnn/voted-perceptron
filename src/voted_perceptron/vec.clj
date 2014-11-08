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

