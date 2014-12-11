name := "WebSocket"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.ats" % "cloud-common" % "0.1-SNAPSHOT",
	"org.ats" % "cloud-cloudstack" % "0.1-SNAPSHOT",
	"org.apache.cloudstack" % "cloud-api" % "4.3.0"
)     

play.Project.playJavaSettings
