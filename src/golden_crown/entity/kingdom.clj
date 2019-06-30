(ns golden-crown.entity.kingdom
  (:require [clojure.string :as string]
             [golden-crown.persistence.memory.kingdom :as mem-kingdom]))

;; Entity to contain the kingdom
;; Properties: id::int, emblem::string, name::string, ally-of::string

(defn allies
  "Return the kingdoms which are allies to the ruler
  @params none
  @result kingdoms which are allies of the ruler"
  []
  (mem-kingdom/get {:ally-of "King Shan"}))

(defn process-message
  "Figure out if its an ally or not
  @params kingdom-id:int, message:message-entity
  @return nil"
  [kingdom-id message]
  (let [{:keys [emblem] :as kingdom} (mem-kingdom/get {:id kingdom-id})
        message-letter-freq (frequencies (string/lower-case (:message message)))
        ally? (->> (string/lower-case emblem)
                   frequencies
                   (every? (fn [[letter count]]
                             (<= count (or (get message-letter-freq letter)
                                           0)))))]
    (when ally?
      (mem-kingdom/update kingdom-id (assoc kingdom :ally-of "King Shan")))))

(defn create
  "Create and return a kingdom
  @params name::string, emblem::string"
  [name emblem]
  (mem-kingdom/put {:name name
                    :emblem emblem
                    :ally-of nil}))
