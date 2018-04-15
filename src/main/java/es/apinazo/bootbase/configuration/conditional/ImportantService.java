package es.apinazo.bootbase.configuration.conditional;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration("important-service")
@ConditionalOnProperty("important-service")
public class ImportantService {

    @Value("${url}")
    private String url;

    @Value("${user}")
    private String user;

    @Value("$password")
    private String password;

}
