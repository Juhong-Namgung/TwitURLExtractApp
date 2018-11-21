package twitter.main.TweetExtractApp;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/*
 * Extract URL in Tweet using regular expression(regex)
 */	

public class ExtractURLBolt extends BaseRichBolt {
	
	OutputCollector collector;
	//String regex="[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]";
	//String regex="(http://(bit\\.ly|t\\.co|lnkd\\.in|tcrn\\.ch)\\S*)\\b";
	String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;		
	}

	
	@Override
	public void execute(Tuple input) {	
		String twitText = (String) input.getValueByField("tweet");
		String url = null; 
		
		// extract URL using regex
		url = extractURL(twitText, regex);
		if(!url.equals(""))
			collector.emit(new Values(url));
	}

	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {	
		declarer.declare(new Fields("url"));
	}

	public static String extractURL(String twit, String pattern) {
		// pattern matching
		try {
			Pattern patt  = Pattern.compile(pattern);
			Matcher matcher = patt.matcher(twit);
			
			if(matcher.find()) {						
				return matcher.group();
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";		
	}
	
}
