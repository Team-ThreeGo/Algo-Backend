package com.threego.algo.study.query.service;

import com.threego.algo.study.query.dao.StudyPostMapper;
import com.threego.algo.study.query.dto.StudyCommentDTO;
import com.threego.algo.study.query.dto.StudyPostDTO;
import com.threego.algo.study.query.dto.StudyPostDetailDTO;
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
    public List<StudyPostDTO> findAllStudyPosts(StudyPostSearchDTO searchDto) {
        return studyPostMapper.selectAllStudyPosts(searchDto);
    }

    @Override
    public StudyPostDetailDTO findStudyPostDetail(Integer postId) {
        return studyPostMapper.selectStudyPostDetail(postId);
    }

    @Override
    public List<StudyCommentDTO> findStudyPostComments(Integer postId) {
        return studyPostMapper.selectStudyPostComments(postId);
    }

    @Override
    public List<StudyPostDTO> findAllHiddenStudyPosts(StudyPostSearchDTO searchDto) {
        return studyPostMapper.selectAllHiddenStudyPosts(searchDto);
    }

    @Override
    public StudyPostDetailDTO findHiddenStudyPostDetail(Integer postId) {
        return studyPostMapper.selectHiddenStudyPostDetail(postId);
    }

    @Override
    public List<StudyCommentDTO> findAllHiddenStudyComments(StudyPostSearchDTO searchDto) {
        return studyPostMapper.selectAllHiddenStudyComments(searchDto);
    }


}
