import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jibble.pircbot.*;

import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class chatbot_sub extends PircBot {
	// create object of GETAPI class
    GetAPI data = new GetAPI();
    // create config builder
    ConfigurationBuilder buildcfg = new ConfigurationBuilder();

    // setting the name function
    public chatbot_sub() {
        this.setName("HATERSBACKOFF-BOT"); 
    }
    
    // function which sends the message
    public void onMessage(String channel, String sender, String login, String hostname, String message){
    
    // if !time is seen, it'll post the time
    		if (message.equalsIgnoreCase("!time")) {
    			String time = new java.util.Date().toString();
    			sendMessage(channel, ": The time is now " + time);
    		}
    
    // if !help is seen, it'll post help
    		if (message.equalsIgnoreCase("!help")) {
    			sendMessage(channel, "To find out the time type !time" );
    			sendMessage(channel, "To find out the temperature in a certain city type !weather and City name afterwards" );
    			sendMessage(channel, "To find out what's #1 Trending on twitter type !trending");
    			
    		}
    
    // if !trending is seen, it'll post top 10 trending things in twitter
    		if (message.equalsIgnoreCase("!trending")) {
    			
				sendMessage(channel, "Here's the top 10 things trending on twitter right now: " );
    			try {
    				buildcfg.setDebugEnabled(true)
                    .setOAuthConsumerKey("oKFsi5G3oJhWJrGTau3Q7XoXD")
                    .setOAuthConsumerSecret("nhqKgQpi4INJ1Bbutxio9VR8mcEityjjqOV4pnwMJXKm0NHAS9")
                    .setOAuthAccessToken("802584220015198208-QVM05w21fb4KYAqaAUHUYZphGFdnpko")
                    .setOAuthAccessTokenSecret("lNuEP4Ratm4hXuF3WAdxDhnb6CYNaKY7rZMdU6cU5cUtO");
           
                    TwitterFactory tweetfact = new TwitterFactory(buildcfg.build());
                    Twitter twitter = tweetfact.getInstance();
                   
                    String trendingTweets;
                    Trends trends = twitter.getPlaceTrends(2388929);
                    for(int i = 0; i < 10; i++){
                         trendingTweets = trends.getTrends()[i].getName();
                         sendMessage(channel, trendingTweets);
                    }	
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    // if !weather is seen, it'll check if the next word is a known city and it'll find the weather for it
    		String[] payload = message.split(" ");
    		if (payload[0].equalsIgnoreCase("!weather") && payload[1] != null) {
    			String weatherOutput = "";
    			try {
    				weatherOutput = data.weather(payload[1]);
        			sendMessage(channel, weatherOutput);
				} catch (IOException e) {
					e.printStackTrace();
				}

    	    }

    
    // import commoncursewords text file, read them in each line and determine if the user inputs in any foul language
    		try (BufferedReader br = new BufferedReader(new FileReader("commoncursewords.txt"))) {
    		    String line;
    		    while ((line = br.readLine()) != null) { // if it's null, end of file
    		    	for(int i = 0; i < payload.length; i++){ // for loop for each word
    		    		try { // nested try/catch because you are importing a text file and importing words from a IRC
    		    			if(payload[i].equalsIgnoreCase(line)){
    		    				sendMessage(channel, "Please don't use foul language. Stop harassment for good. 20% of children who are cyber bullied contemplate suicide and 1/10 of them attempt suicide. Haters. Back. OFF!");
    		    			}
    		    		} catch (Exception ex){
    		    			sendMessage(channel, "Error");
    		    		}
    		    	}    		    			
    		    }
    		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
}