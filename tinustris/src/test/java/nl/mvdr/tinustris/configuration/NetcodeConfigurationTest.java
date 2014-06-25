package nl.mvdr.tinustris.configuration;

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
    public void testIsNetworkedGameSingleRemote() {
        NetcodeConfiguration configuration = 
            () -> Collections.singletonList(new RemoteConfiguration("localhost", 8080));
        
        Assert.assertTrue(configuration.isNetworkedGame());
    }
    
    /** Test method for {@link NetcodeConfigurationImpl#isNetworkedGame()}. */
    @Test
    public void testIsNetworkedGameMultipleRemotes() {
        NetcodeConfiguration configuration =
            () -> Arrays.asList(new RemoteConfiguration("localhost", 8080), new RemoteConfiguration("localhost", 8081));
        
        Assert.assertTrue(configuration.isNetworkedGame());
    }
}
