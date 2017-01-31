package pageinsight;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Model {
   
private static Connection con = null;
private static Statement stmt = null;
private static ResultSet rs = null;
private static final String tblUrlResult = "urlresult";
   
    public static ArrayList<Integer> myHosts(String query)
    {  
        ArrayList<String> myDomain = Settings.domains("domain");
        ArrayList<Integer> hosts = new ArrayList<>();

         try
              {  
                con = ConnectionManager.getConnection();
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next())  {
                 for (String thisDomain : myDomain) 
                 {
                      boolean startWith = rs.getString(2).contains( thisDomain ) ; 
                      if (startWith) 
                      {
                          hosts.add(rs.getInt(1));   
                      }
                 }
                 
                }
              }
              catch(SQLException e)
              { 
                  System.out.println(e);
              }
              finally
              {
                    try { con.close(); } catch (Exception e) { }
                    try { stmt.close(); } catch (Exception e) { }
                    try { rs.close(); } catch (Exception e) { }
              }
              
        return(hosts);

   }
   static public int get_url_id(String title) 
   {
       int id = 0;
       
        try
          {  
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from urllist WHERE url='"+title+"'");

            while(rs.next())  
            id = rs.getInt(1);
               
          }
          catch(SQLException e)
          { 
              System.out.println(e);
          }
          finally
          {
              try { con.close(); } catch (Exception e) { }
              try { stmt.close(); } catch (Exception e) { }
              try { rs.close(); } catch (Exception e) { }
          }
          
        return id;
   }
   public static String Url(int id)
   {
       String url = "";

        try
          {  
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from urllist WHERE id='"+id+"'");

            while(rs.next())  
            url = rs.getString(2);
                
          }
          catch(SQLException e)
              { 
                 // System.out.println(e);
              }
              finally
              {
//                    try { con.close(); } catch (Exception e) { }
//                    try { stmt.close(); } catch (Exception e) { }
//                    try { rs.close(); } catch (Exception e) { }
              }
         
        return url;
   }
   public static String SaveParsed(String url, String site_name, String site_url,String speed_score, String status, String mobile_freindly_score,int urllist_id, int responsecode)
   {
       
       String sql = "";
         try
         {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
           
            sql = "INSERT INTO urlresult(urllist_id,site_name,site_url,speed_score,status,mobile_freindly_score)VALUES ('"+urllist_id+"','"+url+"', '"+site_url+"', '"+speed_score+"','"+status+"','"+mobile_freindly_score+"')";
            stmt.executeUpdate(sql);
                  
           
            rs = stmt.executeQuery("SELECT * FROM "+ tblUrlResult +" WHERE urllist_id="+urllist_id );
            while(rs.next()){  
                System.out.println("Url : " + url +" ");
                System.out.println("Response : "+ responsecode);
                System.out.println("Result : Speed score(" + rs.getString(5)+") ");
                System.out.println("Mobile freindliness Grade: ("+rs.getString(7)+")");
                System.out.println("Status: (" + rs.getString(6)+") ");
            }
        
         }
         catch(SQLException ex)
         {
            //System.out.println(ex);
         }
         finally
         {
//             try { con.close(); } catch (Exception e) { }
//             try { stmt.close(); } catch (Exception e) { }
//             try { rs.close(); } catch (Exception e) { }
         }
         
         return "";
   }
   public static String SaveSkip(String url, int urllist_id)
   {
       
       String sql = "";
       
         try
         {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
          
            sql = "INSERT INTO urlresult(urllist_id,site_name,site_url,speed_score,status,mobile_freindly_score)VALUES ('"+urllist_id+"','"+url+"', '"+url+"', 'skipped','skipped','skipped')";
            stmt.executeUpdate(sql);
         }
         catch(SQLException ex)
         {
            //System.out.println(ex);
         }
         finally
         {
//             try { con.close(); } catch (Exception e) { }
//             try { stmt.close(); } catch (Exception e) { }
//             try { rs.close(); } catch (Exception e) { }
         }
         
         return "";
   }
   public static int Count(String table) 
   {
       int count = 0;
       
        try
        {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM "+table);
        
            while (rs.next())
            {
                count = rs.getInt(1);
            }
        }
        catch(SQLException e)
          { 
              System.out.println(e);
          } 
          finally
          {
              try { con.close(); } catch (Exception e) { }
              try { stmt.close(); } catch (Exception e) { }
              try { rs.close(); } catch (Exception e) { }
          }
        
        return count; 
   }
   public static boolean check_parsed(String url_name)
   {
        boolean found = false;
       
        try
        {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM urlresult WHERE site_name='"+url_name+"'");
        
            if(rs.next()){
                found = true;
            }else{
                found = false;
            }
        }
        catch(SQLException e)
          { 
              System.out.println(e);
          }
          finally
          {
              try { con.close(); } catch (Exception e) { }
              try { stmt.close(); } catch (Exception e) { }
              try { rs.close(); } catch (Exception e) { }
          }
        
        return found; 
    }
}
