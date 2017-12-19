package com.zhuguang.jack.jms;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Send
 */

/** 
 * @Description �����JMS�淶�ĵ�Ե�ģ�͵������ 
 * @ClassName   Send 
 * @Date        2017��10��21�� ����9:01:12 
 * @Author      ����ѧԺ-jack
 */

public class Send extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Send() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    QueueConnection queCon;
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            //1������Ҫ��ȡJNDI��������
            InitialContext context = new InitialContext();
            
            //2����ȡ��Ϣ��Ӧqueue
            Queue queue = (Queue)context.lookup("java:comp/env/queue/jackqueue");
            
            //3����ȡ���е����ӵĹ���QueueConnectionFactory
            QueueConnectionFactory conFactory = (QueueConnectionFactory)context.lookup("java:comp/env/queue/connectionFactory");
            
            //4����ȡQueueConnection
            queCon = conFactory.createQueueConnection();
            
            //5����ȡ���еĻỰQueueSession
            QueueSession queSession = queCon.createQueueSession(false,
                    Session.DUPS_OK_ACKNOWLEDGE);
            //6������һ����Ϣ�������,����Ҫ������Ϣ������ߣ��Ѵ�������Ϣ�ŵ���һ����������
            QueueSender queSender = queSession.createSender(queue);
            
            int i = 0;
            while (true) {
                //7������һ����Ϣ
                TextMessage message = queSession.createTextMessage("hello my name is jack"
                        + "【" + i++ + "】");
                
                //8����Ϣ�ķ���
                queSender.send(message);
                System.out.println("Message Send:" + message.getText());
                Thread.sleep(1000);
            }
            
            //            out.write("Message send:" + message.getText());
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
