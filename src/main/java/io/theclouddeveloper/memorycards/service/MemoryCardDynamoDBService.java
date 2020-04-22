package io.theclouddeveloper.memorycards.service;

import io.theclouddeveloper.memorycards.model.MemoryCard;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;


@Slf4j
public class MemoryCardDynamoDBService {

    private static final TableSchema<MemoryCard> MEMORYCARD_TABLE_SCHEMA = TableSchema.fromBean(MemoryCard.class);
    private DynamoDbClient dynamoDbClient;
    private DynamoDbEnhancedClient enhancedClient;
    private DynamoDbTable<MemoryCard> memoryCardTable;

    public MemoryCardDynamoDBService() {
        dynamoDbClient = DynamoDbClient.builder()
                .region(System.getenv("REGION") != null? Region.of(System.getenv("REGION")) : Region.of("us-east-1"))
                .build();
        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        memoryCardTable  = enhancedClient.table(System.getenv("TABLE_NAME"), MEMORYCARD_TABLE_SCHEMA);
    }

    public void saveMemoryCard(MemoryCard memoryCard){
        log.info("Saving memoryCard: {}", memoryCard);
        memoryCardTable.putItem(memoryCard);
    }
}
