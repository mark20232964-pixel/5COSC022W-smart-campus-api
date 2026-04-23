## Report Answers

### Part 1

**Q1: Lifecycle of JAX-RS resources**  
In JAX-RS, resource classes are typically created per request, meaning a new object is generated each time the API is called. This avoids shared-state issues between requests. However, when using shared in-memory data structures like HashMaps, extra care is needed to ensure consistency across requests.

**Q2: Role of HATEOAS**  
HATEOAS allows an API to guide clients by including links within responses. This means clients can discover related endpoints dynamically instead of relying only on external documentation. It improves flexibility and reduces tight coupling between client and server.



### Part 2

**Q1: Returning IDs vs full objects**  
Returning only identifiers reduces response size and improves efficiency, but requires additional requests to fetch full details. Returning full objects reduces the number of calls needed but increases payload size. The choice depends on performance vs convenience trade-offs.

**Q2: Why DELETE is idempotent**  
DELETE is considered idempotent because repeating the same request produces the same result. Once a resource is removed, sending the same DELETE request again does not change the system state further.



### Part 3

**Q1: Purpose of @Consumes**  
The @Consumes annotation defines the expected format of incoming request data. If the client sends data in an unsupported format, the server responds with a 415 error, preventing incorrect processing.

**Q2: Query parameters vs path parameters**  
Query parameters are more suitable for filtering and optional inputs, while path parameters are better for identifying specific resources. This makes query parameters more flexible for search-based operations.



### Part 4

**Q1: Sub-resource locator concept**  
Using sub-resource locators helps split complex API structures into smaller, more manageable parts. It improves code organisation and makes the system easier to extend and maintain.

**Q2: Keeping sensor data consistent**  
Whenever a new sensor reading is added, the sensor’s current value is updated immediately. This ensures that the API always reflects the latest sensor state without requiring additional computation.



### Part 5

**Q1: Difference between HTTP 422 and 404**  
A 422 error is used when the request is syntactically correct but semantically invalid, such as referencing a non-existent linked resource. A 404 is used when a resource cannot be found at all.

**Q2: Security risk of stack traces**  
Returning stack traces exposes internal system details like file paths and class names, which can help attackers identify vulnerabilities in the application.

**Q3: Why use filters for logging**  
Using a central filter for logging is better than writing logs inside each method because it reduces code duplication and ensures consistent logging across all endpoints.