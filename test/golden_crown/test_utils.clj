(ns golden-crown.test-utils
  (:require  [golden-crown.persistence.memory.kingdom :as en-kingdom]))

(defn with-clean-memory
  [f]
  (en-kingdom/drop)
  (f)
  (en-kingdom/drop))
