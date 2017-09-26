Spring Integration Java DSL Reference

https://github.com/spring-projects/spring-integration-java-dsl/wiki/spring-integration-java-dsl-reference

- two JMS based inbound Channel Adapater

1. Inbound Channel Adapter
    - using Spring’s JmsTemplate to receive based on a polling period.

2. Message Driven Channel Adapter
    - requires a reference to an instance of a Spring MessageListener container
    



Trying the first version:

1. Start activemq:

```bash
brew services start activemq
```    

2. Verify that you access:

```bash
http://localhost:8161/admin/
credentials: admin/admin
```

3. Start hawtio client

```bash
java -jar hawtio-app-1.5.4.jar --port 9999
```

4. Access hawtio

```bash
``` 

5. Start hawtio agent:

On the `http://localhost:9999/hawtio` click on the `Connect` tab, then `Local` then `Apache ActiveMQ` and then click `Start the agent`. 
Agent URL will be provided and then click on it.

6. Send a message on the already created `jobtrigger` queue:

```xml
<trade>
  <stock>AAPL</stock>
  <quantity>100</quantity>
  <action>BUY</action>
</trade>
```

7. Verify that the job is being triggered:

```bash


```
Here note also that the data is extracted from the message and set as a job parameter.

Very good examples for Spring Integration Java DSL:
https://github.com/spring-projects/spring-integration-java-dsl/wiki/Spring-Integration-Java-DSL-Reference#examples


Spring Integration JMS example with XML:
https://github.com/spring-projects/spring-integration-samples/blob/master/basic/jms/src/main/resources/META-INF/spring/integration/inboundChannelAdapter.xml

Spring Integration JMS support:
https://docs.spring.io/spring-integration/reference/htmlsingle/#jms