package es.apinazo.bootbase.rpc;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import org.springframework.stereotype.Service;

/**
 * Sample RPC service.
 *
 * jsonrpc4j needs both an interface and an implementation of the service.
 */
@Service
public class SampleRPRCServiceImpl implements SampleRPCService {

    public String sayHelloWorld(String name) {
        return "Hello world, " + name;
    }
}
