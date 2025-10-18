package com.threego.algo.member.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IncreasePoint {
    int amount();
    boolean useArgumentMemberId() default true; //false면 포인트 받을 회원의 ID로 인식 (추천용)
    boolean onStatusChangeApproved() default false; // 기업 게시판 승인 시 포인트 지급
}
