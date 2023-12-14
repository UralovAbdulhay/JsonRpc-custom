package uz.abdulhay.annotation.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class JsonRpcMethodContext {


    @Getter
    private final Map<String, Map<Method, Object>> methods = new HashMap<>();

    public void add(String methodName, Map<Method, Object> method) {
        methods.put(methodName, method);
    }

    public void addAll(Map<String, Map<Method, Object>> newMethods) {

        methods.putAll(newMethods);
    }

    public Optional<Map<Method, Object>> getMethod(String methodName) {
        return Optional.ofNullable(this.methods.get(methodName));
    }
}
