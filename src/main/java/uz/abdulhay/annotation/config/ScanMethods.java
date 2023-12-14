package uz.abdulhay.annotation.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import uz.abdulhay.annotation.annotation.JsonRpcMethod;
import uz.abdulhay.annotation.annotation.JsonRpcService;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration

public class ScanMethods implements ApplicationContextAware {

    private ApplicationContext context;
    private final JsonRpcMethodContext jsonRpcMethodContext;

    public ScanMethods(JsonRpcMethodContext jsonRpcMethodContext) {
        this.jsonRpcMethodContext = jsonRpcMethodContext;
    }


    @PostConstruct
    public void scanMethods1() {

        String[] beanNames = context.getBeanNamesForAnnotation(JsonRpcService.class);

        for (String beanName : beanNames) {
            Object beanObject = context.getBean(beanName);
            Class<?> beanClass = beanObject.getClass();
            Map<String, Map<Method, Object>> collect
                    = Arrays.stream(beanClass.getDeclaredMethods())
                    .peek(e -> {
                        System.out.println(e.getName());
                        System.out.println(Arrays.toString(e.getDeclaredAnnotations()));
                    })
                    .collect(Collectors.toMap(
                            e -> {
                                JsonRpcMethod annotation = e.getAnnotation(JsonRpcMethod.class);
                                return annotation.value();
                            },
                            e -> Map.of(e, beanObject))
                    );
            jsonRpcMethodContext.addAll(collect);

        }
    }

    @Autowired
    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
        scanMethods1();
    }


}
