# spark_streaming_weatheradapter
@Author Bharath K Devaraju<br/>
The Spark Streaming custom reader, allows users to read json data from weather data api on bluemix
https://console.ng.bluemix.net/docs/services/Weather/index.html and write it to file on HDFS. <br/>

The output folder path in HDFS can be used to populate bigsql or hive external table with json serde and queried for analytics.<br/>

Steps to run the program<br/>
1. Compile the program using following maven command
mvn compile package

2. Copy the generated jar file to spark cluster <br/>
target/wc-0.0.1-SNAPSHOT.jar <br/>

3. To test the program launch it using spark-submit local mode.<br/>
The program accepts two parameters Weather data api path and HDFS output folder as shown following.

 spark-submit  --class "spark.wc.AnalyzeWeather" --master local[4] wc-0.0.1-SNAPSHOT.jar 
 <weather data url> /tmp/output

The weather data api url can be obtained by registering in bluemix and provisioning the Weather data api service.
