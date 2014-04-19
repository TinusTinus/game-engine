package nl.mvdr.tinustris.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.stream.Stream;

import nl.mvdr.tinustris.input.IndexedInputState;
import nl.mvdr.tinustris.input.Input;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link IndexedInputState}.
 * 
 * @author Martijn van de Rijdt
 */
public class IndexedInputStateTest {
    /** Serialises an indexed input state. */
    @Test
    public void serialize() throws IOException {
        IndexedInputState state = new IndexedInputState(3, input -> false);
        
        try (ObjectOutput output = new ObjectOutputStream(new BufferedOutputStream(new ByteArrayOutputStream()))) {
            output.writeObject(state);
        }
    }
    
    /**
     * Serialises and deserialises an indexed input state.
     * 
     * @throws IOException unexpected
     * @throws ClassNotFoundException unexpected
     */
    @Test
    public void serializeAndDeserializeNothingPressed() throws IOException, ClassNotFoundException {
        IndexedInputState state = new IndexedInputState(3, input -> false);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        // serialise to outputStream
        try (ObjectOutput output = new ObjectOutputStream(new BufferedOutputStream(outputStream))) {
            output.writeObject(state);
        }
        // deserialise from outputStream
        Object result;
        try (ObjectInput input = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(outputStream.toByteArray())))) {
            result = input.readObject();
        }
        
        Assert.assertTrue(result instanceof IndexedInputState);
        IndexedInputState resultState = (IndexedInputState) result;
        Assert.assertEquals(3, resultState.getFrame());
        Stream.of(Input.values()).forEach(input -> Assert.assertFalse(resultState.isPressed(input)));
    }
    
    /**
     * Serialises and deserialises an indexed input state.
     * 
     * @throws IOException unexpected
     * @throws ClassNotFoundException unexpected
     */
    @Test
    public void serializeAndDeserializeEverythingPressed() throws IOException, ClassNotFoundException {
        IndexedInputState state = new IndexedInputState(3, input -> true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        // serialise to outputStream
        try (ObjectOutput output = new ObjectOutputStream(new BufferedOutputStream(outputStream))) {
            output.writeObject(state);
        }
        // deserialise from outputStream
        Object result;
        try (ObjectInput input = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(outputStream.toByteArray())))) {
            result = input.readObject();
        }
        
        Assert.assertTrue(result instanceof IndexedInputState);
        IndexedInputState resultState = (IndexedInputState) result;
        Assert.assertEquals(3, resultState.getFrame());
        Stream.of(Input.values()).forEach(input -> Assert.assertTrue(resultState.isPressed(input)));
    }
}
