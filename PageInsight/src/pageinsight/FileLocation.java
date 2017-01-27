
package pageinsight;

import java.io.File;
import java.io.IOException;

public class FileLocation {
    
    private static String dir = "";
    
    public static String location(String file_name) 
    {
         try 
         {
            File currentDirectory = new File(new File("..").getAbsolutePath());
            dir = currentDirectory.getCanonicalPath() + "/" + file_name ;
         }
         catch(IOException ex)
         {
            System.err.println(ex.getMessage());
         }
         
         return dir;
    }
}
