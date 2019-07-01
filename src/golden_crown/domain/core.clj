(ns golden-crown.domain.core
  (:require [golden-crown.entity.kingdom :as kingdom]
            [golden-crown.entity.message :as message]))

;; Core business logic
;; Create the 6 kingdoms
;; 2 types of messages can come:
;; action: forward the message to the kingdom for processing
;; question: answer it by looping over all the kingdoms.


(def ^:private kingdoms [{:name "Land" :emblem "Panda"}
                         {:name "Water" :emblem "Octopus"}
                         {:name "Ice" :emblem "Mammoth"}
                         {:name "Air" :emblem "Owl"}
                         {:name "Fire" :emblem "Dragon"}
                         {:name "Space" :emblem "Gorilla"}])

(defn init
  []
  (doseq [kingdom kingdoms]
    (kingdom/create (:name kingdom) (:emblem kingdom))))

(defn process-message
  "Process a message recieved from the gateway
  @params emblem::string, message::string
  @return null"
  [emblem message])
