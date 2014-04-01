package nl.mvdr.tinustris.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Controller;
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
    /** Button prompt label. */
    @FXML
    private Label buttonPromptLabel;
    /** Description label. */
    @FXML
    private Label descriptionLabel;
    
    /** Controllers that the player has used so far. */
    private final Set<Controller> controllers;
    /** Input mapping that the user has input so far. */
    private final Map<Input, InputMapping> mapping;
    /** Capture controller. */
    private final JInputCaptureController captureController;
    /** Executor service for running the capture controller. */
    private final ExecutorService executorService;
    
    /** Constructor. */
    public InputConfigurationController() {
        super();
        
        this.controllers = new HashSet<>();
        this.mapping = new HashMap<>();
        
        this.captureController = new JInputCaptureController(this::inputCaptured);
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

        updateLabels();
        
        executorService.submit(captureController);
        
        log.info("Initialisation complete.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }
    }

    /** Updates the label text for the next input to be defined. */
    private void updateLabels() {
        Input input = nextInput().get();
        buttonPromptLabel.setText(String.format("Please press the button for %s.", input));
        descriptionLabel.setText(input.getDescription());
    }
    
    /** @return next input to be defined by the user */
    private Optional<Input> nextInput() {
        return Stream.of(Input.values())
            .filter(input -> !mapping.containsKey(input))
            .sorted()
            .findFirst();
    }
    
    private void inputCaptured() {
        log.info("Input captured.");
        
        // TODO handle
    }
    
    /** Handler for the cancel button. */
    @FXML
    private void cancel() {
        log.info("Cancel button activated.");
        
        // TODO actually cancel
    }
}
