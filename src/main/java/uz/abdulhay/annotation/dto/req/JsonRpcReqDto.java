package uz.abdulhay.annotation.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import uz.abdulhay.annotation.base.JsonRpcBase;

@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JsonRpcReqDto extends JsonRpcBase {

    @NotBlank
    String method;

    Object params;

    public JsonRpcReqDto(Long id, String jsonrpc, Object params) {
        super.id = id;
        super.jsonrpc = jsonrpc;
        this.params = params;
    }

    public JsonRpcReqDto(Long id, String jsonrpc) {
        super(id, jsonrpc);
    }

    public static JsonRpcReqDto of(JsonRpcBase dto, Object o) {
        return new JsonRpcReqDto(dto.getId(), dto.getJsonrpc(), o);
    }

    public static JsonRpcReqDto getDefault() {
        return new JsonRpcReqDto(null, "2.0");
    }

}
