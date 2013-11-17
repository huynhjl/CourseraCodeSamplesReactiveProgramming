package Coursera

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

package object Extensions {

  implicit class ListExtensions[T](val source: List[T]) extends AnyVal {
    def sumBy[B](keySelector: T => Integer): Integer = ???

    def zumBy[B](keySelector: T => B)(implicit num: Numeric[B]): B = {
        source.map(keySelector).sum
    }

  }

  def f[T](that: Future[T]): PartialFunction[Throwable, Future[T]] = { case _: Throwable => that }

  implicit class FutureExtensions[T](val future: Future[T]) extends AnyVal {
    def fallbackTo[U >: T](that: =>Future[U])(implicit executor: ExecutionContext): Future[U] = {
      future.recoverWith(f(that.recoverWith(f(future))))
    }

    def withTry(): Future[Try[T]] = {
      future.map(Success(_)) recover { case t: Throwable => Failure(t) }
    }
  }

  def fallbackTo[U](source: Future[U], that: =>Future[U])(implicit executor: ExecutionContext): Future[U] = {
      source.recoverWith { case _: Throwable => that.recoverWith {  case _: Throwable => source } }
  }

}
