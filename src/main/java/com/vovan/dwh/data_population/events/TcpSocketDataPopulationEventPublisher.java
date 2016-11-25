package com.vovan.dwh.data_population.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * Created by Volodymyr Roman on 25.11.2016.
 */
@Component
public class TcpSocketDataPopulationEventPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void publish(TcpSocketDataPopulationEvent event) {
        publisher.publishEvent(event);
    }
}
