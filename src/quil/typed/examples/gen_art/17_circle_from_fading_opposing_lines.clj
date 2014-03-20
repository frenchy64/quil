(ns quil.typed.examples.gen-art.17-circle-from-fading-opposing-lines
  (:use quil.core
        [quil.helpers.seqs :only [range-incl]]
        [quil.helpers.calc :only [mul-add]])
  (:require [quil.typed :as qt]
            [clojure.core.typed :as t]))

;; Example 17 - Circle from Fading Opposing Lines
;; Taken from Section 4.2, p79 (Figure 4.12)

;; void setup() {
;;   size(500,300);
;;   background(255);
;;   strokeWeight(0.5);
;;   smooth();
;;   float radius = 130;
;;   int centX = 250;
;;   int centY = 150;
;;   float x1, y1, x2, y2;
;;   float lastx = -999;
;;   float lasty = -999;
;;   int strokeCol = 255;
;;   for (float ang = 0; ang <= 360; ang += 1) {
;;     float rad = radians(ang);
;;     x1 = centX + (radius * cos(rad));
;;     y1 = centY + (radius * sin(rad));
;;     float opprad = rad + PI;
;;
;;     x2 = centX + (radius * cos(opprad));
;;     y2 = centY + (radius * sin(opprad));
;;     strokeCol -= 1;
;;     if (strokeCol < 0) {
;;       strokeCol = 255;
;;     }
;;     stroke(strokeCol);
;;
;;     line(x1,y1, x2, y2);
;;   }
;; }

(t/ann setup [-> Any])
(defn setup []
  (background 255)
  (stroke-weight 0.5)
  (smooth)
  (stroke 20 50 70)
  (let [radius   130
        cent-x   250
        cent-y   150
        angles   (range-incl 0 360)
        rads     (map radians angles)
        opp-rads (map + rads (repeat PI))
        colours  (cycle (range-incl 255 0 -1))
        x1s      (map (t/ann-form #(mul-add (cos %) radius cent-x)
                                  [Number -> Number]) 
                      rads)
        y1s      (map (t/ann-form #(mul-add (sin %) radius cent-y)
                                  [Number -> Number]) 
                      rads)
        x2s      (map (t/ann-form #(mul-add (cos %) radius cent-x)
                                  [Number -> Number]) 
                      opp-rads)
        y2s      (map (t/ann-form #(mul-add (sin %) radius cent-y)
                                  [Number -> Number]) 
                      opp-rads)]
    (doall (map (t/ann-form 
                  (fn [x1 y1 x2 y2 col]
                    (stroke col)
                    (line x1 y1 x2 y2))
                  [Number Number Number Number Number -> Any])
                x1s y1s x2s y2s colours))))

(qt/ann-sketch gen-art-17)
(defsketch gen-art-17
 :title "Circle from Fading Opposing Lines"
 :setup setup
 :size [500 300])
