package com.zhuguang.jack.jms;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Receive
 */

/** 
 * @Description �����JMS�淶�ĵ�Ե�ģ�͵������
 * @ClassName   Receive 
 * @Date        2017��10��21�� ����9:01:47 
 * @Author      ����ѧԺ-jack
 */

public class Receive1 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Receive1() {
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
            //6����ȡ��Ϣ�Ľ�����
            QueueReceiver queReceiver = queSession.createReceiver(queue);
            //���ӿ���
            queCon.start();
            //7����ȡ��Ϣ
            while (true) {
                TextMessage message = (TextMessage)queReceiver.receive();
                
                if (message != null) {
                    System.out.println("Message Received2:" + message.getText());
                    //                    out.write("Message Received:" + message.getText());
                }
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
    
    public static void main(String[] args) {
        
    }
    
}
