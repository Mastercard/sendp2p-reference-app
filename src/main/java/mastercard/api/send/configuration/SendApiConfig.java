package mastercard.api.send.configuration;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.List;

import org.openapitools.client.ApiClient;
import org.openapitools.client.api.PaymentTransferApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.mastercard.developer.interceptors.OkHttp2OAuth1Interceptor;
import com.mastercard.developer.utils.AuthenticationUtils;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Configuration class for the application
 */
@Configuration
public class SendApiConfig {

    /**
     * Creates an instance of ApiClient, with an interceptor to force JSON responses, and an interceptor to
     * sign requests to align with Mastercard's OAuth 1.0a requirements.
     * @param env Environment from which we pull property details
     * @return Instance of ApiClient
     * @throws Exception
     */
    @Bean
    public ApiClient apiClient(Environment env) throws Exception {
        String consumerKey = env.getProperty("consumerKey");
        String signingKeyAlias = env.getProperty("keyAlias");
        String signingKeyFilePath = env.getProperty("p12PrivateKey");
        String signingKeyPassword = env.getProperty("keyPassword");
        PrivateKey signingKey = AuthenticationUtils.loadSigningKey(signingKeyFilePath, signingKeyAlias, signingKeyPassword);

        ApiClient client = new ApiClient();
        client.setBasePath(env.getProperty("apiBasePath"));
        client.setDebugging(true);


        List<Interceptor> interceptors = client.getHttpClient().networkInterceptors();
        interceptors.add(new ForceJsonResponseInterceptor());
        interceptors.add(new OkHttp2OAuth1Interceptor(consumerKey, signingKey));

        return client;
    }


 
    /**
     * Creates an instance of PaymentTransferApi using the above defined apiClient
     * @param env Environment from which we pull property details, to pass along to apiClient()
     * @return Instance of PaymentsApi
     * @throws Exception
     */
    @Bean
    public PaymentTransferApi paymentTransferApi(Environment env) throws Exception {
        return new PaymentTransferApi(apiClient(env));
    }

    
    /**
     * Interceptor class that we use to force our responses to be JSON, by adding header to Request as it goes out.
     */
    private static class ForceJsonResponseInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            System.out.println("===========" + originalRequest + "\n");
            String withJsonFormatUrl = withJsonFormat(originalRequest.uri().toString());
            Request newRequest = originalRequest.newBuilder().url(withJsonFormatUrl).build();
            return chain.proceed(newRequest);
        }

        private String withJsonFormat(String uri) {
            StringBuilder newUri = new StringBuilder(uri);
            newUri.append(uri.contains("?") ? "&" : "?");
            newUri.append("Format=JSON");
            return newUri.toString();
        }
    }
}

