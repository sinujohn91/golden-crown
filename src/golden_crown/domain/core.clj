(ns golden-crown.domain.core
  (:require [golden-crown.entity.kingdom :as kingdom]
            [golden-crown.entity.message :as message]))

;; Core business logic
;; Create the 6 kingdoms and keep it in a list
;; 2 types of messages can come:
;; action: forward the message to the kingdom for processing
;; question: answer it by looping over all the kingdoms.

(defn process-message
  "Process a message recieved from the gateway
  @params emblem::string, message::string
  @return null"
  [emblem message])