(defproject quil "1.7.1-typed-SNAPSHOT"
  :description "(mix Processing Clojure)"
  :url "http://github.com/quil/quil"
  :mailing-list {:name "Quil Mailing List"
                 :archive "https://groups.google.com/forum/?fromgroups#!forum/clj-processing"
                 :post "clj-processing@googlegroups.com"}
  :license {:name "Common Public License - v 1.0"
            :url "http://www.opensource.org/licenses/cpl1.0"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojars.processing-core/org.processing.core "1.5.1"]
                 [org.clojars.processing-core/org.processing.gluegen-rt "1.5.1"]
                 [org.clojars.processing-core/org.processing.jogl "1.5.1"]
                 [org.clojars.processing-core/org.processing.opengl "1.5.1"]
                 [org.clojars.processing-core/org.processing.itext "1.5.1"]
                 [org.clojars.processing-core/org.processing.pdf "1.5.1"]
                 [org.clojure/core.typed "0.2.35-SNAPSHOT"]]
  :profiles {:dev {:repl-options {:port 64481}}}
  :aot [quil.applet])
