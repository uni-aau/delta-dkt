package network2;

import ClientUIHandling.ClientLogic;


/**
 * This class can be introduced as wrapper class for a network connection specifically for client connections (server side)
 * It may store some other client specific attributes like:
 *  - a game participant (a player object that has its own properties, (money-) account etc.
 *  - a nickname ?
 *  - the network connection object
 *  - etc..
 */
public class NetworkClientConnection {

    private NetworkConnection connection;

    public NetworkClientConnection(String ip, int port, int timeout, ClientLogic logic) {
        this.connection = new NetworkConnection(ip,  port, timeout,logic);
    }

    public void start(){
        this.connection.start();
    }

    public void sendMessage(String msg) {
         connection.send(msg);
    }

    public void stopConnection() throws Exception {
        connection.close();
    }

    public NetworkConnection getConnection() {
        return connection;
    }
}
