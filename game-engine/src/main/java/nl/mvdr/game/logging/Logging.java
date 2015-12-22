package nl.mvdr.game.logging;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * Logging helper class.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public abstract class Logging {
    /**
     * Logs some version info.
     * 
     * The game's version number is logged based on the version information in the jar's manifest file. If the manifest
     * is missing the value "unknown" will be logged.
     * 
     * For an example of how to get Maven to generate a correct manifest, see the maven-jar-plugin configuration in the
     * game-engine project's pom.
     */
    public void logVersionInfo() {
        if (log.isInfoEnabled()) {
            log.info("Game version: " + retrieveGameVersion());
            log.info("Game engine version: " + retrieveGameEngineVersion());
            log.info("Classpath: " + System.getProperty("java.class.path"));
            log.info("Library path: " + System.getProperty("java.library.path"));
            log.info("Java vendor: " + System.getProperty("java.vendor"));
            log.info("Java version: " + System.getProperty("java.version"));
            log.info("OS name: " + System.getProperty("os.name"));
            log.info("OS version: " + System.getProperty("os.version"));
            log.info("OS architecture: " + System.getProperty("os.arch"));
        }
    }
    
    /**
     * Returns the version number from the game engine jar manifest file.
     * 
     * @return version number if available
     */
    private String retrieveGameEngineVersion() {
        return retrieveVersion(Logging.class);
    }
    
    /**
     * Returns the version number from the concrete class's jar's manifest file.
     * 
     * @return version number if available
     */
    private String retrieveGameVersion() {
        return retrieveVersion(getClass());
    }

    /**
     * Returns the version number from the jar manifest file of the given class.
     * 
     * @param clazz class
     * @return version number if available
     */
    private String retrieveVersion(Class<?> clazz) {
        return Optional.ofNullable(clazz.getPackage())
            .map(Package::getImplementationVersion)
            .orElse("unknown");
    }
    
    /** Sets an uncaught exception handler, which logs all exceptions, on the current thread. */
    public void setUncaughtExceptionHandler() {
        log.info("Installing uncaught exception handler.");
        Thread.currentThread().setUncaughtExceptionHandler(
                (thread, throwable) -> log.error("Uncaught runtime exception", throwable));
    }
}
