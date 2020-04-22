package io.theclouddeveloper.memorycards.service;

import io.theclouddeveloper.memorycards.model.MemoryCard;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.Optional;


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

    public Optional<MemoryCard> getMemoryCardByUuid(String uuid){

        DynamoDbIndex<MemoryCard> memoryCardByUuidIndex = memoryCardTable.index("uuidIndex");
        PageIterable<MemoryCard> memoryCardWithUuid = (PageIterable<MemoryCard>) memoryCardByUuidIndex
                .query(r -> r.queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(uuid))).limit(1)); // why te cast?
        return memoryCardWithUuid.items().stream().findFirst();
    }

    public MemoryCard deleteMemoryCard(MemoryCard memoryCardToDelete){
        log.info("Deleting memoryCard with id: {}", memoryCardToDelete.getUuid());
        MemoryCard memoryCardThatWasDeleted = memoryCardTable.deleteItem(Key.builder().partitionValue(memoryCardToDelete.getAuthor()).sortValue(memoryCardToDelete.getCategoryCreatedTimestamp()).build());
        return memoryCardThatWasDeleted;
    }
}
