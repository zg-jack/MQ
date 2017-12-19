package com.zhuguang.jack.topic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Subscriber
 */
public class Subscriber extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Subscriber() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    TopicConnection queCon;
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            //1������Ҫ��ȡJNDI��������
            InitialContext context = new InitialContext();
            
            //2����ȡ��Ϣ��ӦTopic
            Topic topic = (Topic)context.lookup("java:comp/env/topic/topic0");
            
            //3����ȡ���е����ӵĹ���TopicConnectionFactory
            TopicConnectionFactory conFactory = (TopicConnectionFactory)context.lookup("java:comp/env/topic/connectionFactory");
            
            //4����ȡTopicConnection
            queCon = conFactory.createTopicConnection();
            
            //5����ȡ���еĻỰTopicSession
            TopicSession queSession = queCon.createTopicSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            
            TopicSubscriber ber = queSession.createSubscriber(topic);
            
            queCon.start();
            
            while (true) {
                TextMessage message = (TextMessage)ber.receive();
                System.out.println("consumer 1 receive message:"
                        + message.getText());
                //                out.write("consumer 1 receive message:" + message.getText());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (queCon != null) {
                try {
                    queCon.close();
                }
                catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
    
}
