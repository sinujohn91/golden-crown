(ns golden-crown.persistence.memory.kingdom
  (:refer-clojure :exclude [get put update]))

;; Functions for persisting data to memory
;; Possible to get, put, delete kingdoms from memory
;; We could probably create a protocol for this

(def ^:private kingdoms (atom {}))

(defn get
  "Should return back a kingdom
  @params nil or hashmap of filters
  @return kingdom"
  ([]
   (get {}))
  ([{:keys [id ally-of] :as filters}]
   (if id
     (clojure.core/get @kingdoms id)
     (cond->> (vals @kingdoms)
       ally-of (filter #(= ally-of (:ally-of %)))))))


(defn put
  "Add a new kingdom to the list
  @params kingdom
  @return kingdom"
  [kingdom]
  (let [kingdom-id (-> (last @kingdoms)
                       first
                       (or 0)
                       inc)
        kingdom (assoc kingdom :id kingdom-id)]
    (swap! kingdoms #(assoc % kingdom-id kingdom))
    kingdom))

(defn update
  "Update a kingdom with the new kingdom hashmap passed
  @params kingdom-id:int, kingdom
  @return kingdom"
  [kingdom-id kingdom]
  (let [kingdom (assoc kingdom :id kingdom-id)]
    (swap! kingdoms #(assoc % kingdom-id kingdom))
    kingdom))

(defn drop
  "Reset kingdoms to an empty list"
  []
  (reset! kingdoms {}))
