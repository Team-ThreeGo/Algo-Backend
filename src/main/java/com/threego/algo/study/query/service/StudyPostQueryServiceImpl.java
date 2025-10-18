package com.threego.algo.study.query.service;

import com.threego.algo.study.exception.StudyExceptions.StudyPostNotFoundException;
import com.threego.algo.study.query.dao.StudyPostMapper;
import com.threego.algo.study.query.dto.StudyCommentDTO;
import com.threego.algo.study.query.dto.StudyPostDTO;
import com.threego.algo.study.query.dto.StudyPostDetailDTO;
import com.threego.algo.study.query.dto.StudyPostListResponseDTO;
import com.threego.algo.study.query.dto.StudyPostSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyPostQueryServiceImpl implements StudyPostQueryService {

    private final StudyPostMapper studyPostMapper;

    @Override
    public StudyPostListResponseDTO findAllStudyPosts(StudyPostSearchDTO searchDto) {
        List<StudyPostDTO> posts = studyPostMapper.selectAllStudyPosts(searchDto);
        long totalCount = studyPostMapper.countAllStudyPosts(searchDto);

        return StudyPostListResponseDTO.builder()
                .posts(posts)
                .totalCount(totalCount)
                .build();
    }

    @Override
    public StudyPostDetailDTO findStudyPostDetail(int postId) {
        StudyPostDetailDTO result = studyPostMapper.selectStudyPostDetail(postId);
        if (result == null) {
            throw new StudyPostNotFoundException();
        }
        return result;
    }

    @Override
    public List<StudyCommentDTO> findStudyPostComments(int postId) {
        // 게시물 존재 여부 확인
        StudyPostDetailDTO post = studyPostMapper.selectStudyPostDetail(postId);
        if (post == null) {
            throw new StudyPostNotFoundException();
        }
        return studyPostMapper.selectStudyPostComments(postId);
    }

    @Override
    public List<StudyPostDTO> findAllHiddenStudyPosts(StudyPostSearchDTO searchDto) {
        return studyPostMapper.selectAllHiddenStudyPosts(searchDto);
    }

    @Override
    public StudyPostDetailDTO findHiddenStudyPostDetail(int postId) {
        StudyPostDetailDTO result = studyPostMapper.selectHiddenStudyPostDetail(postId);
        if (result == null) {
            throw new StudyPostNotFoundException();
        }
        return result;
    }

    @Override
    public List<StudyCommentDTO> findAllHiddenStudyComments(StudyPostSearchDTO searchDto) {
        return studyPostMapper.selectAllHiddenStudyComments(searchDto);
    }


}
