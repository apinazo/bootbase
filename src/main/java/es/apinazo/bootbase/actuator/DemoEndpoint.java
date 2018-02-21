package es.apinazo.bootbase.actuator;

import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;


/**
 * A demo custom Actuator {@link Endpoint} with an id
 * that must be unique in the context.
 *
 * An endpoint is a @{@link org.springframework.context.annotation.Bean} annotated with one of this:
 * <ul>
 *     <li>@{@link Endpoint}</li>: enables endpoint in JMX and HTTP.
 *     <li>@{@link org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint}</li>: only in HTTP.
 *     <li>@{@link org.springframework.boot.actuate.endpoint.jmx.annotation.JmxEndpoint}</li>: only in JMX.
 * </ul>
 *
 * Why an {@link Endpoint} instead of a {@link org.springframework.stereotype.Controller}.
 * Because this way:
 * <ul>
 *     <li>Management code is separated from application business code.</li>
 *     <li>{@link Endpoint}s perform very specific tasks while
 *          {@link org.springframework.stereotype.Controller}s cover a
 *          wider range of funcionality</li>
 *     <li>{@link Endpoint}s can be managed with management.endpoint configurations.</li>
 * </ul>
 */
@Endpoint(id = "demo") // Declare this bean as an endpoint with the URL /<base-path>/demo.
@ConditionalOnProperty(value = "management.endpoint.demo.enabled") // Enable bean if set to true.
@Component
public class DemoEndpoint {

    private Map<String, String> demoMap;

    public DemoEndpoint() {
        this.demoMap = new HashMap<>();
        demoMap.put("key1", "value1");
        demoMap.put("key2", "value2");
        demoMap.put("key3", "value3");
    }

    /**
     * Returns all keys.
     *
     * Example request: curl -i -X GET http://localhost:8001/actuator/demo
     *
     * Mark a method with @{@link ReadOperation} and it will be
     * executed when a request with GET is made to the endpoint URL.
     *
     * Only one method with the same name can be annotated with this annotation
     * even if they have a different firm.
     *
     * @return The data to be returned as response from the endpoint. Can be any object we want. It will be transformed into JSON.
     */
    @ReadOperation
    public Map<String, String> doRead() {
        return this.demoMap;
    }

    /**
     * Returns vale from a given key.
     *
     * Example request: curl -i -X GET http://localhost:8001/actuator/demo/key1
     *
     * You can have several @{@link ReadOperation} as long as the have
     * different method names and firms.
     *
     * Parameters with @Selector must be named as arg0, arg1, etc...
     * Weird but custom names cannot be used and doing that will fail in
     * missing parameters error.
     *
     * @param arg0 First argument from the URL.
     * @return
     */
    @ReadOperation
    public String doReadKey(@Selector String arg0) {
        Assert.notNull(arg0, "Key must not be null");
        return this.demoMap.get( arg0 );
    }

    /**
     * Set a new value for a given key.
     *
     * Will be executed when a POST request is made.
     *
     * Example request: curl -i -X POST -H 'Content-Type: application/json' -d '{"newValue" : "value4"}' http://localhost:8001/actuator/demo/key1
     *
     * Anything without a @Selector annotation is expected to be found
     * in a JSON request body with the name of the method parameter
     * exactly matching a property in the JSON body.
     *
     * All arguments are mandatory by default so if they are not they must
     * be annotated with @Nullable.
     *
     * @return A result message.
     */
    @WriteOperation
    public String doWrite(@Selector String arg0, @Nullable String newValue) {
        Assert.notNull(arg0, "Key must not be null");
        this.demoMap.put(arg0, newValue);
        return "New value [" + newValue + "] added to key [" + arg0 + "]";
    }

    /**
     * Removes a key.
     *
     * Will be executed when a DELETE request is made.
     *
     * Example request: curl -i -X DELETE http://localhost:8001/actuator/demo/key1
     *
     * @return A result message.
     */
    @DeleteOperation
    public String doDelete(@Selector String arg0) {
        Assert.notNull(arg0, "Key to delete must not be null");
        this.demoMap.remove( arg0 );
        return "Key [" + arg0 + "] was deleted";
    }

}
