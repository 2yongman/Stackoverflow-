package BackEnd.preProject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JwtDecoder {
    public Map<String,Object> jwtPayloadToMap(String jwt){
            ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> payloadMap = null;
        try {
            payloadMap = objectMapper.readValue(jwt, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 변환된 Map 출력
            return payloadMap;
    }
}
