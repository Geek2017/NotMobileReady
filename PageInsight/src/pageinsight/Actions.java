
package pageinsight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;


class Actions {
    
   public String count;
   private static long startTime;
   private static long endTime;
   
   private final String table = "urllist";
   public final String skip = "skip";
   public static String status_code = null;
   
   public static final String protocol = "http://";
   
 
   public void saveContents(String theUrl, int id, int totalhost, int counter )
   {   
       /*startTime = System.currentTimeMillis();
       endTime = System.currentTimeMillis() ;
       System.out.println( "Elapsed Time: "+((endTime - startTime) / 1000) +" sec" );
       System.out.println("" + counter +" out of : ("+  Results.full_progress() + ")");*/
                     
       String result = "";

       try
       {
            URL url = new URL("https://www.googleapis.com/pagespeedonline/v2/runPagespeed?url="+protocol+""+theUrl+"&key=AIzaSyCPFRwvYYi5ASk2g9-RMIztcYSUMo2q_Gc&strategy=mobile");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(Settings.settings("connection_timeout")); 
            con.setReadTimeout(Settings.settings("read_timeout"));         

               if (con.getResponseCode() == HttpURLConnection.HTTP_OK) 
               {
                    InputStreamReader input = new InputStreamReader(con.getInputStream());
                    BufferedReader b = new BufferedReader(input);

                    String line = null;

                    while((line = b.readLine()) != null){
                       result += line;

                      }
                     ParseResult(result,theUrl,id,con.getResponseCode());        
               }
               else
               {  
                     ArrayList<UrlSkipped> objLists = new ArrayList<>();
                     objLists.add( new UrlSkipped(1 , id,  theUrl, con.getResponseCode() )); 
                     for (UrlSkipped obj : objLists) 
                     {
                         System.out.println(obj);
                     }
               }   
       }
       catch(MalformedURLException ex) 
       {
           System.out.println(ex.getMessage());
       }
       catch (IOException ex) 
       {
          
            System.out.println("Read timed out: "+theUrl+" more than: "+Settings.settings("connection_timeout")+ " milliseconds");
       }
       
   }
  
   private void ParseResult(String json,String url, int id, int respCode){

       try
       {
           JSONObject jsonObject = new JSONObject(json);

           JSONObject JSONObject_rg = jsonObject.getJSONObject("ruleGroups");
           JSONObject JSONObject_sc0 = JSONObject_rg.getJSONObject("SPEED");
           int result_score0  = JSONObject_sc0.getInt("score");
           String string_result_score0 = Integer.toString(result_score0);

           String result_title = jsonObject.getString("title");
           String result_id = jsonObject.getString("id");

           JSONObject JSONObject_sc1 = JSONObject_rg.getJSONObject("USABILITY");
              int result_score1 = JSONObject_sc1.getInt("score");
              String string_result_score1 = Integer.toString(result_score1);
              String stat=""; 

              if (result_score1 > 85) 
              {
                stat = "Mobile-Friendly";
              } 
              else
              {
                stat = "Not Mobile-Friendly";
              }
            
            try
            {
                Model.SaveParsed(url,url,result_id,string_result_score0,stat,string_result_score1,id, respCode);
            }
            catch( Exception ex)
            {
                System.out.println("reason : " + ex.getMessage());
            }
       }
       catch( JSONException ex )
       {
           System.out.println( ex );
       }
        }
    }
  