package com.talktalkcare.domain.openvidu;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.SessionProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/openvidu/api")
@CrossOrigin(origins = "https://www.talktalkcare.com", 
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS},
    allowCredentials = "true")
public class OpenViduController {

    private final OpenVidu openVidu;

    public OpenViduController() {
        // 도커 네트워크 내부 통신은 http로 가능
        this.openVidu = new OpenVidu("https://openvidu:443", "talktalkcare");
    }

    @PostMapping("/sessions")
    public ResponseEntity<?> initializeSession(@RequestBody(required = false) Map<String, Object> params) {
        try {
            SessionProperties properties = SessionProperties.fromJson(params).build();
            Session session = openVidu.createSession(properties);
            return ResponseEntity.ok(Collections.singletonMap("sessionId", session.getSessionId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/sessions/{sessionId}/connection")
    public ResponseEntity<?> createConnection(
            @PathVariable String sessionId,
            @RequestBody(required = false) Map<String, Object> params) {
        try {
            Session session = openVidu.getActiveSession(sessionId);
            if (session == null) {
                // 세션이 없으면 새로 생성
                SessionProperties properties = SessionProperties.fromJson(params).build();
                session = openVidu.createSession(properties);
            }
            ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
            Connection connection = session.createConnection(properties);
            return ResponseEntity.ok(Collections.singletonMap("token", connection.getToken()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}