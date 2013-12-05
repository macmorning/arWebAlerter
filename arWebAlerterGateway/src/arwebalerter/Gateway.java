/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arwebalerter;
import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.Constants;
//import com.bmc.arsys.api.UserInfo;
import com.bmc.arsys.api.AlertReceiver;
import com.bmc.arsys.api.AlertMessageInfo;
import com.bmc.arsys.api.AlertCallbackHandler;
import java.io.IOException;

/**
 *
 * @author syyvon
 */
public class Gateway {
    private static class MyCallBack implements AlertCallbackHandler {
        public void onAlertRecieved(AlertMessageInfo alert) {
            System.out.println(alert.getAlertText());
        }
    }

    private static ARServerUser ctx;
    private static AlertReceiver alerter;
    private static int portnum;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Setup my authentication credentials
        String serverName = "ittux8";
        String userName = "ADMIN";
        String userPassword = "tagada";
        int serverPort = 3500;
        portnum = 6600;
        
        // Instantiate my ARServerUser Object
        ctx = new ARServerUser();
        ctx.setServer(serverName);
        ctx.setUser(userName);
        ctx.setPassword(userPassword);
        ctx.setPort(serverPort);
        
        // Verify that the authentication information provided is valid
        connectionTest();

        connectAlertProxy();
        // Output some key information about the connected AR Server
        //showARSystemDetails();
 
        // Log out from AR Server when completed
        //ctx.logout();
    }
 
    private static void connectionTest() {
        try {
            ctx.verifyUser();
        } catch (ARException e) {
            System.out.println(e.getMessage());
        }
    }
 
    
    private static void connectAlertProxy() {
        try {
            alerter = new AlertReceiver(new MyCallBack());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("Receiving");
            alerter.beginReceive(ctx);
        } catch (ARException e) {
            System.out.println(e.getMessage());
        }
       
    }
//    private static void showARSystemDetails() {
//        System.out.println("Connected to AR Server: " + ctx.getServerInfoStr());
//        System.out.println("AR Server version: " + ctx.getServerVersion() + "\n");
//        System.out.println("List all connected users and last accessed time");
//        try {
//            for (UserInfo user : ctx.getListUser(Constants.AR_USER_LIST_CURRENT)) {
//                System.out.println("   " + user.getUserName() + " - "
//                    + user.getLastAccessTime().toDate());
//            }
//        } catch (ARException e) {
//            System.out.println(e.getMessage());
//        }
//    }
    
    
}
