name := "Spark Streaming (Kafka Consumer)"

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

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7"