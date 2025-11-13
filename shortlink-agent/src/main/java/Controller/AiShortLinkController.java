package com.example.shortlinkagent.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiShortLinkController {

    private final ChatClient chatClient;

    public AiShortLinkController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/ai/generate-short-link")
    public String generateShortLink(@RequestBody MessageRequest request) {
        return chatClient.prompt()
                .user(request.getMessage())
                .call()
                .content();
    }

    public static class MessageRequest {
        private String message;

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
