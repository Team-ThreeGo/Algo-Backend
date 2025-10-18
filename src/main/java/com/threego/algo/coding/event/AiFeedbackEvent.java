package com.threego.algo.coding.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AiFeedbackEvent {
    private final int postId;
    private final String title;
    private final String content;
    private final String problemTitle;
}