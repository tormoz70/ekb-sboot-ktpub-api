"%JAVA_HOME%\bin\java" ^
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9092 ^
-jar target\ekb-sboot-ktpub-api-1.0-SNAPSHOT.jar