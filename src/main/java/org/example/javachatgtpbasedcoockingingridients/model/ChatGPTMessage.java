package org.example.javachatgtpbasedcoockingingridients.model;

import lombok.Builder;

@Builder
public record ChatGPTMessage(String role, String content) {
}
