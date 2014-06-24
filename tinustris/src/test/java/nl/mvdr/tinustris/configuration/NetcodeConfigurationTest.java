package nl.mvdr.tinustris.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        NetcodeConfiguration configuration = new NetcodeConfiguration() {
            @Override
            public List<RemoteConfiguration> getRemotes() {
                return Collections.emptyList();
            }
        };
        
        Assert.assertFalse(configuration.isNetworkedGame());
    }
    
    /** Test method for {@link NetcodeConfiguration#isNetworkedGame()}. */
    @Test
    public void testIsNetworkedGameSingleRemote() {
        NetcodeConfiguration configuration = new NetcodeConfiguration() {
            @Override
            public List<RemoteConfiguration> getRemotes() {
                return Collections.singletonList(new RemoteConfiguration("localhost", 8080));
            }
        };
        
        Assert.assertTrue(configuration.isNetworkedGame());
    }
    
    /** Test method for {@link NetcodeConfiguration#isNetworkedGame()}. */
    @Test
    public void testIsNetworkedGameMultipleRemotes() {
        NetcodeConfiguration configuration = new NetcodeConfiguration() {
            @Override
            public List<RemoteConfiguration> getRemotes() {
                return Arrays.asList(
                    new RemoteConfiguration("localhost", 8080), new RemoteConfiguration("localhost", 8081));
            }
        };
        
        Assert.assertTrue(configuration.isNetworkedGame());
    }
}
