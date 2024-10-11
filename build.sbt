ThisBuild / tlBaseVersion := "2.1"

ThisBuild / developers := List(
  tlGitHubDev("larsrh", "Lars Hupel"),
  tlGitHubDev("travisbrown", "Travis Brown"),
  tlGitHubDev("ChristopherDavenport", "Christopher Davenport"),
  tlGitHubDev("djspiewak", "Daniel Spiewak"),
  Developer("vasilmkd", "Vasil Vasilev", "vasil@vasilev.io", url("https://github.com/vasilmkd"))
)

val Scala3 = "3.3.3"
ThisBuild / crossScalaVersions := Seq(Scala3)
ThisBuild / tlCiReleaseTags := false

ThisBuild / licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
ThisBuild / startYear := Some(2019)

val disciplineV = "1.7.0"
val specs2V = "5.5.6"

lazy val `discipline-specs2` =
  tlCrossRootProject.aggregate(core).settings(name := "discipline-specs2")

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "discipline-specs2",
    libraryDependencies += "org.typelevel" %%% "discipline-core" % disciplineV,
    libraryDependencies += "org.specs2" %%% "specs2-scalacheck" % specs2V,
    scalacOptions ++= Seq("-new-syntax", "-indent", "-source:future"),
    headerLicense := Some(
      HeaderLicense.MIT(s"${startYear.value.get}-2022", organizationName.value)
    )
  )
