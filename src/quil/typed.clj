(ns quil.typed
  (:require [clojure.core.typed :as t :refer [Num]]
            [quil.core]))

(t/ann ^:no-check quil.core/smooth [-> Any])
(t/ann ^:no-check quil.core/background 
       (Fn [Number -> Any]
           [Number Number -> Any]
           [Number Number Number -> Any]
           [Number Number Number Number -> Any]))
(t/ann ^:no-check quil.core/stroke
       (Fn [Number -> Any]
           [Number Number -> Any]
           [Number Number Number -> Any]
           [Number Number Number Number -> Any]))
(t/ann ^:no-check quil.core/line
       (Fn ['[Number Number] '[Number Number] -> Any]
           ['[Number Number Number] '[Number Number Number] -> Any]
           [Number Number Number Number -> Any]
           [Number Number Number Number Number Number -> Any]))
(t/ann ^:no-check quil.core/fill
       (Fn [Number -> Any]
           [Number Number -> Any]
           [Number Number Number -> Any]
           [Number Number Number Number -> Any]))
(t/ann ^:no-check quil.core/ellipse
       (Fn [Number Number Number Number -> Any]))
(t/ann ^:no-check quil.core/stroke-weight
       [Number -> Any])
(t/ann ^:no-check quil.core/width [-> Number])
(t/ann ^:no-check quil.core/height [-> Number])
(t/ann ^:no-check quil.core/frame-rate [t/AnyInteger -> Any])
(t/ann ^:no-check quil.core/no-fill [-> Any])
(t/ann ^:no-check quil.core/no-cursor [-> Any])

(t/ann ^:no-check quil.core/stroke-cap 
       [(U ':square 
           ':round 
           ':project 
           ':model)
        -> Any])

(t/ann ^:no-check quil.core/radians [Number -> Number])
(t/ann ^:no-check quil.core/sin [Number -> Number])
(t/ann ^:no-check quil.core/cos [Number -> Number])
(t/ann ^:no-check quil.core/pow [Number Number -> Number])
(t/ann ^:no-check quil.core/noise 
       (Fn [Number -> Number]
           [Number Number -> Number]
           [Number Number Number -> Number]))
(t/ann ^:no-check quil.core/random
       (Fn [Number -> Number]
           [Number Number -> Number]))

(t/ann ^:no-check quil.helpers.seqs/range-incl
       (Fn [-> (t/Seq t/AnyInteger)]
           [Number -> (t/Seq t/AnyInteger)]
           [t/AnyInteger Number -> (t/Seq t/AnyInteger)]
           [Number Number -> (t/Seq Number)]
           [t/AnyInteger Number t/AnyInteger -> (t/Seq t/AnyInteger)]
           [Number Number Number -> (t/Seq Number)]))
(t/ann ^:no-check quil.helpers.seqs/steps
       (Fn [-> (t/Seq t/AnyInteger)]
           [t/AnyInteger -> (t/Seq t/AnyInteger)]
           [Number -> (t/Seq Number)]
           [t/AnyInteger t/AnyInteger -> (t/Seq t/AnyInteger)]
           [Number Number -> (t/Seq Number)]))
(t/ann ^:no-check quil.helpers.seqs/seq->stream
       (All [x]
            [(U nil (t/Seqable x)) -> [-> (U nil x)]]))
(t/ann ^:no-check quil.helpers.seqs/perlin-noise-seq
       [Number Number -> (t/Seq Number)])

(t/ann ^:no-check quil.helpers.calc/mul-add
       (Fn [Number Number Number -> Number]
           [(U Number (t/Seqable Number)) (U Number (t/Seqable Number)) Number -> (t/Seq Number)]))

(t/ann ^:no-check quil.helpers.drawing/line-join-points
       (All [[x :< Number]]
         (Fn ; none of these should be HVecs
             ;['[x x x] '[x x x] -> (t/Seq '[x x x x])
             ;[(HVec ['[x x] *]) -> (t/Seq '[x x x x])]
             ;['[x x x] '[x x x] '[x x x] -> (t/Seq '[x x x x x x])]
             ;[(HVec ['[x x x] *]) -> (t/Seq '[x x x x x x])]
             [(t/Seqable Number) (t/Seqable Number) -> (t/Seq (t/Seq Number))]
             [(t/Seqable (t/Seqable Number)) -> (t/Seq (t/Seq Number))]
             )))

(t/ann ^:no-check quil.applet/applet
       [& :optional {:size '[Number Number]
                     :renderer (U nil
                                  ':p2d
                                  ':java2d
                                  ':opengl
                                  ':pdf
                                  ':dxf)
                     :output-file String
                     :title (U nil String)
                     :target (U ':frame 
                                ':perm-frame
                                ':none)
                     :setup (U nil [-> Any])
                     :draw (U nil [-> Any])
                     :focus-gained (U nil [-> Any])
                     :mouse-entered (U nil [-> Any])
                     :mouse-exited (U nil [-> Any])
                     :mouse-pressed (U nil [-> Any])
                     :mouse-released (U nil [-> Any])
                     :mouse-clicked (U nil [-> Any])
                     :mouse-moved (U nil [-> Any])
                     :mouse-dragged (U nil [-> Any])
                     :mouse-wheel (U nil [t/Int -> Any])
                     :key-pressed (U nil [-> Any])
                     :key-released (U nil [-> Any])
                     :key-typed (U nil [-> Any])
                     :safe-draw-fn Boolean
                     :on-close (U nil [-> Any])
                     :keep-on-top Boolean}
        -> quil.Applet])

(defmacro ann-sketch [nme]
  `(t/ann ~nme ~'quil.Applet))

(defmacro set-state! 
  "Like quil.core/set-state! except takes an expected
  type for the state. This type must represent the Applet state
  to be altered."
  [t & args]
  `(let [args# [~@args]
         m# (apply hash-map args#)]
     (t/ann-form m# ~t)
     (t/tc-ignore
       (apply quil.core/set-state! args#))))

(t/ann quil.core/state [Any -> Any])

(defmacro state 
  "An unchecked lookup to state set with quil.typed/set-state!.
  
  This must be preceeded with a call to quil.typed/set-state!
  with the same type. This is unsound if any other calls to set-state!
  take precendence.

  If this property cannot be guaranteed, use quil.core/state instead.
  
  eg. (def-alias State '{:a Number})
      (quil.typed/set-state! State
         :a 1)
      (quil.typed/state State :a)
  "
  [t k]
  (assert (keyword? k))
  `(t/unsafe-ann-form
      (quil.core/state ~k)
      (~'Get ~t ~(list 'Value k))))
