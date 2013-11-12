package Coursera

/**
 * Created with IntelliJ IDEA.
 * User: netflix
 * Date: 11/12/13
 * Time: 6:53 PM
 * To change this template use File | Settings | File Templates.
 */
package object Extensions {

  implicit class ListExtensions[T](source: List[T]) extends AnyVal {
    def sumBy[B](keySelector: T => B)(implicit num: Numeric[B]): B = {
      source.map(keySelector).sum
    }
  }
}
