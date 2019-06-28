(ns golden-crown.entity.message)

;; Entity to contain a message
;; Its responsible for parsing through the message
;; Properties: kingdom-id, type, subtype, message

;; could move to utils if required
(defn- match?
  "Util function to return true if the reqex matches else false
  @params regex:regex-literal string:string
  @return boolean"
  [regex string]
  (boolean (re-matches regex string)))

(defn- get-subtype
  "Return :who-is-ruler? or :allies-of-ruler? based on the question
  @params message:string
  @return keyword"
  [message]
  (cond
    (match? #"(?i).*who.*is.*ruler.*\?$" message) :who-is-ruler?
    (match? #"(?i).*allies.*of.*(ruler|shan).*\?$" message) :allies-of-ruler?))

(defn- get-type
  "Return true if the message ends with a question mark
  @params message:string
  @return boolean"
  [message]
  (cond
    (match? #".*\?$" message) :question
    :else :action))

(defn create
  "Create a new message
  @params kingdom-id::int, message::string
  @return entity"
  [kingdom-id message]
  {:kingdom-id kingdom-id
   :message message
   :type (get-type message)
   :subtype (get-subtype message)})
