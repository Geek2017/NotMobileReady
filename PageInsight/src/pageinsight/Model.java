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

        ArrayList<Integer> hosts = new ArrayList<>();

         try
              {  
                con = ConnectionManager.getConnection();
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next())  
                hosts.add(rs.getInt(1));
                    
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
              System.out.println(e);
          } 
          finally
          {
              try { con.close(); } catch (Exception e) { }
              try { stmt.close(); } catch (Exception e) { }
              try { rs.close(); } catch (Exception e) { }
          }
         
        return url;
   }
   static public String Save_url(String status_code, String url, String site_name, String site_url,String speed_score, String status, String mobile_freindly_score,int urllist_id)
   {
       
       String sql = "";
       int generated_keys = 0;
       
         try
         {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            
            switch(status_code)
            {
                case "skip":
                        sql = "INSERT INTO urlresult(urllist_id,site_name,site_url,speed_score,status,mobile_freindly_score)VALUES ('"+urllist_id+"','"+url+"', '"+site_url+"', '0','skipped','0')";
                        generated_keys = stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
                    break;
                    
                case "save":
                     sql = "INSERT INTO urlresult(urllist_id,site_name,site_url,speed_score,status,mobile_freindly_score)VALUES ('"+urllist_id+"','"+url+"', '"+site_url+"', '"+speed_score+"','"+status+"','"+mobile_freindly_score+"')";
                     generated_keys = stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
                    break;
            }
           
            rs = stmt.getGeneratedKeys();

            if (rs.next()){
                generated_keys = rs.getInt(1);
            }
                 
           rs = stmt.executeQuery("SELECT * FROM "+ tblUrlResult +" WHERE id="+generated_keys );
            while(rs.next()){  
                System.out.println("Result : Speed score(" + rs.getString(5)+") ");
                System.out.println("Mobile freindliness Grade: ("+rs.getString(7)+")");
                System.out.println("Status: (" + rs.getString(6)+") ");
            }
        
         }
         catch(SQLException ex)
         {
             System.out.println(ex);
         }
         finally
         {
             try { con.close(); } catch (Exception e) { }
             try { stmt.close(); } catch (Exception e) { }
             try { rs.close(); } catch (Exception e) { }
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
