package com.vovan.dwh.data_population;

import com.vovan.dwh.data_population.events.TcpSocketDataPopulationEvent;
import com.vovan.dwh.data_population.generator.ConsumptionLogsGeneratorFactory;
import com.vovan.dwh.models.PowerConsumption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Volodymyr Roman on 25.11.2016.
 */
@Component
public class SocketServerPopulator implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServerPopulator.class);
    private static final long POPULATION_INTERVAL = 2000L;
    private static final long SOCKET_RESTART_INTERVAL = 10000L;
    private static final String HEARTBEAT_MESSAGE = "---SENDING_HEARTBEAT---\n";

    @Value("${data_source.tcp.port}")
    private int tcpPort;

    private LinkedList<TcpSocketDataPopulationEvent> eventsQueue = new LinkedList();


    @Override
    public void run() {
        int countFails = 0;
        ServerSocket serverSocket = null;
        Socket server = null;

        while (true) {
            try {
                serverSocket = new ServerSocket(tcpPort);
                server = serverSocket.accept();

                LOGGER.info("New connection with TCP socket, port {}", tcpPort);
                DataOutputStream out = new DataOutputStream(server.getOutputStream());

                listenEventsAndPublishLogs(out);
            } catch (IOException | InterruptedException e) {
                countFails++;
                LOGGER.warn("TCP socket if failed for {} times. Error message: {}", countFails, e.toString());
                try {
                    Thread.sleep(SOCKET_RESTART_INTERVAL);
                } catch (InterruptedException e1) {
                    LOGGER.warn("Interruption while TCP socket restart. Error message: {}", e.toString());
                }
            } finally {
                try {
                    if (server != null) {
                        server.close();
                    }
                    if (serverSocket != null) {
                        serverSocket.close();
                    }
                } catch (IOException e) {
                    LOGGER.warn("Exception closing TCP socket. Error message: {}", e.toString());
                }
            }
        }

    }

    private void listenEventsAndPublishLogs(DataOutputStream out) throws InterruptedException, IOException {
        while (true) {
            Thread.sleep(POPULATION_INTERVAL);
            out.writeUTF(HEARTBEAT_MESSAGE);
            while (eventsQueue.size() > 0) {
                TcpSocketDataPopulationEvent event = eventsQueue.pollLast();
                Iterator<PowerConsumption> logsIter = ConsumptionLogsGeneratorFactory.create(
                        event.getStartTimestamp(), event.getStartId(), event.getTransformers(), event.getNumberOfMessages());
                while (logsIter.hasNext()) {
                    out.writeUTF(logsIter.next().toLineFormat() + "\n");
                }
            }
        }
    }

    @EventListener
    public void blogModified(TcpSocketDataPopulationEvent blogModifiedEvent) {
        eventsQueue.push(blogModifiedEvent);
    }
}
