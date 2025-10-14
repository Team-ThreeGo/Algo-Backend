package com.threego.algo.study.command.application.service;

import com.threego.algo.study.command.application.dto.create.StudyPostCreateDTO;
import com.threego.algo.study.command.application.dto.create.StudyPostCreateResponseDTO;
import com.threego.algo.study.command.application.dto.create.StudyPostRequestDTO;
import com.threego.algo.study.command.application.dto.create.StudyReportCreateDTO;
import com.threego.algo.study.command.application.dto.update.StudyPostUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudyPostService {

    /* 설명. 게시물 작성 */
    StudyPostCreateResponseDTO createPost(StudyPostRequestDTO requestDto);

    /* 설명. 게시물 수정 */
    ResponseEntity<String> updatePost(int postId, int memberId, StudyPostUpdateDTO request);

    /* 설명. 게시물 삭제 */
    ResponseEntity<String> deletePost(int postId, int memberId);

    /* 설명. 관리자 게시물 삭제 */
    ResponseEntity<String> adminDeletePost(int postId, int adminId);
}
