package com.fantasticfour.shareyourrecipes.questionandanswer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.Answer;
import com.fantasticfour.shareyourrecipes.domains.Question;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.AnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.CreateAnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.repository.AnswerRepo;
import com.fantasticfour.shareyourrecipes.questionandanswer.repository.QuestionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnswerServiceImpl implements AnswerService{

    private final AnswerRepo answerRepo;
    private final UserRepo userRepo;
    private final QuestionRepo questionRepo;
    @Autowired 
    public AnswerServiceImpl(AnswerRepo answerRepo, UserRepo userRepo, QuestionRepo questionRepo) {
        this.answerRepo = answerRepo;
        this.userRepo =  userRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public List<AnswerDTO> findAll() {
        // TODO Auto-generated method stub
        List<Answer> answers =  answerRepo.findAll();
        
        return answers.stream().map(AnswerDTO::new).collect(Collectors.toList());
    }

    @Override
    public void createAnswer(CreateAnswerDTO createAnswerDTO) {
        // TODO Auto-generated method stub
        Answer answer = new Answer();
        answer.setAnswerer(userRepo.findValidUserById(createAnswerDTO.getAnswererId()));
        answer.setQuestion(questionRepo.findById(createAnswerDTO.getQuestionId()).get());
        answer.setContent(createAnswerDTO.getContent());

    }

    @Override
    public void deleteAnswer(Long id) throws Exception {
        // TODO Auto-generated method stub
        Answer answer = this.findById(id);
        if (answer == null) {
            throw new Exception("not found answer");
        }
        answer.setDeleted(true);
        answerRepo.save(answer);
        
    }

    @Override
    public Answer findById(Long id) {
        // TODO Auto-generated method stub
        Answer answer = answerRepo.findById(id).orElse(null);
        return answer;
    }

    @Override
    public AnswerDTO viewAnswerDTO(Long id) throws Exception {
        // TODO Auto-generated method stub
        Answer answer = this.findById(id);
        if (answer == null) {
            throw new Exception("not found answer");
        }
        return new AnswerDTO(answer);
    }

    
}
