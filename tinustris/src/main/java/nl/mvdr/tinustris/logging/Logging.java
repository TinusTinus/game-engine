package nl.mvdr.tinustris.logging;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Logging utility class.
 * 
 * @author Martijn van de Rijdt
 */
// private constructor to prevent instantiation
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class Logging {
    /** Logs some version info. */
    public static void logVersionInfo() {
        if (log.isInfoEnabled()) {
            log.info("Application version: " + retrieveVersion().orElse("unknown"));
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
     * Returns the version number from the jar manifest file.
     * 
     * @return version number if available
     */
    private static Optional<String> retrieveVersion() {
        return Optional.ofNullable(Logging.class.getPackage())
            .map(Package::getImplementationVersion);
    }
    
    /** Sets an uncaught exception handler, which logs all exceptions, on the current thread. */
    public static void setUncaughtExceptionHandler() {
        log.info("Installing uncaught exception handler.");
        Thread.currentThread().setUncaughtExceptionHandler(
                (thread, throwable) -> log.error("Uncaught runtime exception", throwable));
    }
    
    /** Instructs Hazelcast to log using slf4j. */
    public static void setUpHazelcastLogging() {
        log.info("Setting Hazelcast logging mode to slf4j.");
        System.setProperty("hazelcast.logging.type", "slf4j");
    }
    
    /** Installs a bridge for java.util.logging to slf4j. */
    public static void installSlf4jBridge() {
        log.info("Installing java.util.logging to slf4j bridge.");
        
        // remove existing handlers attached to java.util.logging root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        // add SLF4JBridgeHandler to java.util.logging's root logger
        SLF4JBridgeHandler.install();
    }
}
