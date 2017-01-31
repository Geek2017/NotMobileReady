
package pageinsight;

public class UrlSkipped {
    
        public int counter;
        public int id;
        public String urlname;
        public int totalhost = 0;
        public int responsecode =  0;
        
        public UrlSkipped(int counter,  int id ,String urlname, int responsecode){
            this.counter = counter;
            this.id = id;
            this.urlname = urlname;  
            this.responsecode = responsecode;
        }  
        @Override
        public String toString(){  
            
            Model.SaveSkip(this.urlname,this.id);
            
            return "Url" + this.urlname + 
                    "\nResponse : " + this.responsecode + 
                    "\nResult : Speed score(skipped)\nMobile freindliness Grade: (skipped)\nStatus :(skipped)"  ;
        }
        
}
