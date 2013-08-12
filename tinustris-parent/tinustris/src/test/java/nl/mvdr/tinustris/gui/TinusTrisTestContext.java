package nl.mvdr.tinustris.gui;

/**
 * Main class, whose main method simply calls {@link TinusTris#main(String[])}.
 * 
 * Workaround for the fact that the test classpath (which contains useful things such as a log4j configuration) does not
 * seem to be available when running {@link TinusTris#main(String[])} directly in Eclipse Kepler.
 * 
 * @author Martijn van de Rijdt
 */
public class TinusTrisTestContext {

    /**
     * Calls {@link TinusTris#main(String[])}.
     * 
     * @param args
     *            command-line parameters
     */
    public static void main(String[] args) {
        Tinustris.main(args);
    }

}
