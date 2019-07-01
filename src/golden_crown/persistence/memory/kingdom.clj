(ns golden-crown.persistence.memory.kingdom
  (:refer-clojure :exclude [get put update]))

;; Functions for persisting data to memory
;; Possible to get, put, delete kingdoms from memory
;; We could probably create a protocol for this

(def ^:private kingdoms (atom {}))

(defn get
  "Should return back kingdoms which match the filter
  @params nil or hashmap of filters
  @return kingdoms"
  ([]
   (get {}))
  ([{:keys [id ally-of name] :as filters}]
   (cond->> (vals @kingdoms)
     id (filter #(= id (:id %)))
     ally-of (filter #(= ally-of (:ally-of %)))
     name (filter #(= name (:name %))))))

(defn find
  "Should return back the first kingdom which matches the filter
  @params filters
  @return kingdom"
  [filters]
  (-> (get filters) first))

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
