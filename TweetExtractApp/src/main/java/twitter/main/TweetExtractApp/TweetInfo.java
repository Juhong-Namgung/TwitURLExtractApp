package twitter.main.TweetExtractApp;

import java.util.Date;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Scopes;
import twitter4j.Status;
import twitter4j.User;

public class TweetInfo {
	
	private String userName;
	private Date createdDate;
	private long userId;
	private String text;
	private String source;
	private long inReplyToStatusId;
	private long inReplyToUserId;
	private String inReplyToScreenName;
	private GeoLocation geoLocation;
	private Place place;
	private int favoritCount;
	private User user;
	private Status retweetedStatus;
	private long[] contributors;
	private int retweetCount;
	private long currentUserRetweetId;	
	private String lang;
	private Scopes scpoes;
	private String[] withheldInCountries;
	private long quotedStatusId;
	private Status quotedStatus;
	
	String getUserName() {
		return userName;
	}
	void setUserName(String userName) {
		this.userName = userName;
	}
	Date getCreatedDate() {
		return createdDate;
	}
	void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	long getUserId() {
		return userId;
	}
	void setUserId(long userId) {
		this.userId = userId;
	}
	String getText() {
		return text;
	}
	void setText(String text) {
		this.text = text;
	}
	String getSource() {
		return source;
	}
	void setSource(String source) {
		this.source = source;
	}
	long getInReplyToStatusId() {
		return inReplyToStatusId;
	}
	void setInReplyToStatusId(long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}
	long getInReplyToUserId() {
		return inReplyToUserId;
	}
	void setInReplyToUserId(long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}
	String getInReplyToScreenName() {
		return inReplyToScreenName;
	}
	void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}
	GeoLocation getGeoLocation() {
		return geoLocation;
	}
	void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}
	Place getPlace() {
		return place;
	}
	void setPlace(Place place) {
		this.place = place;
	}
	int getFavoritCount() {
		return favoritCount;
	}
	void setFavoritCount(int favoritCount) {
		this.favoritCount = favoritCount;
	}
	User getUser() {
		return user;
	}
	void setUser(User user) {
		this.user = user;
	}
	Status getRetweetedStatus() {
		return retweetedStatus;
	}
	void setRetweetedStatus(Status retweetedStatus) {
		this.retweetedStatus = retweetedStatus;
	}
	long[] getContributors() {
		return contributors;
	}
	void setContributors(long[] contributors) {
		this.contributors = contributors;
	}
	int getRetweetCount() {
		return retweetCount;
	}
	void setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
	}
	long getCurrentUserRetweetId() {
		return currentUserRetweetId;
	}
	void setCurrentUserRetweetId(long currentUserRetweetId) {
		this.currentUserRetweetId = currentUserRetweetId;
	}
	String getLang() {
		return lang;
	}
	void setLang(String lang) {
		this.lang = lang;
	}
	Scopes getScpoes() {
		return scpoes;
	}
	void setScpoes(Scopes scpoes) {
		this.scpoes = scpoes;
	}
	String[] getWithheldInCountries() {
		return withheldInCountries;
	}
	void setWithheldInCountries(String[] withheldInCountries) {
		this.withheldInCountries = withheldInCountries;
	}
	long getQuotedStatusId() {
		return quotedStatusId;
	}
	void setQuotedStatusId(long quotedStatusId) {
		this.quotedStatusId = quotedStatusId;
	}
	Status getQuotedStatus() {
		return quotedStatus;
	}
	void setQuotedStatus(Status quotedStatus) {
		this.quotedStatus = quotedStatus;
	}
	
	@Override
	public String toString() {
		
		return "(1) userName= " +this.userName +" (2) Date= " + this.createdDate + " (3) userId= " + this.userId + " (4) text= " + this.text;
	}
}
