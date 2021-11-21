ThisBuild / baseVersion := "2.0"

ThisBuild / organization := "org.typelevel"
ThisBuild / organizationName := "Typelevel"

ThisBuild / developers := List(
  Developer("larsrh", "Lars Hupel", "", url("https://github.com/larsrh")),
  Developer("travisbrown", "Travis Brown", "", url("https://github.com/travisbrown")),
  Developer(
    "ChristopherDavenport",
    "Christopher Davenport",
    "",
    url("https://github.com/ChristopherDavenport")
  ),
  Developer("djspiewak", "Daniel Spiewak", "", url("https://github.com/djspiewak")),
  Developer("vasilmkd", "Vasil Vasilev", "vasil@vasilev.io", url("https://github.com/vasilmkd"))
)

val Scala3 = "3.1.0"
ThisBuild / crossScalaVersions := Seq(Scala3)

ThisBuild / githubWorkflowJavaVersions := Seq("adoptium@8")
ThisBuild / githubWorkflowEnv += ("JABBA_INDEX" -> "https://github.com/typelevel/jdk-index/raw/main/index.json")

ThisBuild / githubWorkflowUseSbtThinClient := false
ThisBuild / githubWorkflowTargetBranches := Seq("series/2.x")

ThisBuild / homepage := Some(url("https://github.com/typelevel/discipline-specs2"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/typelevel/discipline-specs2"),
    "git@github.com:typelevel/discipline-specs2.git"
  )
)

ThisBuild / licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
ThisBuild / startYear := Some(2019)
ThisBuild / endYear := Some(2021)

ThisBuild / githubWorkflowBuildPreamble ++= Seq(
  WorkflowStep.Use(
    UseRef.Public("ruby", "setup-ruby", "v1"),
    params = Map("ruby-version" -> "2.6")
  ),
  WorkflowStep.Run(List("gem install jekyll -v 4"))
)

ThisBuild / githubWorkflowBuild := Seq(
  WorkflowStep.Sbt(
    List("test", "mimaReportBinaryIssues"),
    name = Some("Validate unit tests and binary compatibility")
  ),
  WorkflowStep.Sbt(List("docs/makeMicrosite"))
)

val disciplineV = "1.3.0"
val specs2V = "5.0.0-RC-21"

lazy val `discipline-specs2` =
  project.in(file(".")).aggregate(coreJVM, coreJS).enablePlugins(NoPublishPlugin)

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "discipline-specs2",
    libraryDependencies += "org.typelevel" %%% "discipline-core" % disciplineV,
    libraryDependencies += "org.specs2" %%% "specs2-scalacheck" % specs2V,
    scalacOptions ++= Seq("-new-syntax", "-indent", "-source:future")
  )

lazy val coreJVM = core.jvm
lazy val coreJS = core.js

lazy val docs = project
  .in(file("docs"))
  .enablePlugins(MicrositesPlugin, NoPublishPlugin)
  .settings(micrositeSettings)
  .dependsOn(coreJVM)

lazy val micrositeSettings = {
  import microsites._
  Seq(
    micrositeName := "discipline-specs2",
    micrositeDescription := "Specs2 Integration for Discipline",
    micrositeAuthor := "typelevel",
    micrositeGithubOwner := "typelevel",
    micrositeGithubRepo := "discipline-specs2",
    micrositeBaseUrl := "/discipline-specs2",
    micrositeDocumentationUrl := "https://www.javadoc.io/doc/org.typelevel/discipline-specs2_2.13",
    micrositeGitterChannelUrl := "typelevel/cats",
    micrositeFooterText := None,
    micrositeHighlightTheme := "atom-one-light",
    micrositePalette := Map(
      "brand-primary" -> "#3e5b95",
      "brand-secondary" -> "#294066",
      "brand-tertiary" -> "#2d5799",
      "gray-dark" -> "#49494B",
      "gray" -> "#7B7B7E",
      "gray-light" -> "#E5E5E6",
      "gray-lighter" -> "#F4F3F4",
      "white-color" -> "#FFFFFF"
    ),
    micrositeExtraMdFiles := Map(
      file("CHANGELOG.md") -> ExtraMdFileConfig(
        "changelog.md",
        "page",
        Map("title" -> "changelog", "section" -> "changelog", "position" -> "100")
      ),
      file("CODE_OF_CONDUCT.md") -> ExtraMdFileConfig(
        "code-of-conduct.md",
        "page",
        Map("title" -> "code of conduct", "section" -> "code of conduct", "position" -> "101")
      ),
      file("LICENSE") -> ExtraMdFileConfig(
        "license.md",
        "page",
        Map("title" -> "license", "section" -> "license", "position" -> "102")
      )
    )
  )
}
