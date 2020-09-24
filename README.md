# Todo

## How to start the Todo application

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/Todo-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`


## End points
1. GET `/tasks/todo`
1. GET /`tasks/todos/{id}`
1. PUSH `/tasks/todos`
1. PUT `/tasks/todos/{id}`
1. DELETE `/tasks/todos/{id}`

## Health Check

To see your applications health enter url `http://localhost:8081/healthcheck`
