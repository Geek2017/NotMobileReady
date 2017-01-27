
package pageinsight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
   
   private String getHeaderContents(String theUrl)
   {
       try
       {
            URL url_api = new URL("https://www.googleapis.com/pagespeedonline/v2/runPagespeed?url="+protocol+""+theUrl+"&key=AIzaSyCPFRwvYYi5ASk2g9-RMIztcYSUMo2q_Gc&strategy=mobile");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url_api.openConnection();
            httpURLConnection.setConnectTimeout(Settings.settings("connection_timeout")); 
            httpURLConnection.setReadTimeout(Settings.settings("read_timeout")); 
           
               StringBuilder builder = new StringBuilder();
                builder.append(httpURLConnection.getResponseCode())
                .append(" ")
                .append(httpURLConnection.getResponseMessage())
                .append("\n");

                Map<String, List<String>> map = httpURLConnection.getHeaderFields();

                for (Map.Entry<String, List<String>> entry : map.entrySet())
                {
                    if (entry.getKey() == null) 
                        continue;
                    builder.append( entry.getKey())
                           .append(": ");

                    List<String> headerValues = entry.getValue();
                    Iterator<String> it = headerValues.iterator();

                    if (it.hasNext()) {
                                builder.append(it.next());

                                while (it.hasNext()) {
                                    builder.append(", ")
                                           .append(it.next());
                                }
                    }
                    builder.append("\n");
                }


                System.out.println(builder);
       }
       catch(MalformedURLException ex) 
       {
           System.out.println(ex);
       }
       catch (IOException ex) 
        {
            System.out.println(ex);
        } 
       return "";
               
   }
   private String saveContents(String theUrl, int id, int totalhost, int counter )
   {   
       startTime = System.currentTimeMillis();
       
       String result = "";

       try
       {
            URL url_api = new URL("https://www.googleapis.com/pagespeedonline/v2/runPagespeed?url="+protocol+""+theUrl+"&key=AIzaSyCPFRwvYYi5ASk2g9-RMIztcYSUMo2q_Gc&strategy=mobile");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url_api.openConnection();
            httpURLConnection.setConnectTimeout(Settings.settings("connection_timeout")); 
            httpURLConnection.setReadTimeout(Settings.settings("read_timeout"));         
            
             if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) 
             {
                 InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                   BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);

                   String line = null;

                   while((line = bufferedReader.readLine()) != null){
                       result += line;
                   }

                  bufferedReader.close();
                  
                   System.out.println();
                   System.out.println("Url : "+Model.Url(id));
                   System.out.println("Response : "+httpURLConnection.getResponseCode());
                   endTime = System.currentTimeMillis() ;
                   System.out.println( "Elapsed Time: "+((endTime - startTime) / 1000) +" sec" );
                   ParseResult(result,Model.Url( id ),id);
                   System.out.println("" + counter +" out of : ("+  Results.full_progress() + ")");
                   System.out.println();
             }
             else
             {
                   status_code = skip;
                   String url = Model.Url(id);
                   String result_id = Model.Url(id);
                   String string_result_score0 = skip;
                   String stat = skip;
                   String string_result_score1 = skip;
                   
                   System.out.println();
                   
                   System.out.println( "Url : "+ Model.Url( id ) );
                   System.out.println("Response : "+httpURLConnection.getResponseCode());
                    endTime = System.currentTimeMillis() ;
                   System.out.println( "Elapsed Time: "+((endTime - startTime) / 1000) +" sec" );
                   Model.Save_url(status_code,url,url,result_id,string_result_score0,stat,string_result_score1,id);
                   System.out.println("" + counter +" out of : ("+   Results.full_progress() + ")");
                   System.out.println();
             }
       }
       catch(MalformedURLException ex) 
       {
           System.out.println(ex);
       }
       catch (IOException ex) 
       {
            System.out.println("Read timed out: "+theUrl+" more than: "+Settings.settings("connection_timeout")+ " milliseconds");
       } 
       return "";
   }
   private static String ParseResult(String json,String url, int id){
         
       status_code = "save";
       String stat=""; 
       
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
               Model.Save_url(status_code,url,url,result_id,string_result_score0,stat,string_result_score1,id);
            }
            catch( Exception ex)
            {
                System.out.println(ex);
            }
       }
       catch( JSONException ex )
       {
           System.out.println( ex );
       }
       
         return "";
    }
   public void printHeaders(String url, int id) 
   {
       getHeaderContents(url);
   }
   public void saveParseData(String url, int id, int totalhost, int counter) 
   {
      saveContents(url,id,totalhost,counter);
   }
}