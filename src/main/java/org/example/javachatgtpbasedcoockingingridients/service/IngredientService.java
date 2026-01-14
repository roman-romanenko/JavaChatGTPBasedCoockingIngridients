package org.example.javachatgtpbasedcoockingingridients.service;

import org.example.javachatgtpbasedcoockingingridients.model.ChatGPTMessage;
import org.example.javachatgtpbasedcoockingingridients.model.ChatGPTMessageRole;
import org.example.javachatgtpbasedcoockingingridients.model.ChatGPTRequest;
import org.example.javachatgtpbasedcoockingingridients.model.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class IngredientService {

    private final RestClient restClient;

    private static final ChatGPTMessage DEVELOPER_MESSAGE =
            ChatGPTMessage.builder()
                    .role(ChatGPTMessageRole.DEVELOPER.getValue())
                    .content("""
                        You are an ingredient classification expert.
                        You will receive the name of one food ingredient.
                        Return ONLY one word: "vegan", "vegetarian", or "regular".
                        Do not add explanations or extra text.
                        Classification rules:
                            vegan = no animal products;
                            vegetarian = animal products without meat or fish (e.g. milk, eggs, honey);
                            regular = meat, fish, seafood, gelatin, or animal-derived ingredients requiring slaughter.
                        If the ingredient is ambiguous or unknown, return "regular".
                        Ingredient names may be in any language.
                    
                        If the input is NOT a real food ingredient (e.g. car, river, table),
                        return ONLY the word "other".
                    """)
                    .build();


    public IngredientService(RestClient.Builder restClientBuilder,
                             @Value("${OPEN_AI_API_KEY}") String token) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    public String categorizeIngredient(String ingredient) {
        ChatGPTRequest requestBody = buildRequest(ingredient);
        ChatGPTResponse response = sendRequest(requestBody);
        String responseMesssage = response.choices().getFirst().message().content();

        if(responseMesssage.equals("other")) {
            return ingredient + " is not a real ingredient. Please try again with a different ingredient.";
        }

        return responseMesssage;
    }

    private ChatGPTRequest buildRequest(String ingredient) {
        ChatGPTMessage userMessage = ChatGPTMessage.builder()
                .role(ChatGPTMessageRole.USER.getValue())
                .content(ingredient)
                .build();

        return ChatGPTRequest.builder()
                .model("gpt-5-nano-2025-08-07")
                .messages(List.of(DEVELOPER_MESSAGE, userMessage))
                .build();
    }

    private ChatGPTResponse sendRequest(ChatGPTRequest requestBody) {
        return restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(ChatGPTResponse.class);
    }
}


