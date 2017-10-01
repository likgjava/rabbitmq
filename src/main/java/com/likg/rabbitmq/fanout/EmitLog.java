package com.likg.rabbitmq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); //fanout表示分发，所有的消费者得到同样的队列信息

        //分发信息
        for (int i = 0; i < 100; i++) {
            String message = "Hello World" + i;
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println("EmitLog Sent '" + message + "'");
            Thread.sleep(1000);
        }

        channel.close();
        connection.close();
    }

}
