(defproject voted_perceptron "0.1.0-SNAPSHOT"
  :description "Implementation of the Voted Perceptron algorithm used to learn digits."

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [incanter/incanter-core "1.5.5"] ]

  :main ^:skip-aot voted-perceptron.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
