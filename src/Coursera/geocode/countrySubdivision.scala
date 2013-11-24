package coursera.geocode

import retrofit.http.{Path, GET}
import retrofit.{RetrofitError, RestAdapter, Callback}
import coursera.usgs.{Feature, FeatureCollection}
import rx.lang.scala.Observable
import rx.lang.scala.subjects.AsyncSubject
import java.lang.Exception
import retrofit.client.Response
import scala.concurrent.{Promise, Future}

class CountrySubdivision {
  val countryCode: String = null
  val countryName: String = null
  val adminCode1: Integer = null
  val adminName1: String = null
}