name := "xs4s-root"

scalaVersion in ThisBuild := "2.12.11"
crossScalaVersions in ThisBuild := Seq("2.12.11", "2.13.2")
organization in ThisBuild := "com.scalawilliam"
version in ThisBuild := "0.6"

lazy val root = (project in file("."))
  .aggregate(core, example)
  .dependsOn(core, example)

lazy val core = project.settings(
  libraryDependencies ++= Seq(
    "xmlunit"                % "xmlunit"           % "1.6" % "test",
    "org.codehaus.woodstox"  % "woodstox-core-asl" % "4.4.1",
    "org.scalatest"          %% "scalatest"        % "3.1.2" % "test",
    "org.scala-lang.modules" %% "scala-xml"        % "1.3.0",
    "co.fs2"                 %% "fs2-core"         % "2.2.1" % "provided",
    "co.fs2"                 %% "fs2-io"           % "2.2.1" % "provided",
  ),
  name := "xs4s",
  publishMavenStyle := true,
  pomIncludeRepository := { _ =>
    false
  },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra := <url>https://github.com/ScalaWilliam/xs4s</url>
    <licenses>
      <license>
        <name>BSD-style</name>
        <url>http://www.opensource.org/licenses/bsd-license.php</url>
        <distribution>repo</distribution>
      </license>
      <license>
        <name>The Apache Software License, Version 2.0</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:ScalaWilliam/xs4s.git</url>
      <connection>scm:git:git@github.com:ScalaWilliam/xs4s.git</connection>
    </scm>
    <developers>
      <developer>
        <id>ScalaWilliam</id>
        <name>William Narmontas</name>
        <url>https://www.scalawilliam.com</url>
      </developer>
    </developers>
)

def download(source: sbt.URL, target: sbt.File): Int = {
  import scala.sys.process._

  (source #> target).!
}

lazy val example = project
  .dependsOn(core)
  .settings(
    libraryDependencies += "co.fs2"  %% "fs2-core"    % "2.2.1",
    libraryDependencies += "co.fs2"  %% "fs2-io"      % "2.2.1",
  )

Global / onChangedBuildSource := ReloadOnSourceChanges
