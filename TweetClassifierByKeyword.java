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

public class TweetClassifierByKeyword {

    public static void main(String args[]){
        
       if(args.length != 1) {
            System.err.println("Invalid command line, exactly one argument required");
            System.exit(1);
        }
        
        BufferedReader br = null;
        BufferedWriter bw = null;
        int tweetProcessed = 0;
        Pattern p11, p12, p13, p14, p15, p16, p17, p18, p19, p191, p192, p2, p3;
        Matcher m11, m12, m13, m14, m15, m16, m17, m18, m19, m191, m192, m2, m3;
        String sCurrentLine, filename = null;
        
        //Creating folder structure
        File mainDir = new File("TweetsByBrand");
        mainDir.mkdir();
        File huaweiDir = new File("TweetsByBrand/Huawei");
        huaweiDir.mkdir();
        File zteDir = new File("TweetsByBrand/ZTE");
        zteDir.mkdir();
        File beatsDir = new File("TweetsByBrand/Beats");
        beatsDir.mkdir();
        File skullcandyDir = new File("TweetsByBrand/SkullCandy");
        skullcandyDir.mkdir();
        File boseDir = new File("TweetsByBrand/Bose");
        boseDir.mkdir();
        File mcdDir = new File("TweetsByBrand/McDonalds");
        mcdDir.mkdir();
        File bkDir = new File("TweetsByBrand/BurgerKing");
        bkDir.mkdir();
        File costcoDir = new File("TweetsByBrand/Costco");
        costcoDir.mkdir();
        File walmartDir = new File("TweetsByBrand/Walmart");
        walmartDir.mkdir();
        File nookDir = new File("TweetsByBrand/Nook");
        nookDir.mkdir();
        File koboDir = new File("TweetsByBrand/Kobo");
        koboDir.mkdir();
        //end creating folder structure
        
        try{
            br = new BufferedReader(new FileReader(args[0]));
            
            while ((sCurrentLine = br.readLine()) != null ){
                
                //Creating regular expressions for matching keywords
                p11 = Pattern.compile("Huawei|Ascend P2|Ascend W1|Ascend mate|Ascend D2|Ascend G600|M886|U8651T|M920|U8652|Ascend Y200|Ascend P1|Ascend G300|Ascend P1s|Ascend D1 quad|Ascend X|M865|M835|IDEOS X3|IDEOS X5|M860|IDEOS|U8300|U8110|U8100|U8220|Premia 4G|Ascend Y|Unite Q|Ascend Q|Activa 4G|Ascend II|U8652|U8800|M650|Mediapad|E368|F256|EC5072|E366|E587|F253|E397Bu-502|FT2260",Pattern.CASE_INSENSITIVE);         
                m11 = p11.matcher(sCurrentLine);
                p12 = Pattern.compile("ZTE|Cricket Engage LT|V768|Z431|WF720|Z331|Z221|AC3781|Rocket 3.0|Rocket 4G",Pattern.CASE_INSENSITIVE);         
                m12 = p12.matcher(sCurrentLine);
                p13 = Pattern.compile("Beats|Beats by dr. dre|Beats by dre|Beats audio|powerbeats|solo HD",Pattern.CASE_INSENSITIVE);         
                m13 = p13.matcher(sCurrentLine);
                p14 = Pattern.compile("Skullcandy|Hesh 2|Ink’d 2",Pattern.CASE_INSENSITIVE);         
                m14 = p14.matcher(sCurrentLine);
                p15 = Pattern.compile("Bose|QuietComfort|SIE2i|SIE2i|MIE2i|MIE2|AE2|AE2i|OE2|OE2i",Pattern.CASE_INSENSITIVE);         
                m15 = p15.matcher(sCurrentLine);
                p16 = Pattern.compile("Angus|McChicken|Big Mac|McDouble|McCafe|McNuggets|McBites|McMuffin|McRib|McWrap|McFlurry",Pattern.CASE_INSENSITIVE);         
                m16 = p16.matcher(sCurrentLine);
                p17 = Pattern.compile("Burger King|Croissan’wich|Whopper|Double whopper|Triple whopper|Tendercrisp|Tendergrill|Whopper Jr.|Chick’n crisp|Loaded Tater Tots",Pattern.CASE_INSENSITIVE);         
                m17 = p17.matcher(sCurrentLine);
                p18 = Pattern.compile("Costco|Costco Pharmacy|Costco Wholesale|Costco Photo Center|Costco Travel",Pattern.CASE_INSENSITIVE);         
                m18 = p18.matcher(sCurrentLine);
                p19 = Pattern.compile("Walmart|Walmart Gift Registry",Pattern.CASE_INSENSITIVE);         
                m19 = p19.matcher(sCurrentLine);
                p191 = Pattern.compile("Barnes and Noble Nook|Nook| Nook HD|Nook Simple Touch",Pattern.CASE_INSENSITIVE);         
                m191 = p191.matcher(sCurrentLine);
                p192 = Pattern.compile("Kobo|Kobo arc|Kobo glo|Kobomini|Kobo touch|Kobo aura HD",Pattern.CASE_INSENSITIVE);         
                m192 = p192.matcher(sCurrentLine);
                //end creating regular expressions for matching keywords
                
                //Extracting tweet-id to create filename
                p2 = Pattern.compile("(, \"id\": )(.{18})(, \"favorite_count\")");
                m2 = p2.matcher(sCurrentLine);
                if(m2.find()== true){
                   StringBuilder sb = new StringBuilder();
                   sb.append(m2.group(2));
                   sb.append(".txt");
                   filename = sb.toString();
                }
                //End of extracting tweet-id to create filename
                
                //Putting file in appropriate directory 
                if(m11.find()== true){
                    File file = new File(huaweiDir,filename);
                    bw = new BufferedWriter(new FileWriter(file));
                }
                else if(m12.find() == true){
                    File file = new File(zteDir,filename);
                    bw = new BufferedWriter(new FileWriter(file));
                }
                else if(m13.find() == true){
                    File file = new File(beatsDir,filename);
                    bw = new BufferedWriter(new FileWriter(file));
                }
                else if(m14.find() == true){
                    File file = new File(skullcandyDir,filename);
                    bw = new BufferedWriter(new FileWriter(file));
                }
                else if(m15.find() == true){
                    File file = new File(boseDir,filename);
                    bw = new BufferedWriter(new FileWriter(file));
                }
                else if(m16.find() == true){
                    File file = new File(mcdDir,filename);
                    bw = new BufferedWriter(new FileWriter(file));
                }
                else if(m17.find() == true){
                    File file = new File(bkDir,filename);
                    bw = new BufferedWriter(new FileWriter(file));
                }
                else if(m18.find() == true){
                    File file = new File(costcoDir,filename);
                    bw = new BufferedWriter(new FileWriter(file));
                }
                else if(m19.find() == true){
                    File file = new File(walmartDir,filename);
                    bw = new BufferedWriter(new FileWriter(file));
                }
                else if(m191.find() == true){
                    File file = new File(nookDir,filename);
                    bw = new BufferedWriter(new FileWriter(file));
                }
                else if(m192.find() == true){
                    File file = new File(koboDir,filename);
                    bw = new BufferedWriter(new FileWriter(file));
                }
                else {
                    File file = new File(mainDir, filename);
                    bw = new BufferedWriter(new FileWriter(file));
                }
                //end putting file in appropriate directory
                
                //Extracting text and writing to file
                p3 = Pattern.compile("(\"text\": \")(.*)(\", \"in_reply_to_status_id\")");
                m3 = p3.matcher(sCurrentLine);
                if(m3.find()== true){
                  bw.write(m3.group(2));
                  bw.newLine();
                  System.out.println(m3.group(2));  
                  bw.flush();
                  bw.close();
                }
               //End of extracting text and writing to file
                
              tweetProcessed++;
            } 
            
            System.out.println("Number of tweets processed:"+tweetProcessed);
            
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
