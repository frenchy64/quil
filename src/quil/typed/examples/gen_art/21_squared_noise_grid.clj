(ns quil.typed.examples.gen-art.21-squared-noise-grid
  (:use quil.core
        [quil.helpers.seqs :only [range-incl]]
        [quil.helpers.calc :only [mul-add]])
  (:require [quil.typed :as qt]
            [clojure.core.typed :as t]))

;; Example 21 - Squared 2D Noise Grid
;; Taken from Listing 5.2, p86

;; float xstart, xnoise, ynoise;
;;
;; void setup() {
;;   size(300, 300);
;;   smooth();
;;   background(255);
;;   xstart = random(10);
;;   xnoise = xstart;
;;   ynoise = random(10);
;;   for(int y = 0; y <= height; y+=5) {
;;     ynoise += 0.1;
;;     xnoise = xstart;
;;     for(int x = 0; x <= width; x+=5) {
;;       xnoise += 0.1;
;;       drawPoint(x, y, noise(xnoise, ynoise));
;;     }
;;   }
;; }

;; void drawPoint(float x, float y, float noiseFactor) {
;;   float len = 10 * noiseFactor;
;;   rect(x, y, len, len);
;; }

(t/ann draw-point [Number Number Number -> Any])
(defn draw-point
  [x y noise-factor]
  (let [len (* 10 noise-factor)]
    (rect x y len len)))

(t/ann draw-squares [Number Number -> Any])
(defn draw-squares
  [x-start y-start]
  (dorun
   (t/for> :- Any
     [y :- Number, (range-incl 0 (height) 5)
      x :- Number, (range-incl 0 (width) 5)]
     (let [x-noise (mul-add x 0.01 x-start)
           y-noise (mul-add y 0.01 y-start)
           alph    (* 255 (noise x-noise y-noise))]
       (draw-point x y (noise x-noise y-noise))))))

(t/ann setup [-> Any])
(defn setup []
  (smooth)
  (background 255)
  (draw-squares (random 10) (random 10)))

(qt/ann-sketch gen-art-21)
(defsketch gen-art-21
  :title "Squared 2D Noise Grid"
  :setup setup
  :size [300 300])
