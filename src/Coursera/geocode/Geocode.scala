package coursera.geocode

import retrofit.http.{Path, GET}
import retrofit.{RetrofitError, RestAdapter, Callback}
import scala.concurrent.{Promise, Future}
import retrofit.client.Response
import coursera.usgs.Point

object Geocode {

  private val restAdapter = new RestAdapter.Builder().setServer("http://ws.geonames.org").build()

  def apply(point: Point): Future[CountrySubdivision] = {
    Geocode(point.latitude, point.longitude)
  }

  def apply(latitude: Double, longitude: Double): Future[CountrySubdivision] = {

    // Promise/Future is isomorphic to Observer/Observable as a Subject

    val promise = Promise[CountrySubdivision]()

    restAdapter.create(classOf[Gecode]).get(latitude, longitude, new Callback[CountrySubdivision] {

      def failure(error: RetrofitError): Unit = {
        promise.failure(new Exception(error.getBodyAs(classOf[String]).asInstanceOf[String]))
      }

      def success(t: CountrySubdivision, response: Response): Unit = {
        promise.success(t)
      }

    })

    promise.future
  }
}

private trait Gecode {
  @GET("/countrySubdivisionJSON?lat={lat}&lng={lng}")
  def get(@Path("lat")latitude: Double, @Path("lng")longitude: Double, callback: Callback[CountrySubdivision])
}
