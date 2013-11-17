package Coursera

import scala.concurrent.{Promise, ExecutionContext, Future}
import scala.util.{Failure, Success, Try}
import scala.async.Async._

package object Extensions {

  implicit class ListExtensions[T](val source: List[T]) extends AnyVal {
    def sumBy(keySelector: T => Integer): Integer = ???

    private def sumBy[B](keySelector: T => B)(implicit num: Numeric[B]): B = {
        source.map(keySelector).sum
    }

  }

  def f[T](that: Future[T]): PartialFunction[Throwable, Future[T]]  = { case _: Throwable => that }
  def g[T]:                  PartialFunction[Throwable, Failure[T]] = { case t: Throwable => Failure(t) }

  implicit class FutureExtensions[T](val future: Future[T]) extends AnyVal {

    def fallbackTo[U >: T](that: =>Future[U])(implicit executor: ExecutionContext): Future[U] = {
      future.recoverWith(f(that.recoverWith(f(future))))
    }

    def withTry()(implicit executor: ExecutionContext): Future[Try[T]] = {
      future.map(Success(_)) recover g
    }
  }

  def withTry[T](future: Future[T])(implicit executor: ExecutionContext): Future[Try[T]] = {
    future.map(Success(_)) recover { case t: Throwable => Failure(t) }
  }

  def fallbackTo[U](future: Future[U], that: =>Future[U])(implicit executor: ExecutionContext): Future[U] = {
    future.recoverWith { case _: Throwable => that.recoverWith {  case _: Throwable => future } }
  }

  def retryI[T](n: Integer)(block: =>Future[T]): Future[T] = {
    if (n == 0) {
      Future.failed(new Exception("Sorry"))
    } else {
      block fallbackTo { retryI(n-1) { block } }
    }
  }

  def retryII[T](n: Integer)(block: =>Future[T]): Future[T] = {
    val ns: Iterator[Int] = (1 to n).iterator
    val attempts: Iterator[()=>Future[T]] = ns.map(_ => ()=>block)
    val failed: Future[T] = Future.failed(new Exception)
    attempts.foldLeft(failed)((a, block) => a fallbackTo { block() })
  }

  def retryIII[T](n: Integer)(block: =>Future[T]): Future[T] = {
    val ns: Iterator[Int] = (1 to n).iterator
    val attempts: Iterator[()=>Future[T]] = ns.map(_ => ()=>block)
    val failed: Future[T] = Future.failed(new Exception)
    attempts.foldRight(()=>failed)((block, a) => ()=> { block() fallbackTo{ a() }}) ()
  }

  def retryIV[T](n: Int)(block: =>Future[T])(implicit executor: ExecutionContext): Future[T] = async {
    var i: Integer = 0
    var result: Try[T] = Failure(new Exception("Oops"))

    while (i < n) {
      result = await { block.withTry() }

      result match {
        case Success(s) => { i = i+1  }
        case Failure(f) => { i = n    }
      }
    }

    result.get
  }

  def filterI[T](future: Future[T], predicate: T => Boolean)(implicit executor: ExecutionContext): Future[T] = async{
    val x: T = await{ future }
    if(!predicate(x)) {
      throw new Exception("No such element")
    } else {
      x
    }
  }

  def filterII[T](future: Future[T], predicate: T => Boolean)(implicit executor: ExecutionContext): Future[T] = {
    val p = Promise[T]()
    future.onComplete {
      case Success(s) => {
        if(!predicate(s)) {
          p.failure(new Exception("No such element"))
        } else {
          p.success(s)
        }
      }
      case Failure(f) => { p.failure(f) }
    }
    p.future
  }

  def flatMap[T,S](future: Future[T], selector: T => Future[S])(implicit executor: ExecutionContext): Future[S] = async{
    val x: T = await{ future }
    await{ selector(x) }: S
  }

}
