(ns golden-crown.domain.core
  (:require [clojure.string :as string]
            [golden-crown.entity.kingdom :as kingdom]
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
  "Create the 6 kingdoms"
  []
  (doseq [kingdom kingdoms]
    (kingdom/create (:name kingdom) (:emblem kingdom))))

(defn- get-ruler
  "If there are three allies for King Shan return King Shan else return None"
  []
  (let [allies (kingdom/get {:ally-of "King Shan"})]
    (if (>= (count allies) 3)
      "King Shan"
      "None")))

(defn process-message
  "Process a message recieved from the gateway
  @params emblem::string, message::string
  @return null"
  [message]
  (let [{:keys [type subtype] :as message} (message/create message)]
    (cond
      (and (= type :question)
           (= subtype :who-is-ruler?)) (get-ruler)
      (and (= type :quesion)
           (= subtype :allies-of-king?)) (or (->> (kingdom/get {:ally-of "King Shan"})
                                                  (map :name)
                                                  (string/join ", "))
                                             "None")
      (= type :action) (kingdom/process-message message))))
