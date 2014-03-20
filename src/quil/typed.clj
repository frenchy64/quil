(ns quil.typed
  "Parent namespace for core.typed Quil usage.

  For working examples of using `quil.typed`, see `quil.typed.examples.*`.

  This namespace contains static type annotations, convenience macros and enhanced
  type checked operations for Quil.

  This namespace should be required as usual to trigger annotations
  to be loaded.

  eg. (ns my.ns 
        (:require 
          [clojure.core.typed :as t]
          [quil.typed :as qt]))

  # Notes on Annotations

  This file should be used as the reference for the type annotations core.typed
  assigns to quil operations.

  In almost all cases, the return type of a function is useless, so an `Any`
  return type is common.

  # Convenience macros

  ## `ann-sketch`

  Each call to `defsketch` must be accompanied by a call to `ann-sketch`.

  eg.  (qt/ann-sketch gen-art-1)
       (defsketch gen-art-1
         :title \"Cross with circle\"
         :setup setup
         :size [500 300])

  # Enhanced Operations

  ## `quil.typed/set-state!`

  Replacement for `quil.core/set-state!`. 
  
  The user must provide an expected static type as the first argument, 
  which the rest of the keyword arguments to `set-state!` will be checked against.

  eg. (qt/set-state! '{:a Number :b Boolean}
        :a 1
        :b true)

  ## `quil.typed/state`

  Replacement for `quil.core/state`.

  The user must provide a 'trusted' static type representing the current sketch's state.
  This *must* match the annotations given any relevant calls to `set-state!`, as core.typed
  will simply assume the provided type is correct.

  If possible, core.typed will infer an accurate type for the result of the state lookup.

  `quil.typed/state` does not throw a static type error on non-existant keys. This will result
  a runtime error: exactly the same behaviour as `quil.core/state`.

  eg. (qt/state '{:a Number :b Boolean}
        :a)
      ;=> ? : Number
  
  "
  (:require [clojure.core.typed :as t :refer [Num]]
            [clojure.core.typed.unsafe :as unsafe]
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
(t/ann ^:no-check quil.core/stroke-int
       (Fn [Number -> Any]
           [Number Number -> Any]))
(t/ann ^:no-check quil.core/width [-> Number])
(t/ann ^:no-check quil.core/height [-> Number])
(t/ann ^:no-check quil.core/frame-rate [t/AnyInteger -> Any])
(t/ann ^:no-check quil.core/no-fill [-> Any])
(t/ann ^:no-check quil.core/no-cursor [-> Any])

(t/ann ^:no-check quil.core/PI Number)

(t/ann ^:no-check quil.core/curve-vertex 
       (Fn [Number Number -> Any]
           [Number Number Number -> Any]))

(t/ann ^:no-check quil.core/point
       (Fn [Number Number -> Any]
           [Number Number Number -> Any]))

(t/ann ^:no-check quil.core/begin-shape 
       (Fn [-> Any]
           [(U ':points ':lines ':triangles
               ':triangle-fan ':triangle-strip
               ':quads ':quad-strip)
            -> Any]))

(t/ann ^:no-check quil.core/end-shape 
       (Fn [-> Any]
           [':close -> Any]))

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
(t/ann ^:no-check quil.core/rect
       [Number Number Number Number -> Any])

(t/ann ^:no-check quil.core/push-matrix [-> Any])
;(t/ann ^:no-check quil.core/translate [-> Any])

(t/ann ^:no-check quil.helpers.seqs/range-incl
       (Fn [-> (t/Seq t/AnyInteger)]
           [Number -> (t/Seq t/AnyInteger)]
           [t/AnyInteger Number -> (t/Seq t/AnyInteger)]
           [Number Number -> (t/Seq Number)]
           [t/AnyInteger Number t/AnyInteger -> (t/Seq t/AnyInteger)]
           [Number Number Number -> (t/Seq Number)]))
(t/ann ^:no-check quil.helpers.seqs/steps
       (Fn [-> (t/Seq t/AnyInteger)]
           [(U t/AnyInteger (t/Seqable t/AnyInteger)) -> (t/Seq t/AnyInteger)]
           [(U Number (t/Seqable Number)) -> (t/Seq Number)]
           [t/AnyInteger (U t/AnyInteger (t/Seqable t/AnyInteger)) -> (t/Seq t/AnyInteger)]
           [Number (U Number (t/Seqable Number)) -> (t/Seq Number)]))
(t/ann ^:no-check quil.helpers.seqs/seq->stream
       (All [x]
            [(U nil (t/Seqable x)) -> [-> (U nil x)]]))
(t/ann ^:no-check quil.helpers.seqs/perlin-noise-seq
       [Number Number -> (t/Seq Number)])

(t/ann ^:no-check quil.helpers.seqs/cycle-between
       (Fn [Number Number -> (t/Seq Number)]
           [Number Number Number -> (t/Seq Number)]
           [Number Number Number Number -> (t/Seq Number)]
           [Number Number Number Number Number -> (t/Seq Number)]
           [Number Number Number Number Number (U ':up ':down) -> (t/Seq Number)]))

(t/ann ^:no-check quil.helpers.calc/mul-add
       (Fn [Number Number Number -> Number]
           ; these arities always return a Seq
           [(t/Seqable Number) (U Number (t/Seqable Number)) (U Number (t/Seqable Number)) -> (t/Seq Number)]
           [(U Number (t/Seqable Number)) (t/Seqable Number) (U Number (t/Seqable Number)) -> (t/Seq Number)]
           [(U Number (t/Seqable Number)) (U Number (t/Seqable Number)) (t/Seqable Number) -> (t/Seq Number)]
           ; default, could return Number or Seq
           [(U Number (t/Seqable Number)) (U Number (t/Seqable Number)) (U Number (t/Seqable Number)) -> (U Number (t/Seq Number))]))

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
  `(unsafe/ignore-with-unchecked-cast
      (quil.core/state ~k)
      (~'Get ~t ~(list 'Value k))))
