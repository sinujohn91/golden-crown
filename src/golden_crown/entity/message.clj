(ns golden-crown.entity.message
  (:require [clojure.string :as string]))

;; Entity to contain a message
;; Its responsible for parsing through the message
;; Properties: kingdom-name, type, subtype, message

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
  @params message::string
  @return entity"
  [message]
  (let [type (get-type message)
        kingdom-name (when (= type :action)
                       (-> message (string/split #"," 2) first string/trim))
        message (if (= type :action)
                  (-> message (string/split #"," 2) last (string/replace #"\"" "")string/trim)
                  message)]
    {:kingdom-name kingdom-name
     :message message
     :type type
     :subtype (get-subtype message)}))
