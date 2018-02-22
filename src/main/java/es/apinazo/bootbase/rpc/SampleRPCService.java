package es.apinazo.bootbase.rpc;

import com.googlecode.jsonrpc4j.JsonRpcParam;

/**
 * Sample RPC service interface.
 *
 * jsonrpc4j needs both an interface and an implementation of the service.
 */
public interface SampleRPCService {

    String sayHelloWorld(@JsonRpcParam(value="name") String name);
}
