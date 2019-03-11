package twitter.main.TweetExtractApp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;

public class TwitURLExtractTopology {
	
	private static String consumerKey = "OAuDAsBOG38gkOIhSWAt0aKVc";
    private static String consumerSecret = "vbwRpGfH89XmBcHF0F6x4ae2ZY1SBssKET6oTc80cpDnMAmcgD";
    private static String accessToken = "109866396-yfI2NAR8XoWfbS7VVj55dIZgPM4enFYmnx6t1HNP";
    private static String accessTokenSecret = "VZy5FjwUYSgEP6jJcWy53hVtZ4xIpsmqJRzRUmWGDivVs";
	/*private static String consumerKey = "";
    private static String consumerSecret = "";
    private static String accessToken = "";
    private static String accessTokenSecret = "";
	*/
    private static Log LOG = LogFactory.getLog(TwitURLExtractTopology.class);

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
		
		if(args.length < 4) {
			LOG.error("Argument Missing Error");
		} else {
		 consumerKey =args[0];
         consumerSecret =args[1];
         accessToken =args[2];
         accessTokenSecret =args[3];
		}
		
		TwitterSpout spout = new TwitterSpout(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		
		ExtractURLBolt extractBolt = new ExtractURLBolt();
		ExpandURLBolt expandBolt = new ExpandURLBolt();
		ValidateURLBolt validateBolt = new ValidateURLBolt();
		ReportBolt reportBolt = new ReportBolt();
		
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("twit-spout", spout);
		builder.setBolt("extract-bolt", extractBolt).shuffleGrouping("twit-spout");
		builder.setBolt("expand-bolt", expandBolt).shuffleGrouping("extract-bolt");
		builder.setBolt("validate-bolt", validateBolt).shuffleGrouping("expand-bolt");
		builder.setBolt("report-bolt", reportBolt).shuffleGrouping("validate-bolt");
		
		Config config = new Config();
		config.setNumWorkers(5);
		
		StormSubmitter.submitTopology("twiturl", config, builder.createTopology());
		
	}
}
