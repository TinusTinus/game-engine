package nl.mvdr.tinustris.configuration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link NetcodeConfiguration}.
 * 
 * @author Martijn van de Rijdt
 */
public class NetcodeConfigurationTest {
    /** Test method for {@link NetcodeConfiguration#isNetworkedGame()}. */
    @Test
    public void testIsNotNetworkedGame() {
        NetcodeConfiguration configuration = Collections::emptyList;

        Assert.assertFalse(configuration.isNetworkedGame());
    }

    /** Test method for {@link NetcodeConfiguration#isNetworkedGame()}. */
    @Test
    public void testIsNetworkedGameSingleRemote() throws IOException {
        NetcodeConfiguration configuration = () -> Collections.singletonList(createRemoteConfiguration());

        Assert.assertTrue(configuration.isNetworkedGame());
    }

    /** Test method for {@link NetcodeConfigurationImpl#isNetworkedGame()}. */
    @Test
    public void testIsNetworkedGameMultipleRemotes() throws IOException {
        NetcodeConfiguration configuration = () -> Arrays.asList(createRemoteConfiguration(),
                createRemoteConfiguration());

        Assert.assertTrue(configuration.isNetworkedGame());
    }

    /** Creates a dummy remote configuration. */
    private RemoteConfiguration createRemoteConfiguration() {
        ObjectOutputStream outputStream;
        ObjectInputStream inputStream;
        try {
            outputStream = new ObjectOutputStream(new ByteArrayOutputStream());
            inputStream = new ObjectInputStream() { };
        } catch (IOException e) {
            throw new RuntimeException("Unexpected I/O exception", e);
        }

        return new RemoteConfiguration(outputStream, inputStream);
    }
}
