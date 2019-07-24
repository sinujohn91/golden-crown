(set-env!
 :project 'golden-crown
 :version "0.1.0"
 :source-paths #{"src" "test"}
 :resource-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.10.1"]
                 [metosin/bat-test "0.4.3" :scope "test"]])

(task-options!
 pom {:project (get-env :project)
      :version (get-env :version)}
 jar {:main 'golden-crown.core})

(require '[metosin.bat-test :refer (bat-test)]
         '[golden-crown.core :as core])

(deftask run
  []
  (core/-main))

(deftask test
  []
  (bat-test))

(deftask coverage
  []
  (bat-test :cloverage true))

(deftask build
  []
  (comp (aot :all true) (pom) (uber) (jar) (target)))
