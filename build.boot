(set-env! 
  :project 'golden-crown
  :version "0.1.0"
  :source-paths #{"src" "test"}
  :resource-paths #{"src"}
  :dependencies '[[org.clojure/clojure "1.10.1"]])

(task-options!
  pom {:project (get-env :project)
        :version (get-env :version)}
  jar {:main 'golden-crown.core})

(deftask build
  []
  (comp (aot :all true) (pom) (uber) (jar) (target)))