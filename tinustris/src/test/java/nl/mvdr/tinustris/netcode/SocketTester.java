package nl.mvdr.tinustris.netcode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.input.InputState;

/**
 * Test class for playing around with reading and writing to sockets.
 * 
 * TODO delete this class when it is no longer needed
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class SocketTester {
    /** Port nuber. */
    private static final int PORT = 8080;

    /**
     * Main method.
     * 
     * @param args
     *            command-line parameters; these are ignored
     */
    public static void main(String[] args) {
        // server socket: reads an object and logs it
        new Thread(() -> {
            try (Socket socket = new ServerSocket(PORT).accept()) {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Object object = in.readObject();

                log.info("Read: " + object);
            } catch (IOException | ClassNotFoundException e) {
                log.error("Unexpected exception.", e);
            }
        }, "server").start();
        
        // client socket: connects to server and writes an object
        try (Socket socket = new Socket("localhost", PORT)) {
            InputState object = input -> false;
            log.info("Writing: " + object);
            
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(object);
        } catch (IOException e) {
            log.error("Unexpected exception.", e);
        }
    }
}
