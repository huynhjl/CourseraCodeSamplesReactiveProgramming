package coursera.rx

/**
 * Created with IntelliJ IDEA.
 * User: netflix
 * Date: 11/23/13
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
object Quizzes {
  def quizI(): Unit = {

  val xs = Observable(Range(1,10).inclusive)
  val ys = xs.map(x => x+1)

  println(ys.toBlockingObservable.toList ==  List(2,3,4,5,6,7,8,9,10,11))
  }
}
