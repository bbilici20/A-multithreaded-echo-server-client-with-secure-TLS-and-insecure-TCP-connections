import java.util.Scanner;
import java.time.Duration;
import java.time.Instant;

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
public class MainSSLClient {
    public final static String TLS_SERVER_ADDRESS = "localhost";
    public final static String MESSAGE_TO_SERVER = "75794COMP416";
    public final static int TLS_SERVER_PORT = 4444;

    public static void main(String[] args) {

        Scanner stringScanner = new Scanner(System.in);
        System.out.println("Connect to SSL (1), connect to TCP (2):");
        String choice = stringScanner.next();

        if (choice.equals("1")) {
            /*
             * Creates an SSLConnectToServer object on the specified server address and port
             */
            Instant start = Instant.now();
            SSLConnectToServer sslConnectToServer = new SSLConnectToServer(TLS_SERVER_ADDRESS, TLS_SERVER_PORT);
            /*
             * Connects to the server
             */
            sslConnectToServer.Connect();
            // Scanner scanner = new Scanner(System.in);
            // System.out.println("Enter a message for the echo");
            // String message = scanner.nextLine();
            // while (!message.equals("QUIT")) {
            // System.out.println("Response from server: " +
            // sslConnectToServer.SendForAnswer(message));
            // System.out.println("Enter a message for the echo");
            // message = scanner.nextLine();
            // }

            for (int i = 0; i < MESSAGE_TO_SERVER.length(); i++) {

                char c = MESSAGE_TO_SERVER.charAt(i);
                String mystring = "" + c;
                System.out.println("Response from server: " + sslConnectToServer.SendForAnswer(mystring));

            }

            sslConnectToServer.Disconnect();
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.println("Time taken for SSL connection: " + timeElapsed.toMillis() + " milliseconds");

        }

        else if (choice.equals("2")) {

            Instant start = Instant.now();
            ConnectionToServer connectionToServer = new ConnectionToServer(ConnectionToServer.DEFAULT_SERVER_ADDRESS,
                    ConnectionToServer.DEFAULT_SERVER_PORT);
            connectionToServer.Connect();
            // Scanner scanner = new Scanner(System.in);
            // System.out.println("Enter a message for the echo");
            // String message = scanner.nextLine();
            // while (!message.equals("QUIT")) {
            // System.out.println("Response from server: " +
            // connectionToServer.SendForAnswer(message));
            // System.out.println("Enter a message for the echo");
            // message = scanner.nextLine();
            // }

            for (int i = 0; i < MESSAGE_TO_SERVER.length(); i++) {

                char c = MESSAGE_TO_SERVER.charAt(i);
                String mystring = "" + c;
                System.out.println("Response from server: " + connectionToServer.SendForAnswer(mystring));

            }

            connectionToServer.Disconnect();
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.println("Time taken for TCP connection: " + timeElapsed.toMillis() + " milliseconds");

        } else {
            System.out.println("Invalid choice");
        }
    }
}
