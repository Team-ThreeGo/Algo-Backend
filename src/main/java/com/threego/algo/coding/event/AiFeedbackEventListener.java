package com.threego.algo.coding.event;

import com.threego.algo.coding.event.AiFeedbackEvent;
import com.threego.algo.coding.command.domain.aggregate.CodingPost;
import com.threego.algo.coding.command.domain.repository.CodingPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiFeedbackEventListener {

    private final CodingPostRepository postRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${coding.fastapi.url}")
    private String fastApiUrl;

    @Async
    @TransactionalEventListener // 트랜잭션 커밋 후 실행됨
    public void handleAiFeedbackEvent(AiFeedbackEvent event) {
        log.info("🚀 비동기 FastAPI 호출 시작 (postId={})", event.getPostId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = Map.of(
                "title", event.getTitle(),
                "content", event.getContent(),
                "problem", event.getProblemTitle()
        );

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    fastApiUrl,
                    new HttpEntity<>(request, headers),
                    Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, String> body = response.getBody();

                CodingPost post = postRepository.findById(event.getPostId())
                        .orElseThrow(() -> new IllegalArgumentException("게시글 없음: " + event.getPostId()));

                post.setAiFeedback(
                        body.get("aiBigO"),
                        body.get("aiGood"),
                        body.get("aiBad"),
                        body.get("aiPlan")
                );

                postRepository.save(post);
                log.info("✅ FastAPI 피드백 저장 완료 (postId={})", event.getPostId());
            } else {
                log.warn("⚠️ FastAPI 피드백 응답 비정상 (postId={}): {}", event.getPostId(), response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("❌ FastAPI 피드백 요청 실패 (postId={}): {}", event.getPostId(), e.getMessage());
        }
    }
}