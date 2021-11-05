package com.fantasticfour.shareyourrecipes.questionandanswer.service;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.Question;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.CreateQuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.QuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.UpdateQuestionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    Page<QuestionDTO> findAll(Pageable pageable);
    Page<QuestionDTO> findByStatus(String status, Pageable pageable);
    Page<QuestionDTO> findQuestionApproved(Pageable pageable);
    void createQuestion(CreateQuestionDTO createQuestionDTO) throws Exception ;
    void deleteQuestion(Long id) throws Exception;
    void updateQuestion(UpdateQuestionDTO dto) throws Exception;
    void approved(Long idQuestion) throws Exception;
    void deApproved(Long idQuestion) throws Exception;
    Question findById(Long id) ;
    Question findQuestionApprovedById(Long id);
    QuestionDTO viewQuestionDTO(Long id)  throws Exception;
    QuestionDTO getQuestionBySlug(String slug);
}
