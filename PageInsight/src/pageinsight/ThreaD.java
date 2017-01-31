package pageinsight;

class ThreaD extends Thread {
    
   private Thread t;
   private final String threadName;
   private final String query = "SELECT * FROM urllist";
   private int ids = 0;
   private String names = "";
   
   ThreaD( String name,  int id, String names) {
      threadName = name;
      this.ids = id;
      this.names = names;
   }
   @Override
   public void run()
   {  
       // System.out.println("Querying.." + names );
        Actions actions = new Actions();
        synchronized(actions) {
              actions.saveContents( names , ids, 1,1);
        }  
      //System.out.println( threadName + " exiting.");
   }
   @Override
   public void start () {     
      //System.out.println("Starting " +  threadName );
      if (t == null) 
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}