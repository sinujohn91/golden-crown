(ns golden-crown.entity.message-test
  (:require [clojure.test :refer [deftest testing is]]
            [golden-crown.entity.kingdom :as en-kingdom]
            [golden-crown.entity.message :as en-message]))

(deftest create
  (testing "It should return back a message hashmap"
    (let [{kingdom-id :id} (en-kingdom/create "Land" "Panda")
          message-str "Some action Message"
          actual-message (-> (en-message/create 1 message-str)
                             (select-keys #{:type :subtype :message}))
          expected-message {:type :action
                            :subtype nil
                            :message message-str}]
      (is (= expected-message actual-message))))

  (testing "It should be able to correctly identity if its a type"
    (let [{kingdom-id :id} (en-kingdom/create "Land" "Panda")
          actual-question-type (-> (en-message/create 1 "is this a question?")
                                   :type)
          actual-action-type (-> (en-message/create 1 "this is an action")
                                 :type)]
      (is (= actual-question-type :question))
      (is (= actual-action-type :action))))

  (testing "It should be able to correctly identify if the question is asking who the ruler is"
    (let [{kingdom-id :id} (en-kingdom/create "Land" "Panda")
          who-is-the-ruler-question-strs #{"Who is the ruler of Southeros?"}
          actual-subtype (->> who-is-the-ruler-question-strs
                              (map #(en-message/create 1 %))
                              (map :subtype)
                              (into #{}))
          expected-subtype #{:who-is-ruler?}]
      (is (= expected-subtype actual-subtype))))

  (testing "It should be able to correctly identify if the question is asking who the allies for the ruler are"
    (let [{kingdom-id :id} (en-kingdom/create "Land" "Panda")
          who-are-allies-question-strs #{"Allies of King Shan?" "Allies of Ruler?"}
          actual-subtype (->> who-are-allies-question-strs
                              (map #(en-message/create 1 %))
                              (map :subtype)
                              (into #{}))
          expected-subtype #{:allies-of-ruler?}]
      (is (= expected-subtype actual-subtype)))))

