package io.theclouddeveloper.memorycards;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.theclouddeveloper.memorycards.model.MemoryCard;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Handler implements RequestHandler<APIGatewayV2ProxyRequestEvent, APIGatewayV2ProxyResponseEvent> {

    Gson gson = new GsonBuilder().create();

    @Override
    public APIGatewayV2ProxyResponseEvent handleRequest(APIGatewayV2ProxyRequestEvent input, Context context) {
        log.info("event: {}", input);
        String body = input.getBody();
        MemoryCard memoryCard = parseMemoryCard(body);

        log.info("Received memoryCard: {}", memoryCard);

        Map<String, String> headers = Map.of("Content-Type", "application/json");

        APIGatewayV2ProxyResponseEvent response = new APIGatewayV2ProxyResponseEvent(); // It's a pity there is not default builder for this class
        response.setBody(gson.toJson(memoryCard));
        response.setHeaders(headers);
        response.setStatusCode(202);

        return response;
    }

    private MemoryCard parseMemoryCard(String memoryCardStringified){
        MemoryCard memoryCard = gson.fromJson(memoryCardStringified, MemoryCard.class);
        return memoryCard;
    }
}
