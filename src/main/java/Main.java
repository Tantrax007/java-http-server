import utils.Reader;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    // No need to use InetAddress for the server
    try (final ServerSocket ssocket = new ServerSocket(42069, 1)) {
      System.out.println("Server up and running on port: 42069");
      System.out.println("Awaiting connection...");

      /**
       * Blocking call that will wait for a client to initiate
       * communications and then return with a normal
       * Socket that is then used for communication with the client.
       */
      final Socket connection = ssocket.accept();

      // Once we have a connection with a client
      System.out.println("Connection established in local address: " + connection.getLocalSocketAddress());
      System.out.println("Client address: " + connection.getRemoteSocketAddress());

      // Prepare the input stream as we are not going to send anything out
      final InputStream is = connection.getInputStream();
      System.out.println("Tamanyo del stream: " + is.available());

      while(true) {
        // TODO: Improvement, we might want  to add a fix buffer length
        if (is.available() > 0){
          final String decodedResponse = Reader.readFileWithEightBytesBuffer(is);
          System.out.println(decodedResponse);

          //! Important, because `.read()` is a destructive operation.
          // If we place this outside the conditional we will end up with
          // a race condition where `if (is.available() > 0)` is not met and
          // at the end `is.read()` blocks the thread until the request arrives
          // to the server, takes the first byte out and loops again which causes
          // that first byte to be lost.
          if (is.read() == -1) break;
        }
      }
    } catch (IOException io) {
      System.err.println("Something went wrong when trying to listen on port 42069");
    }
  }
}
