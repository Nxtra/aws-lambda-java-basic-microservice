# AWS-Lambda-java-basic-microservice

## Build and deploy

* build: `./gradlew clean build`
* deploy first time: `sam deploy --guided`
* deploy: `sam deploy`
* build and deploy. After deploying for the first time you can build and deploy wit using the `build-and-deploy.sh` script.
* delete the stack: `aws cloudformation delete-stack --stack-name aws-lambda-java-basic-service`

## Architecture


## Playing with the application
Once deployed copy the ApiGateway url and start sending requests to it.
You can post a basic "memoryCard" that looks like this: 
```
{
  "author": "@TheNickVanHoof",
  "memoryText": "Use the new dynamodb enhanced client",
  "category": "cloud"
}
```
This will then be saved in the database.  
You can create more memoryCards.  
Or you can delete, getOne, getAll.  
These endpoints will all trigger different operations on DynamoDB using the `enhanced-client` for DynamoDB.

**Intellij Http**
If you are using IntelliJ you can use the Http Requests in the `http` folder to trigger the endpoints.  
* Update the URL in `http-client.env.json` to your own URL
* run all requests in `api.http` using the `test` environment

