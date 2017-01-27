
package pageinsight;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CsvReader implements Runnable {
    
    private static Connection con = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private static final String table = "urllist";
    private String sql = "";
    private int generated_keys = 0;

    @Override
    public void run() {

        ArrayList<String> myArrayHost = ReadCsv.myHostList();
        
        for (String hosts : myArrayHost)
        { 
            try 
            {
                Thread.sleep(Settings.settings("sleep"));
              
                if( ReadCsv.check_parsed( hosts ) == false) {
                     con = ConnectionManager.getConnection();
                        stmt = con.createStatement();
                
                    generated_keys = stmt.executeUpdate("INSERT INTO "+table+"(url)VALUES ('"+hosts+"')" ,Statement.RETURN_GENERATED_KEYS);

                    rs = stmt.getGeneratedKeys();

                    if (rs.next()){
                        generated_keys = rs.getInt(1);
                    }

                    System.out.println("Uploading... id: "+generated_keys+" host: "+hosts + "..done");

                }
            } 
            catch (InterruptedException | SQLException ex) 
            {
                System.err.println(ex.getMessage());
            }
            finally
            {    
                if(con!=null)
                    
                try 
                {
                    con.close();
                } 
                catch (SQLException ex) 
                {
                   System.err.println( ex.getMessage() );
                }
            }
        }
   }

}
