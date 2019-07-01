(ns golden-crown.entity.message-test
  (:require [clojure.test :refer [deftest testing is use-fixtures]]
            [golden-crown.entity.kingdom :as en-kingdom]
            [golden-crown.entity.message :as en-message]
            [golden-crown.test-utils :as test-utils]))

(use-fixtures :each test-utils/with-clean-memory)

(deftest create
  (testing "It should return back a message hashmap"
    (let [{kingdom-id :id} (en-kingdom/create "Land" "Panda")
          message-str "Land, \"Some action Message\""
          actual-message (-> (en-message/create message-str)
                             (select-keys #{:type :subtype :message :kingdom-name}))
          expected-message {:type :action
                            :subtype nil
                            :message "Some action Message"
                            :kingdom-name "Land"}]
      (is (= expected-message actual-message))))

  (testing "It should be able to correctly identity its type"
    (let [{kingdom-id :id} (en-kingdom/create "Land" "Panda")
          actual-question-type (-> (en-message/create "is this a question?")
                                   :type)
          actual-action-type (-> (en-message/create "Land, \"this is an action\"")
                                 :type)]
      (is (= actual-question-type :question))
      (is (= actual-action-type :action))))

  (testing "It should be able to correctly identify if the question is asking who the ruler is"
    (let [{kingdom-id :id} (en-kingdom/create "Land" "Panda")
          who-is-the-ruler-question-strs #{"Who is the ruler of Southeros?"}
          actual-subtype (->> who-is-the-ruler-question-strs
                              (map #(en-message/create %))
                              (map :subtype)
                              (into #{}))
          expected-subtype #{:who-is-ruler?}]
      (is (= expected-subtype actual-subtype))))

  (testing "It should be able to correctly identify if the question is asking who the allies for the ruler are"
    (let [{kingdom-id :id} (en-kingdom/create "Land" "Panda")
          who-are-allies-question-strs #{"Allies of King Shan?" "Allies of Ruler?"}
          actual-subtype (->> who-are-allies-question-strs
                              (map #(en-message/create %))
                              (map :subtype)
                              (into #{}))
          expected-subtype #{:allies-of-ruler?}]
      (is (= expected-subtype actual-subtype)))))
