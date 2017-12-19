package com.zhuguang.jack.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
    
    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        MessageProducer producer;
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
        
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("J-MQ-QUEUE");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMesasge(session, producer);
            //session����Ҫ�ύ���͸����ǵ�������һ��ģ����activeMQ��ͬ�������ĸ���
            session.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            }
            catch (Throwable ee) {
                
            }
        }
    }
    
    public static void sendMesasge(Session session, MessageProducer producer)
            throws Exception {
        for (int i = 0; i < 10; i++) {
            TextMessage message = session.createTextMessage("ActiveMQ ������Ϣ: jack"
                    + i);
            System.out.println("������Ϣ��" + "ActiveMQ ������Ϣ" + i);
            producer.send(message);
        }
    }
    
}
