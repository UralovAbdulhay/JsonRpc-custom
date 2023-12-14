package uz.abdulhay.annotation.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.el.MethodNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import uz.abdulhay.annotation.base.JsonRpcBase;
import uz.abdulhay.annotation.config.JsonRpcMethodContext;
import uz.abdulhay.annotation.dto.req.JsonRpcReqDto;
import uz.abdulhay.annotation.dto.res.ErrorDto;
import uz.abdulhay.annotation.dto.res.JsonRpcErrorDto;
import uz.abdulhay.annotation.dto.res.JsonRpcOkResDto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@RestController
@RequestMapping(value = "/jsonrpc")
@RestControllerAdvice
public class EntryPointController {


    private final JsonRpcMethodContext jsonRpcMethodContext;


    public EntryPointController(JsonRpcMethodContext jsonRpcMethodContext) {
        this.jsonRpcMethodContext = jsonRpcMethodContext;
    }

    @PostMapping("/**")
    public ResponseEntity<JsonRpcBase> into(@RequestBody @Validated() JsonRpcReqDto request) {

        System.out.println(request);

        try {
            String methodName = request.getMethod();
            Map<Method, Object> methodMap = jsonRpcMethodContext.getMethod(methodName).orElseThrow(
                    () -> new MethodNotFoundException("Method not found")
            );

            Object params = request.getParams();

            if (methodMap.isEmpty()) {
                ErrorDto errorDto = ErrorDto.builder()
                        .code(-2)
                        .message("Method not found").build();
                return ResponseEntity.ok(JsonRpcErrorDto.of(request, errorDto));
            }

            Method method = methodMap.keySet().toArray(new Method[]{})[0];
            Object beanObject = methodMap.get(method);

            Class<?> parameterType = method.getParameterTypes()[0];

            ObjectMapper mapper = new ObjectMapper();
            Object param = mapper.convertValue(params, parameterType);

            try {
                Object invoke = method.invoke(beanObject, param);
                return ResponseEntity.ok(JsonRpcOkResDto.of(request, invoke));
            } catch (IllegalAccessException e) {
                ErrorDto methodNotFound = ErrorDto.builder()
                        .code(-1)
                        .message(e.getMessage()).build();

                return ResponseEntity.ok(JsonRpcErrorDto.of(request, methodNotFound));
            } catch (InvocationTargetException e) {
                ErrorDto errorDto = ErrorDto.builder()
                        .code(-2)
                        .message(e.getMessage()).build();
                return ResponseEntity.ok(JsonRpcErrorDto.of(request, errorDto));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDto errorDto = ErrorDto.builder()
                    .code(-3)
                    .message(e.getMessage()).build();
            return ResponseEntity.ok(JsonRpcErrorDto.of(request, errorDto));
        }
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<JsonRpcBase> handleBadRequest(HttpClientErrorException.BadRequest e) {
        e.printStackTrace();
        return ResponseEntity.ok(JsonRpcErrorDto.of(
                JsonRpcErrorDto.getDefault(), e.getMessage(), -4001
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<JsonRpcBase> handle(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return ResponseEntity.ok(JsonRpcErrorDto.of(
                JsonRpcErrorDto.getDefault(), "Bad Request", -4002
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonRpcBase> handle(Exception e) {
        e.printStackTrace();
        return ResponseEntity.ok(JsonRpcErrorDto.of(
                JsonRpcErrorDto.getDefault(), "Bad Request", -4003
        ));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<JsonRpcBase> handle(Error e) {
        e.printStackTrace();
        return ResponseEntity.ok(JsonRpcErrorDto.of(
                JsonRpcErrorDto.getDefault(), "Bad Request", -4004
        ));
    }
}
