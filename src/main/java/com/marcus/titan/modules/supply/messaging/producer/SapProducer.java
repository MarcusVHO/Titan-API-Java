package com.marcus.titan.modules.supply.messaging.producer;

import com.marcus.titan.infra.messaging.RabbitConfig;
import com.marcus.titan.modules.supply.dto.request.MaterialRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class SapProducer {

    private final RabbitTemplate rabbitTemplate;

    public SapProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendMaterialRequest (MaterialRequest request) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.EXECUTE_ROUTING_KEY,
                request
        );
    }
}
