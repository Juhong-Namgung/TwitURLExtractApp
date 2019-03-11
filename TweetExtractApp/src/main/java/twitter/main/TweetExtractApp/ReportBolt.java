package twitter.main.TweetExtractApp;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import twitter4j.Status;

public class ReportBolt extends BaseRichBolt {
	//TweetInfo info;
	OutputCollector collector;
		
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		//this.info = new TweetInfo();
	}

	public void execute(Tuple input) {
		
		/*Status tweet = (Status) input.getValueByField("tweet");
		
		info.setCreatedDate(tweet.getCreatedAt());
		info.setUserId(tweet.getId());
		info.setText(tweet.getText());
		info.setSource(tweet.getSource());
		info.setInReplyToStatusId(tweet.getInReplyToStatusId());
		info.setInReplyToUserId(tweet.getInReplyToUserId());
		info.setInReplyToScreenName(tweet.getInReplyToScreenName());
		info.setGeoLocation(tweet.getGeoLocation());
		info.setPlace(tweet.getPlace());
		info.setFavoritCount(tweet.getFavoriteCount());		
		info.setUser(tweet.getUser());
		info.setRetweetedStatus(tweet.getRetweetedStatus());
		info.setContributors(tweet.getContributors());
		info.setRetweetCount(tweet.getRetweetCount());
		info.setCurrentUserRetweetId(tweet.getCurrentUserRetweetId());
		info.setLang(tweet.getLang());
		info.setScpoes(tweet.getScopes());
		info.setWithheldInCountries(tweet.getWithheldInCountries());
		info.setQuotedStatusId(tweet.getQuotedStatusId());
		info.setQuotedStatus(tweet.getQuotedStatus());
		*/
		
		String twitURL = (String) input.getValueByField("validurl");
		
		System.out.println(twitURL);
		
	
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {		
		
	}

}
