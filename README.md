# Todo

## How to start the Todo application

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/Todo-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Availabel REST Apis
GET /tasks/todos
GET /tasks/todos/{id}
PUSH /tasks/todos
PUT /tasks/todos/{id}
DELETE /tasks/todos/{id}

## Health Check

To see your applications health enter url `http://localhost:8081/healthcheck`
