# Getting Started

# Help and Documentation

### Get All Books

```shell
  curl -u admin:password -X GET http://localhost:8081/books -H "Accept: application/json"
```

### Get Book by ID

```shell
  curl -u admin:password -X GET http://localhost:8081/books/1 -H "Accept: application/json"
```

### Add a New Book

```shell
  curl -u admin:password -X POST http://localhost:8081/books -H "Content-Type: application/json" -d '{"title": "New Book", "author": "Author Name"}'
```

### Update a Book

```shell
  curl -u admin:password -X PUT http://localhost:8081/books/1 -H "Content-Type: application/json" -d '{"title": "Updated Book", "author": "Updated Author"}'
```

### Delete a Book

```shell
  curl -u admin:password -X DELETE http://localhost:8081/books/1
```

# OpenAPI Documentation

### Swagger UI

You can access the Swagger UI at:

```
    http://localhost:8081/swagger-ui/index.html#/
``` 
