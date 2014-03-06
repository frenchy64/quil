(ns quil.examples.gen-art.concentric-circles
  (:use quil.core)
  (:require [clojure.core.typed :as t]
            [quil.typed :as qt]))

;; Example 3 - Concentric circles drawn using traces
;; Taken from Listing 2.3, p37

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
;;   strokeWeight(1);
;;   noFill();
;; }

;; void draw() {
;;   if(diam <= 400) {
;;     ellipse(centX, centY, diam, diam);
;;     diam += 10;
;;   }
;; }

(t/def-alias GenArt3State
  '{:diam (t/Atom1 t/AnyInteger)
    :cent-x Number
    :cent-y Number})

(t/ann setup [-> Any])
(defn setup []
  (frame-rate 24)
  (smooth)
  (background 180)
  (stroke 0)
  (stroke-weight 1)
  (no-fill)
  (qt/set-state! GenArt3State
                 :diam (t/atom> t/AnyInteger 10)
                 :cent-x (/ (width) 2)
                 :cent-y (/ (height) 2)))

(defmacro gen-art-3-state [& args]
  `(qt/state ~`GenArt3State ~@args))

(t/ann draw [-> Any])
(defn draw []
  (let [cent-x (gen-art-3-state :cent-x)
        cent-y (gen-art-3-state :cent-y)
        diam   (gen-art-3-state :diam)]
    (when (<= @diam 400)
      (ellipse cent-x cent-y @diam @diam)
      (swap! diam + 10))))

(qt/ann-sketch gen-art-3)
(defsketch gen-art-3
  :title "Concentric Circles"
  :setup setup
  :draw draw
  :size [500 300])
