package jp.ac.chitose.ir.config;

import jp.ac.chitose.ir.service.HelloService;
import jp.ac.chitose.ir.service.class_select.ClassSelect;
import jp.ac.chitose.ir.service.sample.SampleService;
import jp.ac.chitose.ir.service.student.StudentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfiguration {

    @Value("${fastapi.url}")
    private String baseUrl;

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory(WebClient.Builder builder) {
        final WebClient webClient = builder.baseUrl(baseUrl).build();
        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
    }

    @Bean
    public HelloService helloService(HttpServiceProxyFactory factory) {
        return factory.createClient(HelloService.class);
    }

    @Bean
    public SampleService sampleService(HttpServiceProxyFactory factory) {
        return factory.createClient(SampleService.class);
    }

    @Bean
    public ClassSelect classSelect(HttpServiceProxyFactory factory) {
        return factory.createClient(ClassSelect.class);
    }

    @Bean
    public StudentService studentService(HttpServiceProxyFactory factory) {
        return factory.createClient(StudentService.class);
    }
}
