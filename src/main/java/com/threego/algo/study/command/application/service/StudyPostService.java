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

    StudyPostCreateResponseDTO createPost(StudyPostRequestDTO requestDto);

    ResponseEntity<String> updatePost(int postId, int memberId, StudyPostUpdateDTO request);

    ResponseEntity<String> deletePost(int postId, int memberId);

    ResponseEntity<String> adminDeletePost(int postId, int adminId);
}
