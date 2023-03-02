import jakarta.jms.*;
import javax.naming.*;
import java.io.*;

public class  Receveur {

    public static void main(String[] args) throws NamingException, JMSException{
        InitialContext messaging = new InitialContext();
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory) messaging.lookup("jms/ConnectionFactoryPtP");
        Queue queue = (Queue) messaging.lookup("jms/QueuePtP");
        QueueConnection connection = connectionFactory.createQueueConnection();
        QueueSession session = connection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
        connection.start();

        QueueReceiver receiver = session.createReceiver(queue);
        TextMessage msg = (TextMessage) receiver.receive();
        System.out.println(msg.getText());

        connection.close();
    }
}