package es.apinazo.bootbase.rpc;

import com.googlecode.jsonrpc4j.spring.JsonServiceExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of all RPC services.
 *
 * JSON-RPC could be more useful than REST in inter-service communications
 * since it will reduce the traffic overload and gives a simple way to expose
 * services, avoiding all the REST verbosity.
 */
@Configuration
public class RPCConfiguration {

    /**
     * A JSON-RPC endpoint available at an URL equals to the bean name.
     *
     * The endpoint will expose all the service public methods which could be used
     * setting the "method" parameter in the JSON.
     *
     * Example request:
     * curl -v -H "Content-Type: application/json" -d "{\"id\":0, \"method\":\"sayHelloWorld\", \"params\":[\"John Doe\"]}" http://localhost:8001/rpc/sample
     *
     * Request using named params:
     * curl -i -H "Content-Type: application/json" -d "{\"id\":0, \"method\":\"sayHelloWorld\", \"params\":{\"name\":\"John Doe\"} }" http://localhost:8001/rpc/sample
     *
     * @param sampleRPCService The service bean, autowired.
     * @return The JSON-RPC bean.
     */
    @Bean(name = "/rpc/sample")
    public JsonServiceExporter jsonServiceExporter(SampleRPCService sampleRPCService) {
        JsonServiceExporter exporter = new JsonServiceExporter();
        exporter.setService(sampleRPCService);
        exporter.setServiceInterface(SampleRPCService.class);
        return exporter;
    }

}
