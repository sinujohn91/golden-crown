(ns golden-crown.entity.kingdom-test
  (:require [clojure.test :refer [deftest testing is]]
            [golden-crown.entity.message :as en-message]
            [golden-crown.entity.kingdom :as en-kingdom]
            [golden-crown.persistence.memory.kingdom :as mem-kingdom]))

(deftest create
  (testing "It should create a kingdom"
    (let [emblem "Panda"
          name "Land"
          {kingdom-id :id} (en-kingdom/create name emblem)
          actual-kingdom (-> (mem-kingdom/get {:id kingdom-id})
                             (dissoc :id))
          expected-kingdom {:emblem emblem
                            :name name
                            :ally-of nil}]
      (is (= expected-kingdom actual-kingdom)))))

(deftest process-message
  (testing "A kingdom should become allies with king shan if the message has minimum number of characters repeated as that of its animal"
    (let [emblem "Panda"
          name "Land"
          {kingdom-id :id} (en-kingdom/create name emblem)
          message (en-message/create kingdom-id "I need to add a p, n, and d")
          _ (en-kingdom/process-message kingdom-id message)
          kingdom (mem-kingdom/get {:id kingdom-id})
          expected-allies "King Shan"
          actual-allies (:ally-of kingdom)]
      (is (= expected-allies actual-allies))))

  (testing "A kingdom should not become allies with king shan if the message doesn't have minimum number of characters repeated as that of its animal"
    (let [emblem "Panda"
          name "Land"
          {kingdom-id :id} (en-kingdom/create name emblem)
          message (en-message/create kingdom-id "Non Allied worthy message")
          _ (en-kingdom/process-message kingdom-id message)
          kingdom (mem-kingdom/get {:id kingdom-id})
          expected-allies nil
          actual-allies (:ally-of kingdom)]
      (is (= expected-allies actual-allies)))))

(deftest allies
  (testing "It should return the kingdom which are allies to the ruler"
    (let [emblem "Panda"
          name "Land"
          {kingdom-id :id} (en-kingdom/create name emblem)
          message (en-message/create kingdom-id "I need to add a p, n, and d")
          _ (en-kingdom/process-message kingdom-id message)]
      (is (= [kingdom-id] (map :id (en-kingdom/allies))))))

  (testing "It should return empty seq if the ruler doesn't have any allies"
    (let [emblem "Panda"
          name "Land"
          {kingdom-id :id} (en-kingdom/create name emblem)
          message (en-message/create kingdom-id "Non Allied worthy message")
          _ (en-kingdom/process-message kingdom-id message)]
      (is (empty? (en-kingdom/allies))))))
