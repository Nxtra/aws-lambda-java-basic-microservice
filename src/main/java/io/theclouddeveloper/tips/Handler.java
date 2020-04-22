package io.theclouddeveloper.tips;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyRequestEvent> {

    @Override
    public APIGatewayProxyRequestEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        return null;
        
    }
}
