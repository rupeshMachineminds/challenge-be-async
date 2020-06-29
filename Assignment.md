# Assignment

## Heavy report generation

The application exposes a set of HTTP routes, one of which generates report files.

If file generation takes too long (i.e., more than 5 seconds), a **gateway timeout** will occur.

```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/challenge-async-web/reports

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/challenge-async-web/reports?weight=HEAVY
```

In case of timeout:

- return 202 Accepted response;
- update the client accordingly, so that it handles the new possible response;
- use `ConsumerService.consumeReport` to asynchronously deliver the report to the client in the following format:

```
{
  "requestId": <string>
  "report": {
    "id": <string>
    "value": <string>
    "weight": <string>
    "elaborationTimeInMs": <number>
  }
}
```

Feel free to ask questions and make assumptions.

## Bonus points

- implement a different report delivery channel using SSE (Server-Sent Events) and sending messages with the same data format described above.
