package io.theclouddeveloper.memorycards;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.theclouddeveloper.memorycards.model.MemoryCard;
import io.theclouddeveloper.memorycards.service.MemoryCardService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Handler implements RequestHandler<APIGatewayV2ProxyRequestEvent, APIGatewayV2ProxyResponseEvent> {

    Gson gson = new GsonBuilder().create();
    MemoryCardService memoryCardService = new MemoryCardService();

    @Override
    public APIGatewayV2ProxyResponseEvent handleRequest(APIGatewayV2ProxyRequestEvent input, Context context) {
        String body = input.getBody();
        MemoryCard memoryCard = parseMemoryCard(body);

        log.info("Received memoryCard: {}", memoryCard);

        MemoryCard createdMemoryCard = memoryCardService.processCreateNewMemoryCardRequest(memoryCard);

        Map<String, String> headers = Map.of("Content-Type", "application/json");

        APIGatewayV2ProxyResponseEvent response = new APIGatewayV2ProxyResponseEvent(); // It's a pity there is not default builder for this class
        response.setBody(gson.toJson(createdMemoryCard));
        response.setHeaders(headers);
        response.setStatusCode(202);

        return response;
    }

    private MemoryCard parseMemoryCard(String memoryCardStringified){
        return gson.fromJson(memoryCardStringified, MemoryCard.class);
    }

}
