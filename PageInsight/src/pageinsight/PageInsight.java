
package pageinsight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PageInsight {
   
    public static String choice = "";
   
   public static void main(String args[]) 
   { 
      menu();  
   }
   public static void menu()
   {
      System.out.println("1 Start Engine");
      System.out.println("2 View Results ");
      System.out.println("3 Upload Url ");
      System.out.println("4 Exit Application  ");
      System.out.println();
      System.out.print("Enter choice : ");
      BufferedReader inp = new BufferedReader (new InputStreamReader(System.in));
      
       try {
           choice = inp.readLine();
           
           if(choice.equals("1"))
           {
               runEngine();
           }
           else if(choice.equals("2"))
           {
               viewEngineResults();
           }
           else if(choice.equals("3"))
           {
               System.out.println("Please wait validating..");
               uploadEngine();
           }
           else if(choice.equals("4"))
           {
               System.out.println("Application exited.");
               System.exit(0);
           }
           else
           {
               System.err.println("Command not found.");
               menu();
           }
           
       } catch (IOException ex) {
           System.err.println(ex.getMessage());
       }
   }
   public static void viewEngineResults()
   {
       Results.results();
       menu();
   }
   public static void uploadEngine()
   {
        Thread t1 = new Thread(new CsvReader());
        t1.start();    
   }
   public static void runEngine()
   {
      Actions action = new Actions();

      ThreaD T1 = new ThreaD( "Page insight", action );
      T1.start();
      try 
      {
         T1.join();
      }
      catch( InterruptedException e) 
      {
         System.err.println(e.getMessage());
      }
   }
}