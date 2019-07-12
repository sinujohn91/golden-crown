(ns golden-crown.domain.core-test
  (:require [clojure.test :refer [deftest testing is use-fixtures]]
            [golden-crown.domain.core :as domain]
            [golden-crown.persistence.memory.kingdom :as mem-kingdom]
            [golden-crown.test-utils :as test-utils]))

(use-fixtures :each test-utils/with-clean-memory)

(deftest init
  (testing "It should initialize 6 kingdoms"
    (let [_ (domain/init)
          actual-kingdom-names (->> (mem-kingdom/get)
                                    (map :name)
                                    (into #{}))
          expected-kingdom-names #{"Land" "Water" "Ice" "Air" "Fire" "Space"}]
      (is (= expected-kingdom-names actual-kingdom-names)))))

(deftest process-message:who-is-ruler?
  (domain/init)
  (testing "When a question for who is the ruler is recieved"
    (testing "When there is no ruler it should answer None"
      (let [expected-result "None"
            actual-result (domain/process-message "Who is the ruler?")]
        (is (= expected-result actual-result))))

    (testing "When there are more than 3 allies it should answer King Shan"
      (let [_ (domain/process-message "Air, \"oaaawaala\"")
            _ (domain/process-message "Land, \"a1d22n333a4444p\"")
            _ (domain/process-message "Ice, \"zmzmzmzaztzozh\"")
            expected-result "King Shan"
            actual-result (domain/process-message "Who is the ruler?")]
        (is (= expected-result actual-result))))

    (testing "It should return back a message saying cant understand question if the question subtype is neither :who-is-ruler or allies-of-king?"
      (let [expected-result "Sorry, dont have an answer for your question"
            actual-result (domain/process-message "some random question?")]
        (is (= expected-result actual-result))))))

(deftest process-message:allies-of-ruler?
  (domain/init)
  (testing "When a question for who the allies of the ruler is asked"
    (testing "It should return none if there are no allies of the ruler"
      (let [expected-result "None"
            actual-result (domain/process-message "Who are the allies of the ruler?")]
        (is (= expected-result actual-result))))

    (testing "It should return the allies of the ruler"
      (let [expected-result "Land, Air"
            _ (domain/process-message "Air, \"oaaawaala\"")
            _ (domain/process-message "Land, \"a1d22n333a4444p\"")
            actual-result (domain/process-message "Who are the allies of the ruler?")]
        (is (= expected-result actual-result))))))
