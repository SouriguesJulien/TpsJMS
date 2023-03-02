import jakarta.jms.*;
import javax.naming.*;
import java.io.*;

public class  Emetteur {

    public static void main(String[] args) throws NamingException, JMSException{
        if (args.length < 1){
            System.err.println("Erreur : Argument invalid");
            return;
        }
        InitialContext messaging = new InitialContext();
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory) messaging.lookup("jms/ConnectionFactoryPtP");
        Queue queue = (Queue) messaging.lookup("jms/QueuePtP");
        QueueConnection connection = connectionFactory.createQueueConnection();
        QueueSession session = connection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
        connection.start();

        QueueSender sender = session.createSender(queue);
        TextMessage msg = session.createTextMessage();

        msg.setText(args[0]);
        if (args.length > 1) {
            msg.setStringProperty("destinataire", args[1]);
        }
        sender.send(msg);

        connection.close();
    }
}