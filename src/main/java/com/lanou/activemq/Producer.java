package com.lanou.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by dllo on 17/11/28.
 */
public class Producer {
    //获取activeMQ默认的登录用户名
    private static final String USERNAME= ActiveMQConnection.DEFAULT_USER;
    //获取activeMQ默认的密码
    private static  final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //获取activeMQ默认的BROKER_URL
    private static  final String BROKER_URL= ActiveMQConnection.DEFAULT_BROKER_URL;

    public void sendMessage(String queueName){

        try {
            //1.创建一个连接工厂
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKER_URL);
            //2.创建一个连接
            Connection connection = connectionFactory.createConnection();
            //3.开启连接
            connection.start();
            //4.61616 获取事物
            Session session = connection.createSession(true,Session.SESSION_TRANSACTED);
            //5.创建消息对列
            Queue queue = session.createQueue(queueName);
            //6.创建消息生成器
            MessageProducer messageProducer = session.createProducer(queue);
            Integer count =0;
            while (true){
                Thread.sleep(1000);
                //7.创建消息
                TextMessage textMessage = session.createTextMessage("这是一条消息:" + count++);
                //8.发送
                messageProducer.send(textMessage);
                //9.提交事物
                session.commit();
            }

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.sendMessage("0703");
    }
}
