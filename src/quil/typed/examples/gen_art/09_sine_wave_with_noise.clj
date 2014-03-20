(ns quil.typed.examples.gen-art.09-sine-wave-with-noise
  (:use quil.core
        [quil.helpers.drawing :only [line-join-points]]
        [quil.helpers.seqs :only [range-incl]]
        [quil.helpers.calc :only [mul-add]])
  (:require [quil.typed :as qt]
            [clojure.core.typed :as t]))

;; Example 9 - Sine Wave with Noise
;; Taken from Listing 3.2, p60

;; void setup() {
;;  size(500, 100);
;;  background(255);
;;  strokeWeight(5);
;;  smooth();
;;  stroke(0, 30);
;;  line(20, 50, 480, 50);
;;  stroke(20, 50, 70);

;;  float xstep = 1;
;;  float lastx = -999;
;;  float lasty = -999;
;;  float angle = 0;
;;  float y = 50;
;;  for(int x=20; x<=480; x+=xstep){
;;    float rad = radians(angle);
;;    y = 50 + (pow(sin(rad), 3) * noise(rad*2) * 30)
;;    if(lastx > -999) {
;;      line(x, y, lastx, lasty);
;;    }
;;    lastx = x;
;;    lasty = y;
;;    angle++;
;;  }
;; }


(t/ann setup [-> Any])
(defn setup []
  (background 255)
  (stroke-weight 5)
  (smooth)
  (stroke 0 30)
  (line 20 50 480 50)
  (stroke 20 50 70)

  (let [xs        (range-incl 20 480 1)
        rads      (map radians (range))
        ys        (map sin rads)
        ys        (map (t/ann-form #(pow % 3) [Number -> Number]) 
                       ys)
        ys        (map (t/ann-form (fn [y rad] (* 30 y (noise (* 2 rad))))
                                   [Number Number -> Number]) 
                       ys rads)
        scaled-ys (mul-add ys 1 50)

        line-args (line-join-points xs scaled-ys)]
    (t/tc-ignore
      (dorun (map #(apply line %) line-args)))))

(qt/ann-sketch gen-art-9)
(defsketch gen-art-9
  :title "Sine Wave with Noise"
  :setup setup
  :size [500 100])
