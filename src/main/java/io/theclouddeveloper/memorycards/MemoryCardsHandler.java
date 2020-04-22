package io.theclouddeveloper.memorycards;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.theclouddeveloper.memorycards.model.ResponseMessageBody;
import io.theclouddeveloper.memorycards.model.MemoryCard;
import io.theclouddeveloper.memorycards.service.MemoryCardService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class MemoryCardsHandler {

    private Gson gson;
    private MemoryCardService memoryCardService;

    public MemoryCardsHandler() {
        gson = new GsonBuilder().create();
        memoryCardService = new MemoryCardService();
    }

    public APIGatewayV2ProxyResponseEvent handleCreateNewMemoryCardRequest(APIGatewayV2ProxyRequestEvent input, Context context) {
        String body = input.getBody();
        MemoryCard memoryCard = parseMemoryCard(body);

        log.info("Received memoryCard: {}", memoryCard);

        MemoryCard createdMemoryCard = memoryCardService.processCreateNewMemoryCardRequest(memoryCard);

        Map<String, String> headers = Map.of("Content-Type", "application/json");

        APIGatewayV2ProxyResponseEvent response = new APIGatewayV2ProxyResponseEvent(); // It's a pity there is no default builder for this class
        response.setBody(gson.toJson(createdMemoryCard));
        response.setHeaders(headers);
        response.setStatusCode(202);

        return response;
    }



    public APIGatewayV2ProxyResponseEvent handleGetMemoryCardByUuidRequest(APIGatewayV2ProxyRequestEvent input, Context context) {
        String uuid = input.getPathParameters().get("uuid");

        log.info("Received request to fetch card with id: {}", uuid);


        Optional<MemoryCard> fetchedMemoryCard = memoryCardService.processGetMemoryCardByUuid(uuid);
        if(fetchedMemoryCard.isEmpty()){
            APIGatewayV2ProxyResponseEvent response = new APIGatewayV2ProxyResponseEvent();
            response.setStatusCode(404);
            return response;
        }

        Map<String, String> headers = Map.of("Content-Type", "application/json");

        APIGatewayV2ProxyResponseEvent response = new APIGatewayV2ProxyResponseEvent();
        response.setBody(gson.toJson(fetchedMemoryCard.get()));
        response.setHeaders(headers);
        response.setStatusCode(200);

        return response;
    }

    public APIGatewayV2ProxyResponseEvent handleDeleteMemoryCardByUuid(APIGatewayV2ProxyRequestEvent input, Context context) {
        String uuid = input.getPathParameters().get("uuid");

        log.info("Received request to delete card with id: {}", uuid);

        boolean deleteSuccesfull = memoryCardService.processDeleteMemoryCardByUuidRequest(uuid);
        Map<String, String> headers = Map.of("Content-Type", "application/json");

        if(!deleteSuccesfull){
            ResponseMessageBody responseMessageBody = ResponseMessageBody.builder()
                    .message(String.format("Unable to delete memoryCard with uuid: %s", uuid))
                    .build();
            APIGatewayV2ProxyResponseEvent response = new APIGatewayV2ProxyResponseEvent();
            response.setStatusCode(404);
            response.setHeaders(headers);
            response.setBody(gson.toJson(responseMessageBody));
            return response;
        }

        ResponseMessageBody responseMessageBody = ResponseMessageBody.builder()
                .message(String.format("Successfully deleted memoryCard with uuid: %s", uuid))
                .build();
        APIGatewayV2ProxyResponseEvent response = new APIGatewayV2ProxyResponseEvent();
        response.setBody(gson.toJson(responseMessageBody));
        response.setHeaders(headers);
        response.setStatusCode(200);

        return response;
    }






    private MemoryCard parseMemoryCard(String memoryCardStringified){
        return gson.fromJson(memoryCardStringified, MemoryCard.class);
    }

}
