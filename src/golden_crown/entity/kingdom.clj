(ns golden-crown.entity.kingdom)

;; Entity to contain the kingdom
;; Properties: id::int, emblem::string, animal::string, alliesOf::set

(defn process-message
  "figure out if its an ally or not"
  [kingdom-id message])

(defn create
  "Create and return a kingdom
  @params emblem::string, animal::string"
  [emblem animal])