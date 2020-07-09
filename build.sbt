
inThisBuild(List(
  organization := "ch.epfl.scala",
  homepage := Some(url("https://github.com/scalacenter/sbt-compatibility")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "alexarchambault",
      "Alexandre Archambault",
      "",
      url("https://github.com/alexarchambault")
    )
  )
))

lazy val `sbt-compatibility-rules` = project
  .settings(
    sbtPlugin := true
  )

lazy val `sbt-compatibility` = project
  .dependsOn(`sbt-compatibility-rules`)
  .enablePlugins(ScriptedPlugin)
  .settings(
    sbtPlugin := true,
    scriptedLaunchOpts += "-Dplugin.version=" + version.value,
    scriptedBufferLog := false,
    addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.7.0"),
    libraryDependencies ++= Seq(
      "io.github.alexarchambault" %% "data-class" % "0.2.3" % Provided,
      compilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
    ),
    libraryDependencies ++= Seq(
      "io.get-coursier" % "interface" % "0.0.24",
      "io.get-coursier" %% "versions" % "0.2.2"
    ),
    scriptedDependencies := {
      scriptedDependencies.value
      publishLocal.in(`sbt-compatibility-rules`).value
    }
  )

lazy val `sbt-compatibility-dummy` = project
  .in(file("sbt-compatibility/target/dummy"))
