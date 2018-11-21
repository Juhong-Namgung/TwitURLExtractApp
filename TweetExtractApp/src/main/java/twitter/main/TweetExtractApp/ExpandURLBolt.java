package twitter.main.TweetExtractApp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/*
 * Expand Short URL to Full URL
 */

public class ExpandURLBolt extends BaseRichBolt {

	OutputCollector collector;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;		
	}

	@Override
	public void execute(Tuple input) {
		
		String url = (String) input.getValueByField("url");
		String expandURL;
		
		expandURL = url;
		
		/* 
		 * (추가 예정)
		 * shortURL 확인?
		 */
		
		try {
			expandURL = expandURL(url);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		System.out.println("####" + expandURL);
		collector.emit(new Values(expandURL));
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("expandurl"));
	}
	// Get Full URL Using HttpURLConnection 
	public String expandURL(String shortURL) throws IOException {
		URL url = new URL(shortURL);
		
		HttpsURLConnection httpURLConn = (HttpsURLConnection) url.openConnection(Proxy.NO_PROXY);
		
		// stop following browser redirect
		httpURLConn.setInstanceFollowRedirects(false);
		
		// extract location header containing the actual destination URL
		String expandURL = httpURLConn.getHeaderField("Location");
		httpURLConn.disconnect();
		
		return expandURL;		
	}

}
