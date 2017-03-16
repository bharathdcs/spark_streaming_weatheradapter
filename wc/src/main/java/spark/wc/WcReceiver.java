package spark.wc;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.logging.LogManager;

import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.*;

import com.sun.research.ws.wadl.Response;
public class WcReceiver extends Receiver<String> implements Serializable{
	
	private String url;
	
	//Logger logger=Logger.getLogger(WcReceiver.class);
	
	public WcReceiver(String url)
	{
		super(StorageLevel.MEMORY_AND_DISK_2());
		this.url=url;
	
		
	}

	public WcReceiver(StorageLevel storageLevel) {
		super(storageLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		new Thread(){
			@Override public void run()
			{
				receive();
			}
		}.start();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
	}
	private void receive()
	{
	
		 
		try {
			HttpGet get = new HttpGet(url);
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response=httpclient.execute(get);
	        BufferedReader br=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	 
			store(br.readLine());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
