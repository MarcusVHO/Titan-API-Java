package com.marcus.titan.modules.supply.messaging.consumer;

import com.marcus.titan.infra.messaging.RabbitConfig;
import com.marcus.titan.modules.supply.dto.message.MaterialResponseMessage;
import com.marcus.titan.modules.supply.service.SupplyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SapListener {
    private final SupplyService supplyService;

    public SapListener(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @RabbitListener(queues = RabbitConfig.RESPONSE_QUEUE)
    public void receive(MaterialResponseMessage message) {
        System.out.println("Received Message: " + message);
        supplyService.processMaterialResponse(message);
    }
}
