package com.example.yeogiserver.event.recommand;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class RecommandEvent extends ApplicationEvent {
    private final Long postId;
    private final Long memberId;

    public RecommandEvent(Object source, Long postId, Long memberId) {
        super(source);
        this.postId = postId;
        this.memberId = memberId;
    }

}
