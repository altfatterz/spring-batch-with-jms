# Trigger a Spring Batch job with a JMS message 

The example is using Spring Integration to receive the JMS message. There are two approaches:

- using `JmsDestinationPollingSource` 
- message-driven approach using the `JmsMessageDrivenEndpoint` 

## Setup dependencies 

1. Install and start ActiveMQ:

```bash
brew install activemq
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

4. Start hawtio agent:

On the `http://localhost:9999/hawtio` click on the `Connect` tab, then `Local` then `Apache ActiveMQ` and then click `Start the agent`. 
Agent URL will be provided and then click on it.

## Receive a JMS message using JmsDestinationPollingSource

1. Start app with the `InboudChannelAdapterExample` providing the `--spring.mail.username=<username>` and `--spring.mail.password=<password>` program arguments. 
2. Send a message to the `notification` queue.

```xml
<notification>
   <email>example@gmail.com</email>
   <status>ORDER_DISPATCHED</status>
</notification>

```
3. Verify that the Spring Batch job is triggered and an email is sent to `example@gmail.com` 

## Receive a JMS message using JmsMessageDrivenEndpoint

1. Start the app with the `MessageDrivenChannelAdapterExample`  
2. Send a message to the `trade` queue.

```xml
<trade>
  <stock>AAPL</stock>
  <quantity>100</quantity>
  <action>BUY</action>
</trade>
```

3. Verify that the Spring Batch job is triggered and the trade is logged in the console.
