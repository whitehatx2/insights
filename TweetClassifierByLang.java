/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package insights;

/**
 *
 * @author Joshua
 */
import java.io.*;
import java.util.regex.*;
public class TweetClassifierByLang {
    public static void main(String args[]){
        
        if(args.length != 1) {
            System.err.println("Invalid command line, exactly one argument required");
            System.exit(1);
        }
        
        BufferedReader br = null;
        BufferedWriter bw = null;
        Pattern p = Pattern.compile("\"lang\": \"en\", \"created_at\"");
        Matcher m;
        int totalTweetCounter = 0, englishTweetCounter = 0;
        String sCurrentLine;
        try{
            br = new BufferedReader(new FileReader(args[0]));
            bw = new BufferedWriter(new FileWriter("EnglishTweets.txt"));
            
            while ((sCurrentLine = br.readLine()) != null ){
                totalTweetCounter++;
                m = p.matcher(sCurrentLine);
                if(m.find()==true){
                    bw.write(sCurrentLine);
                    bw.newLine();
                    englishTweetCounter++;
                    System.out.println(sCurrentLine);
                }
            } 
            System.out.println("Number of tweets:"+totalTweetCounter);
            System.out.println("Number of English tweets:"+englishTweetCounter);
        }catch(IOException e){
            System.out.println(e.getMessage());
            System.out.println("Exception1!");
        } finally {
			try {
				if (br != null)
                                {  
                                    br.close();                                   
                                }
                                if (bw != null)
                                {  
                                    bw.close();                                   
                                }
			} catch (IOException ex) {
                            System.out.println(ex.getMessage());
				System.out.println("Exception2!!");
			}
		}
    }
}
