(ns golden-crown.core
  (:require [golden-crown.domain.core :as domain]
            [golden-crown.gateway.cli :as cli])
  (:gen-class))

;; Initialize the cli and call the domain function for processing each message.
;; Print the usage

(defn- start-reading-input
  "Indefinitely read from the cli and call the callback function with the input"
  [processing-fn]
  (doseq [ln (cli/read-line)]
    (println (processing-fn ln))
    (processing-fn ln)))

(defn -main
  []
  (domain/init)
  (let [output (start-reading-input domain/process-message)]
    (when output
      (cli/put output))))
