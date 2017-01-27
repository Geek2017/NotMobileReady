package pageinsight;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Settings {
    
    static BufferedReader bReader;
    private static String name;
    private static int settings  = 0;
    private static final String dataFileName = FileLocation.location("settings.txt");
    private static String line;
    
    public static int settings(String settings_name)
    {
        try
        {
            bReader = new BufferedReader(new FileReader(dataFileName));
            
            while ((line = bReader.readLine()) != null) 
            {
                String datavalue[] = line.split(",");
                if(datavalue[0].equals(settings_name))
                {
                    settings = Integer.parseInt(datavalue[1].replaceAll("\\s+",""));
                }
            }
            bReader.close();  
        }
        catch(FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
        catch(IOException ex)
        {
             System.out.println(ex.getMessage());
        }
        return settings ;
    }
    public static ArrayList<String>domains(String settings_name)
    {
        ArrayList<String> domain = new ArrayList();
        
        try
        {
            bReader = new BufferedReader(new FileReader(dataFileName));
            
            while ((line = bReader.readLine()) != null) 
            {
                String datavalue[] = line.split(",");
                if(datavalue[0].equals(settings_name))
                {
                    domain.add( datavalue[1].replaceAll("\\s+","") );
                }
            }
            bReader.close();  
        }
        catch(FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
        catch(IOException ex)
        {
             System.out.println(ex.getMessage());
        }
        return(domain);
    }
}
