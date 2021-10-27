package com.fantasticfour.shareyourrecipes.questionandanswer.service;
import java.util.List;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.Answer;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.AnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.CreateAnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.UpdateAnswerDTO;



public interface AnswerService {
    List<AnswerDTO> findAll();
    void createAnswer(CreateAnswerDTO createAnswerDTO);
    void deleteAnswer(Long id) throws Exception;
    void updateAnswer(long idAnswer, UpdateAnswerDTO  answerDTO) throws Exception;
    Answer findById(Long id);
    AnswerDTO viewAnswerDTO(Long id) throws Exception;
}