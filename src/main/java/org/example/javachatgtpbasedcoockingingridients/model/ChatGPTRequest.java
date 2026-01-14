package org.example.javachatgtpbasedcoockingingridients.model;

import lombok.Builder;

import java.util.List;

@Builder
public record ChatGPTRequest(String model, List<ChatGPTMessage> messages) {
}
