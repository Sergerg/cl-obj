(ns cl-obj.step1
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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; (_x spoint)
(def _x (field :x))
(def _y (field :y))

(def point
  {:x 10
   :y 20
   :getX (fn [this] (_x this))
   :getY (fn [this] (_y this))})

(def _dx (field :dx))
(def _dy (field :dy))


(def spoint
  {:proto point
   :dx 1
   :dy 2
   :getX (fn [this] (+ (_x this) (_dx this)))
   :getY (fn [this] (+ (_y this) (_dy this)))
   :add (fn [this a b] (+ a b))})

;; (_getX spoint)
(def _getX (method :getX))
(def _getY (method :getY))
(def _add (method :add))

