# An Api Testing Framework Built Using Cucumber and RestAssured
## A file call application.yaml is required in
```src/test/resources/application.yaml```
```yaml
# base url for all the requests
baseURl: https://jsonplaceholder.typicode.com/todos

```

## Log Configuration is required for logging see log4j2 configuration in  [Apache](https://logging.apache.org/log4j/2.x/manual/configuration.html)
```yaml
src\main\resources\log4j2.xml
```
## All step definition have to extend BaseDefinition class
