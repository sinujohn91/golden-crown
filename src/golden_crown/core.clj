(ns golden-crown.core
  (:require [golden-crown.domain.core :as domain]
            [golden-crown.gateway.cli :as cli])
  (:gen-class))

;; Initialize the cli and call the domain function for processing each message.
;; Print the usage

(defn- start-reading-input
  "Indefinitely read from the cli and call the callback function with the input"
  [processing-fn]
  (loop [ln (read-line)]
    (when-not (= "exit" ln)
      (let [output (processing-fn ln)]
        (when output
          (cli/put output)))
      (recur (read-line)))))

(defn -main
  []
  (println "Type exit to quit")
  (domain/init)
  (start-reading-input domain/process-message))
