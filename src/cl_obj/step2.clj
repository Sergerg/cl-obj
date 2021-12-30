(ns cl-obj.step2
  (:gen-class))

(+ 1 2)

;; получение  поля
(defn proto-get [obj key]
  (cond
    (contains? obj key) (obj key)
    (contains? obj :proto) (proto-get (obj :proto) key)))

;; вызов метода
(defn proto-call [obj key & args]
  (apply (proto-get obj key) obj args))

;; Упрощённое поле
(defn field [key]
  #(proto-get % key))

;; упрощённый метод
(defn method [key]
  (fn [obj & args] (apply proto-call obj key args)))

(defn constructor [cons proto]
  (fn [& args] (apply cons {:proto proto} args)))


(def _x (field :x))
(def _y (field :y))
(def _dx (field :dx))
(def _dy (field :dy))


(def _getX (method :getX))
(def _getY (method :getY))
(def _add (method :add))


(def PointProto 
  {
   ;; :getX (fn [this] ((field :x) this))
   :getX (fn [this] (proto-get this :x))
   :getY (fn [this] (_y this))})
   

(defn PointCons [this x y]
  (assoc this
   :x x
   :y y))

(def _Point (constructor PointCons PointProto))

;; (_Point 10 20)
;; (_getX (_Point 12 32)) => 12

(def SPointProto
  (assoc PointProto
         :getX (fn [this] (+ (_x this) (_dx this)))
         :getY (fn [this] (+ (_y this) (_dy this)))
         :add (fn [this a b] (+ a b))))

(defn SPointCons [this x y dx dy]
  (assoc (PointCons this x y)
        :dx dx
        :dy dy ))

(def _SPoint (constructor SPointCons SPointProto))

;; (_SPoint 10 20 1 2)
;; (_getX (_SPoint 10 20 1 2)) => 11
;; (_getY (_SPoint 10 20 1 2)) => 22

