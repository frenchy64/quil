(ns quil.typed.examples.gen-art.02-growing-circle
  (:use quil.core
        [quil.helpers.seqs :only [seq->stream range-incl]])
  (:require [quil.typed :as qt]
            [clojure.core.typed :as t]))

;; Example 2 - Growing Circle
;; Taken from Listing 2.1, p28

;; int diam = 10;
;; float centX, centY;

;; void setup() {
;;   size(500, 300);
;;   frameRate(24);
;;   smooth();
;;   background(180);
;;   centX = width/2;
;;   centY = height/2;
;;   stroke(0);
;;   strokeWeight(5);
;;   fill(255, 50);
;; }

;; void draw() {
;;   if(diam <= 400) {
;;     background(180);
;;     ellipse(centX, centY, diam, diam);
;;     diam += 10;
;;   }
;; }

(t/def-alias GenArt2State
  "State for gen-art-2."
  (HMap :mandatory {:diam [-> (U nil Number)]
                    :cent-x Number
                    :cent-y Number}
        :complete? false))

(t/ann setup [-> Any])
(defn setup []
  (frame-rate 24)
  (smooth)
  (background 180)
  (stroke 0)
  (stroke-weight 5)
  (fill 255 25)
  (let [diams (range-incl 10 400 10)]
    (t/print-env "after diams")
    (qt/set-state! GenArt2State
      :diam (seq->stream diams)
      :cent-x (/ (width) 2)
      :cent-y (/ (height) 2))))

(defmacro gen-art-2-state [k]
  `(qt/state ~`GenArt2State ~k))

(t/ann draw [-> Any])
(defn draw []
  (let [cent-x (gen-art-2-state :cent-x)
        cent-y (gen-art-2-state :cent-y)
        diam   ((gen-art-2-state :diam))]
    (when diam
      (background 180)
      (ellipse cent-x cent-y diam diam))))

(qt/ann-sketch gen-art-2)
(defsketch gen-art-2
  :title "Growing circle"
  :setup setup
  :draw draw
  :size [500 300]
  :keep-on-top true)
