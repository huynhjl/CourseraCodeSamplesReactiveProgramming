package coursera.rx

import rx.lang.scala.Observable
import scala.languageFeature.postfixOps._
import coursera.usgs.{Usgs, Feature}
import coursera.usgs.Magnitude.Magnitude
import coursera.usgs.Magnitude

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
