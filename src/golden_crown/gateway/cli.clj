(ns golden-crown.gateway.cli
  (:refer-clojure :exclude [read-line]))

;; functions for cli operations

(defn read-line
  "Read one line from the stdin
  @return string"
  []
  (clojure.core/read-line))

(defn put
  "Output a line to the stdout
  @params message::string
  @return null"
  [message]
  (println message))
