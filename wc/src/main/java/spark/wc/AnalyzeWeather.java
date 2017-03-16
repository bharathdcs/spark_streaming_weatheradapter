package spark.wc;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import tachyon.client.lineage.LineageFileOutStream;

/**
 * Hello world!
 *
 */
public class AnalyzeWeather 
{
    @SuppressWarnings("deprecation")
	public static void main( final String[] args )
    {
    	
    	    if (args.length < 2) {
    	      System.err.println("Usage:<twc url> <hdfs dir path>");
    	      System.exit(1);
    	    }
    	    //System.out.println("Value of argumetns"+args[0]+args[1]+args[2]);
    	 
    	    SparkConf sparkConf = new SparkConf().setAppName("TWCAdapter");
    	     
    	    JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(20));
    	   // JavaDStream<String> filelines = ssc.textFileStream("/tmp/Streamtest");
    	     
    	    JavaReceiverInputDStream<String> lines = ssc.receiverStream(new WcReceiver(args[0]));
    	    lines.foreachRDD(new VoidFunction<JavaRDD<String> >() {
				
				public void call(JavaRDD<String> arg0) throws Exception {
					// TODO Auto-generated method stub
					arg0.saveAsTextFile(args[1]);
					
					
				}
    	    });
			
    	   lines.print();
    	   ssc.start();
    	    ssc.awaitTermination();
    }
}
