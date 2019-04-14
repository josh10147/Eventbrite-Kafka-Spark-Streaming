name := "Kafka Producer"

version := "0.1"

scalaVersion := "2.11.8"

/************************************************
  * Kafka Dependencies ver 2.0
  ************************************************/
libraryDependencies += "org.apache.kafka" %% "kafka" % "2.1.0"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.1.0"

/************************************************
  * Spark Dependencies ver 2.0.0
  ************************************************/
lazy val sparkVersion = "2.0.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies +=  "org.apache.spark" %% "spark-streaming" % sparkVersion
libraryDependencies +=  "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion

/************************************************
  * Other Dependencies
  ************************************************/
//HTTP Response Dependency
libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.1"

// https://mvnrepository.com/artifact/org.slf4j/slf4j-api
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.26"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime
// https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.26" % Test