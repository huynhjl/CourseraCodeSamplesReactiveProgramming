package coursera.usgs

class Point {
  private val coordinates: Array[Double] = null

  lazy val Latitude: Double              = coordinates(1)
  lazy val Longitude  : Double           = coordinates(0)
  lazy val Altitude  : Double            = coordinates(2)

  override def toString() = s"{ 'longitude':'${Longitude}', 'latitude':'${Latitude}', 'altitude':'${Altitude}' }";

}
