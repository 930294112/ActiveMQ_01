package com.lanou.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by dllo on 17/11/28.
 */
public class Consumer {
    //获取activeMQ默认的登录用户名
    private static final String USERNAME= ActiveMQConnection.DEFAULT_USER;
    //获取activeMQ默认的密码
    private static  final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //获取activeMQ默认的BROKER_URL
    private static  final String BROKER_URL= ActiveMQConnection.DEFAULT_BROKER_URL;

    public void receiveMessage(String queueName){
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            //创建消息的消费者
            MessageConsumer consumer = session.createConsumer(queue);
            //接收消息

            while (true){
                TextMessage textMessage = (TextMessage) consumer.receive();
                Thread.sleep(1000);
                if (textMessage!=null){
                    //告诉ActiveMQ,消息处理完毕
                    textMessage.acknowledge();
                    System.out.println("处理消息:===>" + textMessage.getText() +"<===");
                }
            }

           // connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.receiveMessage("0703");
    }

}
