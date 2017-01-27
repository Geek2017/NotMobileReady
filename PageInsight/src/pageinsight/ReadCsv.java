
package pageinsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReadCsv {
    
    private static Connection con = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private static final String table = "urllist";
    private static final String csvFile = FileLocation.location("top-1m.csv");
    
   public static boolean check_parsed(String url_name)
   {
        boolean found = false;
       
        try
        {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM "+table+" WHERE url='"+url_name+"'");
        
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
            if(con!=null)
                
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        
        return found; 
    }
    public static ArrayList<String> myHostList()
    {
        ArrayList<String> listhost = new ArrayList<>();
        
       
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                String[] country = line.split(cvsSplitBy);

                listhost.add( country[1] );

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return(listhost);
    }
    
}