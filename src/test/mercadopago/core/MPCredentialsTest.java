package test.mercadopago.core;

import com.mercadopago.SDK;
import com.mercadopago.core.MPCredentials;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Mercado Pago SDK
 * MPCredentials Tests class
 *
 * Created by Eduardo Paoletta on 12/13/16.
 */
public class MPCredentialsTest {

    @Test
    public void credentialsTest() throws MPException {
        SDK.cleanConfiguration();

        Exception exception = null;
        try {
            MPCredentials.getAccessToken();

        } catch (Exception ex) {
            assertEquals("\"client_id\" and \"client_secret\" can not be \"null\" when getting the \"access_token\"", ex.getMessage());
            exception = ex;
        }
        assertSame(MPException.class, exception.getClass());

        if (StringUtils.isEmpty("test/mercadopago/data/credentials.properties")) {
            throw new IllegalArgumentException("File path can not be empty");
        }

        Properties properties = null;
        InputStream inputStream = null;
        try {
            properties = new Properties();
            inputStream = SDK.class.getClassLoader().getResourceAsStream("test/mercadopago/data/credentials.properties");
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found");
            }
            properties.load(inputStream);

        } catch (IllegalArgumentException iaException) {
            throw iaException;
        } catch (Exception ex) {
            throw new MPConfException(ex);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (Exception ex) {
                // Do nothing
            }
        }
        SDK.setClientSecret(properties.getProperty("clientSecret"));
        SDK.setClientId(properties.getProperty("clientId"));

        String accessToken = SDK.getAccessToken();
        assertNotNull(accessToken);
    }

}
