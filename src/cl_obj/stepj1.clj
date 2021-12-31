(ns cl-obj.stepj1
  (:gen-class))

(definterface IPoint
  (^Number getX [])
  (^Number getY []))

(deftype JPoint [x y]
  IPoint
  (getX [this] x)
  (getY [this] y))

;; (.x (JPoint. 10 20)) => 10

(deftype JShiftedPoint [x y dx dy]
  IPoint
  (getX [this] (+ (.x this) (.dx this)))
  (getY [this] (+ (.y this) (.dy this))))

;; (.x (JShiftedPoint. 10 20 30 40)) => 10
;; (.getX (JShiftedPoint. 10 20 30 40)) => 40
;; (.getX (new JShiftedPoint 10 20 30 40)) => 40
;; (type (new JShiftedPoint 10 20 30 40)) => cl-obj.stepj1.JShiftedPoint

