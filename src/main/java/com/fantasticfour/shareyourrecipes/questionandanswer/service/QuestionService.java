package com.fantasticfour.shareyourrecipes.questionandanswer.service;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.Question;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.CreateQuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.QuestionDTO;

public interface QuestionService {
    List<QuestionDTO> findAll();
    void createQuestion(CreateQuestionDTO createQuestionDTO) throws Exception ;
    void deleteQuestion(Long id) throws Exception;
    Question findById(Long id) ;
    QuestionDTO viewQuestionDTO(Long id)  throws Exception;
}
