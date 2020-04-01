package twitter.main.TweetURLExtractApp;

import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector;
import org.apache.storm.topology.TopologyBuilder;

public class TwitURLExtractTopology {

	private static String consumerKey = "consumer-key";
	private static String consumerSecret = "consumer-secret";
	private static String accessToken = "access-token";
	private static String accessTokenSecret = "access-token-secret";

	private static Log LOG = LogFactory.getLog(TwitURLExtractTopology.class);

	public static void main(String[] args)
			throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
		String[] keywords = Arrays.copyOfRange(args, 0, args.length);

		TwitterSpout spout = new TwitterSpout(consumerKey, consumerSecret, accessToken, accessTokenSecret, keywords);

		ExtractURLBolt extractBolt = new ExtractURLBolt();
		ExpandURLBolt expandBolt = new ExpandURLBolt();
		ValidateURLBolt validateBolt = new ValidateURLBolt();
		ReportBolt reportBolt = new ReportBolt();
		
		// KafkaBolt Setting
		String topic = "twit";
		Properties props = new Properties();
		props.put("metadata.broker.list", "SN02:9092,SN03:9092,SN04:9092");
		props.put("bootstrap.servers", "SN02:9092");
		props.put("acks", "1");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		KafkaBolt kafkabolt = new KafkaBolt().withProducerProperties(props)
				.withTopicSelector(new DefaultTopicSelector(topic))
				.withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper());
		;

		// Topology Build
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("twit-spout", spout);
		builder.setBolt("extract-bolt", extractBolt, 2).shuffleGrouping("twit-spout").setNumTasks(2);
		builder.setBolt("expand-bolt", expandBolt).shuffleGrouping("extract-bolt");
		builder.setBolt("validate-bolt", validateBolt, 2).shuffleGrouping("expand-bolt").setNumTasks(2);
		builder.setBolt("report-bolt", reportBolt).shuffleGrouping("validate-bolt");
		builder.setBolt("kafka-bolt", kafkabolt).shuffleGrouping("report-bolt");

		Config config = new Config();
		config.setNumWorkers(8);

		StormSubmitter.submitTopology("NumTest", config, builder.createTopology());

	}
}
