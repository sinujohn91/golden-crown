(ns golden-crown.gateway.cli)

;; functions for cli operations

(defn read-line
  "Read one line from the stdin
  @return string"
  []
  (line-seq (java.io.BufferedReader. *in*)))

(defn put
  "Output a line to the stdout
  @params message::string
  @return null"
  [message]
  (println message))
