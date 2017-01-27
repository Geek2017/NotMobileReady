package pageinsight;
import java.util.ArrayList;

class ThreaD extends Thread {
    
   private Thread t;
   private final String threadName;
   private final String query = "select * from urllist";
   Actions actions;
   
   ThreaD( String name,  Actions action) {
      threadName = name;
      actions = action;
   }
   
   @Override
   public void run()
   {   
       response();
   }
   public void response()
   {
       synchronized(actions) 
        {
            ArrayList<Integer> myArray = Model.myHosts(query);
            
            int counter = 0;
            int  TotalHost = 0; //counts();
             
            for (Integer strDomainId : myArray) 
            {
                 ArrayList<String> myDomain = Settings.domains("domain");
                 
                 for (String thisDomain : myDomain) 
                 {
                        boolean startWith = Model.Url(strDomainId).contains( thisDomain  ) ;
                        
                        if (startWith) 
                        {
                            counter+=1;
                            
                            if( Model.check_parsed( Model.Url(strDomainId) ) == false) {   
                                 TotalHost += (startWith ? 1 : 0);
                                try 
                                {
                                   //actions.printHeaders( Model.Url(strDomainId) , strDomainId );
                                   System.out.println("Querying.." + Model.Url(strDomainId));
                                   t.sleep(Settings.settings("sleep"));
                                   actions.saveParseData( Model.Url(strDomainId) , strDomainId, TotalHost, counter );
                                } 
                                catch (InterruptedException ex) 
                                {
                                   System.out.println(ex.getMessage());
                                }
                                
                            }
                        }
                 }
              }
        }
      System.out.println( threadName + " exiting.");
   }
   @Override
   public void start () {
       
      System.out.println("Starting " +  threadName );
      
      if (t == null) 
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}