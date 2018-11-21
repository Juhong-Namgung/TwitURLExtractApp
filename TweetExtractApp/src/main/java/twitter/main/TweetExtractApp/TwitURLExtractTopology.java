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
	
	
	private static String consumerKey = "";
    private static String consumerSecret = "";
    private static String accessToken = "";
    private static String accessTokenSecret = "";
	
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
		ReportBolt reportBolt = new ReportBolt();
		
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("twit-spout", spout);
		builder.setBolt("extract-bolt", extractBolt).shuffleGrouping("twit-spout");
		builder.setBolt("expand-bolt", expandBolt).shuffleGrouping("extract-bolt");
		builder.setBolt("report-bolt", reportBolt).shuffleGrouping("expand-bolt");
		
		Config config = new Config();
		config.setNumWorkers(4);
		
		StormSubmitter.submitTopology("twiturl", config, builder.createTopology());
		
	}
}
