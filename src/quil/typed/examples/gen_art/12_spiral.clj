(ns quil.typed.examples.gen-art.12-spiral
  (:use quil.core
        [quil.helpers.drawing :only [line-join-points]]
        [quil.helpers.seqs :only [range-incl steps]])
  (:require [quil.typed :as qt]
            [clojure.core.typed :as t]))

;; Example 12 - Spiral
;; Taken from Listing 4.2, p69

;; void setup() {
;;   size(500,300);
;;   background(255);
;;   strokeWeight(5);
;;   smooth();
;;   float radius = 100;
;;   int centX = 250;
;;   int centY = 150;
;;   stroke(0, 30);
;;   noFill();
;;   ellipse(centX,centY,radius*2,radius*2);
;;   stroke(20, 50, 70);
;;   radius = 10;
;;   float x, y;
;;   float lastx = -999;
;;   float lasty = -999;
;;   for (float ang = 0; ang <= 1440; ang += 5) {
;;     radius += 0.5;
;;     float rad = radians(ang);
;;     x = centX + (radius * cos(rad));
;;     y = centY + (radius * sin(rad));
;;     if (lastx > -999) {
;;       line(x,y,lastx,lasty);
;;     }
;;     lastx = x;
;;     lasty = y;
;;   }
;; }

(t/ann setup [-> Any])
(defn setup []
  (background 255)
  (stroke-weight 5)
  (smooth)
  (let [radius    100
        cent-x    250
        cent-y    150
        radians   (map radians (range-incl 0 1440 5))
        radii     (steps 10 0.5)
        xs        (map (t/ann-form (fn [radians radius] (+ cent-x (* radius (cos radians))))
                                 [Number Number -> Number])
                       radians radii)
        ys        (map (t/ann-form (fn [radians radius] (+ cent-y (* radius (sin radians)))) 
                                   [Number Number -> Number])
                       radians radii)
        line-args (line-join-points xs ys)]
    (stroke 0 30)
    (no-fill)
    (ellipse cent-x cent-y (* radius 2) (* radius 2))
    (stroke 20 50 70)
    (t/tc-ignore
      (dorun (map #(apply line %) line-args)))))

(qt/ann-sketch gen-art-12)
(defsketch gen-art-12
  :title "Spiral"
  :setup setup
  :size [500 300])
