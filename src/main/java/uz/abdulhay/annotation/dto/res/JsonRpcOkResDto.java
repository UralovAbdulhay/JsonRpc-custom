package uz.abdulhay.annotation.dto.res;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.abdulhay.annotation.base.JsonRpcBase;
import uz.abdulhay.annotation.dto.req.JsonRpcReqDto;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JsonRpcOkResDto extends JsonRpcBase {

    Object result;

    public JsonRpcOkResDto(Long id, String jsonrpc, Object params) {
        this.id = id;
        this.jsonrpc = jsonrpc;
        this.result = params;
    }

    public static JsonRpcOkResDto of(JsonRpcBase dto, Object o) {
        return new JsonRpcOkResDto(dto.getId(), dto.getJsonrpc(), o);
    }

}
