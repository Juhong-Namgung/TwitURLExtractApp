package twitter.main.TweetExtractApp;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class ValidateURLBolt extends BaseRichBolt {

	OutputCollector collector;
	HttpURLConnection conn = null;
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {

		String expandURL = (String) input.getValueByField("expandurl");

		// Get HTTP Status Code
		try {
			URL url = new URL(expandURL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			
			int statusCode = conn.getResponseCode();
			System.out.println("?????" + conn.getResponseMessage());
			conn.disconnect();
			if (statusCode >= 400) {
				System.out.println("#######");
				System.out.println("URL: " + expandURL + " is not valid!! HTTP Status Code: " + statusCode);
			} else {
				collector.emit(new Values(expandURL));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				conn.disconnect();
			}
		}

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("validurl"));
	}

}
