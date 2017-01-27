package pageinsight;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Results {
    
    private static Connection con = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private static final String tbl_result = "urlresult";

    public static void results()
    {
        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dmy = dmyFormat.format(myDate);
        System.out.println( "- - - - - - - - - - - - - - - - - - - - - - - - - - - - " );
        System.out.println( "Parsed(" +Results.done(tbl_result,dmy)+")" );
        System.out.println( "Total("+in_progress()+")");
        System.out.println( "Remaining("+full_progress()+")");
        System.out.println( "- - - - - - - - - - - - - - - - - - - - - - - - - - - - " );
    }
    public static int full_progress()
    {
        String urlNames = "";
        int counter = 0;
         try
         {
             con = ConnectionManager.getConnection();
             stmt = con.createStatement();
             rs = stmt.executeQuery("SELECT a.url FROM urllist a\n" +
                                    "LEFT JOIN urlresult b \n" +
                                    "ON a.id = b.urllist_id\n" +
                                    "WHERE b.urllist_id IS NULL");

             while (rs.next())
             {
                 urlNames = rs.getString(1);
                 
                 ArrayList<String> myDomain = Settings.domains("domain");
                 
                 for (String thisDomain : myDomain) 
                 {
                    boolean startWith = urlNames.contains( thisDomain  ) ;

                    if (startWith) 
                    {
                        counter += 1;
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
         
         return counter;
    }
    public static int in_progress()
    {
         int count = 0;
         
         try
         {
             con = ConnectionManager.getConnection();
             stmt = con.createStatement();
             rs = stmt.executeQuery("SELECT COUNT(*) FROM urllist a\n" +
                                    "LEFT JOIN urlresult b \n" +
                                    "ON a.id = b.urllist_id\n" +
                                    "WHERE b.urllist_id IS NULL");

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
    public static int done(String table, String dmy) 
    {
        int count = 0;

         try
         {
             con = ConnectionManager.getConnection();
             stmt = con.createStatement();
             rs = stmt.executeQuery("SELECT COUNT(*) FROM "+table+" \n" +
                                    "WHERE time_stamp LIKE '"+dmy+"%'");

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
    
}
