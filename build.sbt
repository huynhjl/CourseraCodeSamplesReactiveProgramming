name := "reactive-samples"

version := "1.0.0"

scalaVersion := "2.10.2"

scalacOptions ++= Seq("-deprecation", "-feature", "-language:postfixOps")

scalaSource in Compile <<= baseDirectory / "src"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
    "com.netflix.rxjava" % "rxjava-scala" % "0.15.0",
    "com.squareup.retrofit" % "retrofit" % "1.0.0",
    "org.scala-lang.modules" %% "scala-async" % "0.9.0-M2",
    "com.typesafe.akka" %% "akka-actor" % "2.2.3"
  )

