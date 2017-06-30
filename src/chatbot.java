public class chatbot {
    
    public static void main(String[] args) throws Exception {
    	
        // Now start our bot up.
        chatbot_sub bot = new chatbot_sub();
        
        // Enable debugging output.
        bot.setVerbose(true);
        
        // Connect to the IRC server.
        bot.connect("irc.quakenet.org");

        // Join the #pircbot channel.
        bot.joinChannel("#HatersBackOff");
    }
    
}