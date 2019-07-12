(ns golden-crown.entity.kingdom
  (:require [clojure.string :as string]
            [golden-crown.persistence.memory.kingdom :as mem-kingdom])
  (:refer-clojure :exclude [get find]))

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
  @params message:message-entity
  @return nil"
  [message]
  (when (:kingdom-name message)
    (let [{:keys [emblem id] :as kingdom} (mem-kingdom/find {:name (:kingdom-name message)})
          message-letter-freq (frequencies (string/lower-case (:message message)))
          ally? (->> (string/lower-case emblem)
                     frequencies
                     (every? (fn [[letter count]]
                               (<= count (or (clojure.core/get message-letter-freq letter)
                                             0)))))]
      (when ally?
        (mem-kingdom/update id (assoc kingdom :ally-of "King Shan"))))))

(def get mem-kingdom/get)

(def find mem-kingdom/find)

(defn create
  "Create and return a kingdom
  @params name::string, emblem::string"
  [name emblem]
  (mem-kingdom/put {:name name
                    :emblem emblem
                    :ally-of nil}))
