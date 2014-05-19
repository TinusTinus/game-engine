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
    /** Number of objects to write and read. */
    private static final int NUM_OBJECTS = 100;

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
                for (int i = 0; i != NUM_OBJECTS; i++) {
                    log.info("Read: " + in.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
                log.error("Unexpected exception.", e);
            }
        }, "server").start();

        // client socket: connects to server and writes an object
        try (Socket socket = new Socket("localhost", PORT)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            for (int i = 0; i != NUM_OBJECTS; i++) {
                InputState object = input -> false;
                log.info("Writing: " + object);
                out.writeObject(object);
            }
        } catch (IOException e) {
            log.error("Unexpected exception.", e);
        }
    }
}
