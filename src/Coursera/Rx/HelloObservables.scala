package coursera.rx

import rx.lang.scala.{Observable, Subscription}
import scala.languageFeature.postfixOps._
import scala.concurrent.duration._
import coursera.usgs.{Magnitude, Usgs, Feature}
import coursera.usgs.Magnitude.Magnitude
import coursera.usgs.Magnitude

object HelloObservables {

  def ticks(): Unit = {

    val ticks: Observable[Long]        = Observable.interval(1 second)
    val evens: Observable[Long]        = ticks.filter(s => s%2 == 0)
    val buffers: Observable[Seq[Long]] = evens.buffer(2,1)

    // run the program for a while
    val subscription: Subscription     = buffers.subscribe(println(_))

    readLine()

    // stop the stream
    subscription.unsubscribe()
  }

}

object EarthQuakes {

  def quakes(): Observable[Feature] = {
    Usgs()
  }

  def major() = ofMagnitude(Magnitude.Major)

  def ofMagnitude(atLeast: Magnitude) = {

    quakes().map(quake => (quake.geometry, Magnitude(quake.properties.magnitude))).filter{
       case (location, magnitude) => magnitude >= atLeast
    }

  }
}





