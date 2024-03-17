# backend-igclone-java

# Environment
- OS: win/wsl/mac os/linux
- IDE: intellij

# Run Project
1. use intellij idea
2. fill mysql config: `src/main/resources/application.properties`
3. right click `src/main/java/com.yale.fullstackbackend/FullstackBackendApplicaion` > click `Run 'Full stack ... main()'`

# Expected Result
1. start localhost:8080 with no error

# Test Endpoint
```
GET /user
curl localhost:8080/user

POST /user
curl -X POST http://localhost:8080/user \
     -H "Content-Type: application/json" \
     -d '{"name":"hello", "username":"hello", "email":"hello@gmail.com"}'

Delete /user
curl -X DELETE http://localhost:8080/user/9
```
