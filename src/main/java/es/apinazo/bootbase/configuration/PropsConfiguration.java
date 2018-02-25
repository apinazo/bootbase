package es.apinazo.bootbase.configuration;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Example of auto binding properties from configuration files into a class.
 *
 * @{@link Configuration} tells Spring this a configuration class,
 * so it be loaded before the other beans.
 *
 * @{@link ConfigurationProperties} makes Spring look for values in the properties files
 * and auto inject them in this class fields, following a pattern matching by name.
 * The string "props" - the prefix - says where to look for properties hierarchically in the
 * configuration files.
 *
 * For inject to work all fields must have a setter method.
 *
 * See application.yml to find the properties used in this example.
 */
@Data
@Slf4j
@ToString
@Configuration
@ConfigurationProperties("props")
public class PropsConfiguration {

    String name;

    boolean enabled;

    String[] listOfThings;

    String[] anotherListOfThings;

    InnerConf firstInnerConf;

    InnerConf secondInnerConf;

    InnerConf[] innerConfArray;

    /**
     * You can use inner classes to map complex properties or to create a hierarchy.
     */
    @Data
    public static class InnerConf {

        private String key;

        private String value;
    }

    @PostConstruct
    public void init(){
        log.info("PropsConfiguration initialized with values: {}", this);
    }

}
