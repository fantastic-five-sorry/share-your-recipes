package com.fantasticfour.shareyourrecipes.questionandanswer.service;
import java.util.List;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.Answer;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.AnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.CreateAnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.UpdateAnswerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface AnswerService {
    Page<AnswerDTO> findAll(Pageable pageable);
    void createAnswer(CreateAnswerDTO createAnswerDTO);
    void deleteAnswer(Long id) throws Exception;
    void updateAnswer(UpdateAnswerDTO answerDTO) throws Exception;
    Answer findById(Long id);
    AnswerDTO viewAnswerDTO(Long id) throws Exception;

}