(defproject voted_perceptron "0.1.0-SNAPSHOT"
  :description "Implementation of the Voted Perceptron algorithm used to learn digits."

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [amalloy/ring-buffer "1.0"]
                 [incanter/incanter-charts "1.5.5"]
                 [incanter-gorilla "0.1.0"]
                 [incanter/incanter-core "1.5.5"]]

  :plugins [[lein-gorilla "0.3.3"]]

  :main ^:skip-aot voted-perceptron.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
