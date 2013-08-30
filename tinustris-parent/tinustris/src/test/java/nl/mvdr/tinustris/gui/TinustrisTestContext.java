package nl.mvdr.tinustris.gui;

/**
 * Main class, whose main method simply calls {@link Tinustris#main(String[])}.
 * 
 * Workaround for the fact that the test classpath (which contains useful things such as a log4j configuration) does not
 * seem to be available when running {@link Tinustris#main(String[])} directly in Eclipse Kepler.
 * 
 * This class relies on JInput's native libraries. These are not available by default. If you want to run this
 * class, make sure that the java.library.path system property contains target/natives in this project directory.
 * 
 * In Eclipse, you can do this by opening the Run Configuration, opening the arguments tab and pasting the following
 * into the "VM Arguments" text area:
 * 
 * <pre>
 * -Djava.library.path=${project_loc}/target/natives
 * </pre>
 * 
 * @author Martijn van de Rijdt
 */
public class TinustrisTestContext {
    /**
     * Calls {@link Tinustris#main(String[])}.
     * 
     * @param args
     *            command line parameters
     */
    public static void main(String[] args) {
        Tinustris.main(args);
    }
}
