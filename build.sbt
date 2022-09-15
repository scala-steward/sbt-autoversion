enablePlugins(SbtPlugin)

libraryDependencies ++= Seq(
  "com.vdurmont"    % "semver4j"   % "3.1.0",
  "org.scalatest"  %% "scalatest"  % "3.2.13" % Test,
  "org.scalacheck" %% "scalacheck" % "1.16.0" % Test
)

// sbt plugin dependencies
addSbtPlugin("com.github.sbt"   % "sbt-release" % "1.0.15")
addSbtPlugin("com.typesafe.sbt" % "sbt-git"     % "1.0.1")

scalacOptions := Seq(
  "-encoding",
  "UTF-8",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xfuture"
)
