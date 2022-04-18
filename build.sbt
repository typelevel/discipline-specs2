ThisBuild / tlBaseVersion := "1.5"

ThisBuild / developers := List(
  tlGitHubDev("larsrh", "Lars Hupel"),
  tlGitHubDev("travisbrown", "Travis Brown"),
  tlGitHubDev("ChristopherDavenport", "Christopher Davenport"),
  tlGitHubDev("djspiewak", "Daniel Spiewak"),
  Developer("vasilmkd", "Vasil Vasilev", "vasil@vasilev.io", url("https://github.com/vasilmkd"))
)

val Scala213 = "2.13.8"

ThisBuild / tlCiReleaseTags := false
ThisBuild / crossScalaVersions := Seq("3.1.2", "2.12.15", Scala213)
ThisBuild / tlVersionIntroduced := Map("3" -> "1.1.6")

ThisBuild / licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
ThisBuild / startYear := Some(2019)
ThisBuild / tlSiteApiUrl := Some(
  url("https://www.javadoc.io/doc/org.typelevel/discipline-specs2_2.13"))

val disciplineV = "1.4.0"
val specs2V = "4.15.0"
val macrotaskExecutorV = "1.0.0"

lazy val root = tlCrossRootProject.aggregate(core)

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "discipline-specs2",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "discipline-core" % disciplineV,
      "org.specs2" %%% "specs2-scalacheck" % specs2V
    ),
    headerLicense := Some(
      HeaderLicense.MIT(s"${startYear.value.get}-2022", organizationName.value)
    )
  )
  .jsSettings(
    tlVersionIntroduced ~= { _ ++ List("2.12", "2.13").map(_ -> "1.1.0").toMap }
  )

lazy val docs = project.in(file("site")).enablePlugins(TypelevelSitePlugin).dependsOn(core.jvm)
