import jakarta.jms.*;
import javax.naming.*;
import java.io.*;

public class  Emetteur {

    public static void main(String[] args) throws NamingException, JMSException{
        InitialContext messaging = new InitialContext();
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory) messaging.lookup("jms/ConnectionFactoryPtP");
        Queue queue = (Queue) messaging.lookup("jms/QueuePtP");
        QueueConnection connection = connectionFactory.createQueueConnection();
        QueueSession session = connection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
        connection.start();

        QueueSender sender =session.createSender(queue);
        TextMessage msg = session.createTextMessage();
        msg.setText("Hello world !");
        sender.send(msg);

        connection.close();
    }
}