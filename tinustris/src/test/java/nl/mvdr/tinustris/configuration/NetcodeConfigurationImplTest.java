package nl.mvdr.tinustris.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link NetcodeConfigurationImpl}.
 * 
 * @author Martijn van de Rijdt
 */
public class NetcodeConfigurationImplTest {
    /** Test method for {@link NetcodeConfigurationImpl#isNetworkedGame()}. */
    @Test
    public void testIsNotNetworkedGame() {
        NetcodeConfigurationImpl configuration = new NetcodeConfigurationImpl(
                Collections.emptyList());
        
        Assert.assertFalse(configuration.isNetworkedGame());
    }
    
    /** Test method for {@link NetcodeConfigurationImpl#isNetworkedGame()}. */
    @Test
    public void testIsNetworkedGameSingleRemote() {
        NetcodeConfigurationImpl configuration = new NetcodeConfigurationImpl(
                Collections.singletonList(new RemoteConfiguration("localhost", 8080)));
        
        Assert.assertTrue(configuration.isNetworkedGame());
    }
    
    /** Test method for {@link NetcodeConfigurationImpl#isNetworkedGame()}. */
    @Test
    public void testIsNetworkedGameMultipleRemotes() {
        NetcodeConfigurationImpl configuration = new NetcodeConfigurationImpl(Arrays.asList(
            new RemoteConfiguration("localhost", 8080), new RemoteConfiguration("localhost", 8081)));
        
        Assert.assertTrue(configuration.isNetworkedGame());
    }
}
