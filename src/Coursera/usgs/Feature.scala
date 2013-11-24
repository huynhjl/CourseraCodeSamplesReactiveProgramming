package coursera.usgs

import retrofit.http.GET
import retrofit.{RestAdapter, RetrofitError, Callback}
import rx.lang.scala.Observable
import rx.lang.scala.subjects.AsyncSubject
import retrofit.client.Response


class Feature {

  val properties : Properties = null
  val geometry: Point         = null

  override def toString() = s"{ 'properties':'${properties}', 'geometry':'${geometry}' }";
}

object Usgs {

  private val restAdapter = new RestAdapter.Builder().setServer("http://earthquake.usgs.gov").build()

  def apply(): Observable[Feature] = {
    val subject = AsyncSubject[FeatureCollection]()

    restAdapter.create(classOf[Usgs]).get(new Callback[FeatureCollection] {

      def failure(error: RetrofitError): Unit = {
        subject.onError(new Exception(error.getBodyAs(classOf[String]).asInstanceOf[String]))
      }

      def success(t: FeatureCollection, response: Response): Unit = {
        subject.onNext(t)
        subject.onCompleted()
      }

    })

    subject.flatMap(collection => Observable(collection.features : _*))
  }
}

private trait Usgs {
  @GET("/earthquakes/feed/geojson/all/day")
  def get(callback: Callback[FeatureCollection])
}

