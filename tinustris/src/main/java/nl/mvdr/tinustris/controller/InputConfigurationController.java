package nl.mvdr.tinustris.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Controller;
import nl.mvdr.tinustris.input.ControllerAndInputMapping;
import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.input.InputMapping;
import nl.mvdr.tinustris.input.JInputCaptureController;

/**
 * Controller for ithe input configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class InputConfigurationController {
    /** Controllers that the player has used so far. */
    private final Set<Controller> controllers;
    /** Input mapping that the user has input so far. */
    private final Map<Input, InputMapping> mapping;
    
    /** Executor service for running the capture controller. */
    private final ExecutorService executorService;
    
    /** Button prompt label. */
    @FXML
    private Label buttonPromptLabel;
    /** Description label. */
    @FXML
    private Label descriptionLabel;
    
    /** Future for the last task that has been submitted to the executorService. */
    private Future<Optional<ControllerAndInputMapping>> futureMapping;
    
    /** Constructor. */
    public InputConfigurationController() {
        super();
        
        this.controllers = new HashSet<>();
        this.mapping = new HashMap<>();
        
        this.executorService = Executors.newSingleThreadExecutor(runnable -> new Thread(runnable, "Input Configuration"));
    }

    /** Initialisation method. */
    // default visibility for unit test
    @FXML
    void initialize() {
        log.info("Initialising controller for input configuration.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }

        startListeningForNextInput();
        
        log.info("Initialisation complete.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }
    }

    /** Starts listening for the next input value. */
    private void startListeningForNextInput() {
        Input input = nextInput();
        
        log.info("Start listening for input " + input);
        
        // update the label text
        buttonPromptLabel.setText(String.format("Please press the button for %s.", input));
        descriptionLabel.setText(input.getDescription());
        
        // start the capture controller
        futureMapping = executorService.submit(new JInputCaptureController(this::inputCaptured));
    }

    /** @return next input to be defined by the user */
    private Input nextInput() {
        return Stream.of(Input.values())
            .filter(input -> !mapping.containsKey(input))
            .sorted()
            .findFirst()
            .get();
    }
    
    /** Callback, to be run on the input capture thread, called when an ibput has succesfully been captured. */
    private void inputCaptured() {
        log.info("Input capture complete.");
        
        Platform.runLater(this::processCapturedInput);
    }
    
    /** Processes the captured input. */
    private void processCapturedInput() {
        try {
            Optional<ControllerAndInputMapping> captured = futureMapping.get();
            captured.ifPresent(controllerAndInputMapping -> {
                controllers.add(controllerAndInputMapping.getController());
                mapping.put(nextInput(), controllerAndInputMapping.getMapping());
                log.info("Current mapping: " + mapping);
                
                if (Stream.of(Input.values())
                        .allMatch(mapping::containsKey)) {
                    // all inputs have been mapped!
                    executorService.shutdownNow();
                    // TODO all buttons have been mapped, complete succesfully and return to the previous controller
                } else {
                    startListeningForNextInput();
                }
            });
        } catch (InterruptedException | ExecutionException e) {
            log.error("Unexpected exception!", e);
            cancel();
        }
    }
    
    /** Handler for the cancel button. */
    @FXML
    private void cancel() {
        log.info("Shutting down thread pool."
                + " This may result in an InterruptedException in the input configuration thread.");
        
        executorService.shutdownNow();
        
        // TODO return to the previous controller
    }
}