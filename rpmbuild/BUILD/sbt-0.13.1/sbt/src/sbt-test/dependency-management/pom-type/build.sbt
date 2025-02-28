scalaVersion := "2.10.6"

libraryDependencies += "org.scala-sbt" %% "sbinary" % "0.4.1" withSources() withJavadoc()

lazy val checkPom = taskKey[Unit]("check pom to ensure no <type> sections are generated")

checkPom := {
	val pomFile = makePom.value
	val pom = xml.XML.loadFile(pomFile)
	val tpe = pom \\ "type"
	if(!tpe.isEmpty)
		error("Expected no <type> sections, got: " + tpe + " in \n\n" + pom)
}