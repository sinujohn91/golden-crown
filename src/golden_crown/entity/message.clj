(ns golden-crown.entity.message
  (:require [golden-crown.persistence.memory.message]))

;; Entity to contain a message
;; Properties: kingdom-id, type, subtype, message

(defn create
  "Create a new message
  @params kingdom-id::int, message::string
  @return entity"
  [])