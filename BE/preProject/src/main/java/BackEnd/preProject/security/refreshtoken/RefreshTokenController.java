package BackEnd.preProject.security.refreshtoken;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshTokenController {

    @PostMapping("/refresh")
    public ResponseEntity refreshToken(@RequestHeader(value = "REFRESH-TOKEN") String refreshToken) {

    }

}
