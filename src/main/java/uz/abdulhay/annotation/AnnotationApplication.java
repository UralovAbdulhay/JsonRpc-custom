package uz.abdulhay.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uz.abdulhay.annotation.annotation.CustomAnnotation;

@SpringBootApplication
public class AnnotationApplication {

    public static void main(String[] args) {

//        CustomAnnotationScanner.scanMethods(MyService.class, CustomAnnotation.class);

        SpringApplication.run(AnnotationApplication.class, args);
    }

}
