import jakarta.jms.*;
import javax.naming.*;
import java.io.*;

public class  Receveur {

    public static void main(String[] args) throws NamingException, JMSException, InterruptedException {

        if (args.length < 2){
            System.err.println("Erreur : Argument invalid");
            return;
        }

        InitialContext messaging = new InitialContext();
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory) messaging.lookup("jms/ConnectionFactoryPtP");
        Queue queue = (Queue) messaging.lookup("jms/QueuePtP");
        QueueConnection connection = connectionFactory.createQueueConnection();
        QueueSession session = connection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
        connection.start();

        QueueReceiver receiver = session.createReceiver(queue);
        while (true){
            TextMessage msg = (TextMessage) receiver.receive();
            System.out.println("message reçu par " + args[1] + " = \"" + msg.getText() + "\"");
            Thread.sleep(Integer.parseInt(args[0]));
        }

        //connection.close();
    }
}