## cURL requests

### MealRestController

- curl --location --request GET 'http://localhost:8080/topjava/rest/meals'
- curl --location --request
  GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00&endDate=2020-01-30&endTime=14:00'
- curl --location --request GET 'http://localhost:8080/topjava/rest/meals/100003'
- curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100005'
- curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100004' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "id": 100004,
  "dateTime": "2020-03-20T10:00:00",
  "description": "Полдник",
  "calories": 1300 }'
- curl --location --request POST 'http://localhost:8080/topjava/rest/meals' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "dateTime": "2020-03-25T10:00:00",
  "description": "Полдник",
  "calories": 1300
  }'