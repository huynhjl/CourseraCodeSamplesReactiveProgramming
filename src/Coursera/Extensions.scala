package Coursera

package object Extensions {

  implicit class ListExtensions[T](val source: List[T]) extends AnyVal {
    def sumBy[B](keySelector: T => Integer): Integer = ???

    def zumBy[B](keySelector: T => B)(implicit num: Numeric[B]): B = {
        source.map(keySelector).sum
    }

  }
}
