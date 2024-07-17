package com.example.yeogiserver.event.recommand;

import com.example.yeogiserver.event.recommand.application.RecommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionTemplate;


@Component
@RequiredArgsConstructor
public class RecommandEventListener {

    private final RecommandService recommandService;
    private final TransactionTemplate transactionTemplate;

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void handleRecommandEvent(RecommandEvent recommandEvent) {
        recommandService.saveRecommand(recommandEvent);

//        transactionTemplate.executeWithoutResult(status -> {
//            try {
//            } catch (Exception e) {
////                status.setRollbackOnly();
//            }
//        });
    }
}
