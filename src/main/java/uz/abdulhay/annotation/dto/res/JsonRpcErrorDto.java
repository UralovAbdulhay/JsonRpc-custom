package uz.abdulhay.annotation.dto.res;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.abdulhay.annotation.base.JsonRpcBase;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JsonRpcErrorDto extends JsonRpcBase {


    ErrorDto error;

    public JsonRpcErrorDto(Long id, String jsonrpc, ErrorDto params) {
        this.id = id;
        this.jsonrpc = jsonrpc;
        this.error = params;
    }

    public JsonRpcErrorDto(Long id, String jsonrpc) {
        super(id, jsonrpc);
    }

    public static JsonRpcErrorDto of(JsonRpcBase dto, ErrorDto o) {
        return new JsonRpcErrorDto(dto.getId(), dto.getJsonrpc(), o);
    }

    public static JsonRpcErrorDto of(JsonRpcBase dto, String errorMassage, Integer errorCode) {
        ErrorDto errorDto = ErrorDto.builder()
                .code(errorCode)
                .message(errorMassage).build();
        return new JsonRpcErrorDto(dto.getId(), dto.getJsonrpc(), errorDto);
    }

    public static JsonRpcErrorDto getDefault() {
        return new JsonRpcErrorDto(null, "2.0");
    }
}
