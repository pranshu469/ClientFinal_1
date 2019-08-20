
package websocket.client;


import java.net.URI;
import javax.websocket.*;
import org.json.*;
import java.util.Queue;
import org.apache.commons.collections4.queue.CircularFifoQueue;

@ClientEndpoint
public class WSClient 
{
    @SuppressWarnings("FieldMayBeFinal")
    private static Object waitLock = new Object();
    
    static int count=0;
    static double x;
    static double y;
    
   
   
    Queue<Double> fifoX = new CircularFifoQueue<>(5);
    Queue<Double> fifoY = new CircularFifoQueue<>(5);
    static{System.out.println("Selling price , Buying price");}
   
    @OnMessage
    public void onMessage(String message)
    {
       
          
           
              
      JSONObject jsonobj=new JSONObject(message);
      x=jsonobj.getDouble("SellingPrice");
      y=jsonobj.getDouble("BuyingPrice");
      //System.out.println(message);
      //System.out.println("BP: "+x+"  ,SP: "+y+"         count: "+count);
      System.out.println();
      
        
     fifoX.add(x);
     fifoY.add(y);
    System.out.println(fifoX+" "+fifoY);
     count++;
    
    
    }
 
    
    
 @SuppressWarnings("SynchronizeOnNonFinalField")
 private static void  wait4TerminateSignal()
 {
  synchronized(waitLock)
  {
    try{
        waitLock.wait();
    }catch (InterruptedException e) { }
    }}
    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public static void main(String[] args) {
    @SuppressWarnings("UnusedAssignment")
    WebSocketContainer container=null;//
    Session session=null;
    try{
    container = ContainerProvider.getWebSocketContainer(); 
    session=container.connectToServer(WSClient.class, URI.create("ws://localhost:8084/ServerFinalExperiment12_07_19/server")); 
    wait4TerminateSignal();
    } catch (Exception e) {e.printStackTrace();}
    finally{
    if(session!=null){
    try {
    session.close();
    } catch (Exception e) {     
     e.printStackTrace();
    }}
             
    
  
  
 }

   
}

}




