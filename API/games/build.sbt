name := """games"""
organization := "com.bob"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

// Custom dependencies
libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "0.20.11-play28"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.bob.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.bob.binders._"
