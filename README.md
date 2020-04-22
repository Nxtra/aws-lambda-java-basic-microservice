# AWS-Lambda-java-basic-microservice

## Build and deploy

* build: `./gradlew clean build`
* deploy first time: `sam deploy --guided`
* deploy: `sam deploy`

* delete the stack: `aws cloudformation delete-stack --stack-name aws-lambda-java-basic-service`


# TODO
* multiple handlers
* instrument with xray
* output url?