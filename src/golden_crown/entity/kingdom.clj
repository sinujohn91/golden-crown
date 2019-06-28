(ns golden-crown.entity.kingdom)

;; Entity to contain the kingdom
;; Properties: id::int, emblem::string, name::string, allies-of::set

(defn process-message
  "figure out if its an ally or not"
  [kingdom-id message])

(defn create
  "Create and return a kingdom
  @params name::string, emblem::string"
  [name emblem])