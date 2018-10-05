name := "Timely Effects"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.storm-enroute" %% "scalameter"      % "0.10.1" % Test,
  "com.storm-enroute" %% "scalameter-core" % "0.10.1",
//  "com.github.scala-blitz" %% "scala-blitz" % "1.1",
  "org.scala-lang.modules" %% "scala-swing"            % "2.0.3",
  "org.scalatest"          %% "scalatest"              % "3.2.0-SNAP10" % Test,
  "org.scalacheck"         %% "scalacheck"             % "1.13.5" % "test",
  "junit"                  % "junit"                   % "4.12" % Test,
  "org.junit.jupiter"      % "junit-jupiter-api"       % "5.2.0" % Test,
  "org.junit.jupiter"      % "junit-jupiter-engine"    % "5.2.0" % Test,
  "org.junit.jupiter"      % "junit-jupiter-params"    % "5.2.0" % Test,
  "org.junit.platform"     % "junit-platform-launcher" % "1.2.0" % Test,
  "org.junit.platform"     % "junit-platform-engine"   % "1.2.0" % Test,
  "org.junit.platform"     % "junit-platform-runner"   % "1.2.0" % Test
)

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

parallelExecution in Test := false

resolvers ++= Seq(
  Resolver.mavenLocal,
  DefaultMavenRepository
)
