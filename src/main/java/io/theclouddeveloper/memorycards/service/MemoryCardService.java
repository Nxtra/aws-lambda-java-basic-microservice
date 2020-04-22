package io.theclouddeveloper.memorycards.service;

import io.theclouddeveloper.memorycards.model.MemoryCard;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.UUID;

@Slf4j
public class MemoryCardService {

    MemoryCardDynamoDBService memoryCardDynamoDBService;

    public MemoryCardService() {
        memoryCardDynamoDBService = new MemoryCardDynamoDBService();
    }

    public MemoryCard processCreateNewMemoryCardRequest(MemoryCard newMemoryCard){
        log.info("Processing memory card: {}", newMemoryCard);

        String createdTimestamp = Long.toString(Instant.now().toEpochMilli());
        String categoryCreatedTimestamp = newMemoryCard.getCategory() + '_' + createdTimestamp;
        String uuid = UUID.randomUUID().toString();

        MemoryCard memoryCard = MemoryCard.builder()
                .author(newMemoryCard.getAuthor())
                .categoryCreatedTimestamp(categoryCreatedTimestamp)
                .category(newMemoryCard.getCategory())
                .createdTimestamp(createdTimestamp)
                .memoryText(newMemoryCard.getMemoryText())
                .uuid(uuid)
                .build();

        log.info("Instantiated memoryCard: {}",memoryCard);

        memoryCardDynamoDBService.saveMemoryCard(memoryCard);

        return memoryCard;
    }
}
