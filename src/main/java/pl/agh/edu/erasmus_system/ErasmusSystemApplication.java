package pl.agh.edu.erasmus_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ErasmusSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErasmusSystemApplication.class, args);
    }

}
