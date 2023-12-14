package uz.abdulhay.annotation.service;

import uz.abdulhay.annotation.annotation.JsonRpcMethod;
import uz.abdulhay.annotation.annotation.JsonRpcService;
import uz.abdulhay.annotation.dto.req.Person;

@JsonRpcService
public class MyService {

    @JsonRpcMethod(value = "test")
    public String test(Person o) {
        return o + " = test";
    }

    @JsonRpcMethod(value = "hello")
    public String test1(Person o) {
        return o + " = hello";
    }

}
