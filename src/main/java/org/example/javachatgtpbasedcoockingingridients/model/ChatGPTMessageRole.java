package org.example.javachatgtpbasedcoockingingridients.model;

import lombok.Getter;

@Getter
public enum ChatGPTMessageRole {
    DEVELOPER("developer"),
    USER("user");
    private final String value;

    ChatGPTMessageRole(String value) {
        this.value = value;
    }
}
