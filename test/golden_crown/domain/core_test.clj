(ns golden-crown.domain.core-test
  (:require [clojure.test :refer [deftest testing is use-fixtures]]
            [golden-crown.domain.core :as domain]
            [golden-crown.persistence.memory.kingdom :as mem-kingdom]))

(use-fixtures :each )

(deftest init
  (testing "It should initialize 6 kingdoms"
    (let [_ (domain/init)
          actual-kingdom-names (->> (mem-kingdom/get)
                                    (map :name)
                                    (into #{}))
          expected-kingdom-names #{"Land" "Water" "Ice" "Air" "Fire" "Space"}]
      (is (= expected-kingdom-names actual-kingdom-names)))))
