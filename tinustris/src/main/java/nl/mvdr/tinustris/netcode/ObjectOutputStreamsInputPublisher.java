package nl.mvdr.tinustris.netcode;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.FrameAndInputStatesContainer;

/**
 * Implementation of {@link InputPublisher}, which serialises states through object output streams.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@ToString
@Slf4j
public class ObjectOutputStreamsInputPublisher implements InputPublisher {
    /** Outpustreams to which any input states should be written. */
    private final List<ObjectOutputStream> outputStreams;

    /** {@inheritDoc} */
    @Override
    public void publish(FrameAndInputStatesContainer states) {
        for (ObjectOutputStream stream : outputStreams) {
            try {
                stream.writeObject(states);
            } catch (IOException e) {
                // TODO actual error handling
                log.error("Unable to write object " + states, e);
            }
        }
    }

}
