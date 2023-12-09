import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Copyright [Yahya Hassanzadeh-Nazarabadi]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class SSLServer extends Thread {
    private final String SERVER_KEYSTORE_FILE = "keystore.jks";
    private final String SERVER_KEYSTORE_PASSWORD = "storepass";
    private final String SERVER_KEY_PASSWORD = "keypass";
    private SSLServerSocket sslServerSocket;
    private SSLServerSocketFactory sslServerSocketFactory;
    private int choice;
    // private ServerControlPanel serverControlPanel;

    private ServerSocket serverSocket;

    public void run() {
        while (true) {
            if (choice == 1) {
                ListenAndAccept();
            } else if (choice == 2) {
                listenAndAccept();
            }

        }
    }

    public SSLServer(int port, int thread_choice) {
        choice = thread_choice;
        try {

            // serverControlPanel = new ServerControlPanel("hello server!");

            /*
             * Instance of SSL protocol with TLS variance
             */
            SSLContext sc = SSLContext.getInstance("TLS");

            /*
             * Key management of the server
             */
            char ksPass[] = SERVER_KEYSTORE_PASSWORD.toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(SERVER_KEYSTORE_FILE), ksPass);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, SERVER_KEY_PASSWORD.toCharArray());
            sc.init(kmf.getKeyManagers(), null, null);

            /*
             * SSL socket factory which creates SSLSockets
             */

            if (thread_choice == 1) {
                sslServerSocketFactory = sc.getServerSocketFactory();
                sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);

                System.out.println("SSL thread is up and running on port " + port);
            }

            else if (thread_choice == 2) {
                serverSocket = new ServerSocket(Server.DEFAULT_SERVER_PORT);
                System.out.println("TCP thread is up and running on port " + Server.DEFAULT_SERVER_PORT);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Listens to the line and starts a connection on receiving a request with the
     * client
     */
    private void ListenAndAccept() {
        SSLSocket s;
        try {
            s = (SSLSocket) sslServerSocket.accept();
            System.out.println(
                    "An SSL connection was established with a client on the address of " + s.getRemoteSocketAddress());
            SSLServerThread st = new SSLServerThread(s);
            st.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server Class. Connection establishment error inside listen and accept function");
        }
    }

    private void listenAndAccept() {
        Socket s;
        try {
            s = serverSocket.accept();
            System.out.println(
                    "A connection was established with a client on the address of " + s.getRemoteSocketAddress());
            ServerThread st = new ServerThread(s);
            st.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Server Class. Connection establishment error inside listen and accept function");
        }
    }

}
