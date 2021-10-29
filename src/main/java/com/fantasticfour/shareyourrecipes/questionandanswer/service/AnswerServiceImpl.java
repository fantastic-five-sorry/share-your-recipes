package com.fantasticfour.shareyourrecipes.questionandanswer.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.Answer;
import com.fantasticfour.shareyourrecipes.domains.Question;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.AnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.CreateAnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.UpdateAnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.repository.AnswerRepo;
import com.fantasticfour.shareyourrecipes.questionandanswer.repository.QuestionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepo answerRepo;
    private final UserRepo userRepo;
    private final QuestionRepo questionRepo;

    @Autowired
    public AnswerServiceImpl(AnswerRepo answerRepo, UserRepo userRepo, QuestionRepo questionRepo) {
        this.answerRepo = answerRepo;
        this.userRepo = userRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public Page<AnswerDTO> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        // List<Answer> answers =  answerRepo.findAll();
        return answerRepo.findAll(pageable).map(AnswerDTO::new);
        // return answers.stream().map(AnswerDTO::new).collect(Collectors.toList());
    }

    @Override
    public void createAnswer(CreateAnswerDTO createAnswerDTO) {
        // TODO Auto-generated method stub
        Answer answer = new Answer();
        answer.setAnswerer(userRepo.findValidUserById(createAnswerDTO.getAnswererId()));
        answer.setQuestion(questionRepo.findQuestionApprovedById(createAnswerDTO.getQuestionId()).get());
        answer.setContent(createAnswerDTO.getContent());
        answerRepo.save(answer);

    }

    @Override
    public void deleteAnswer(Long id) throws Exception {
        // TODO Auto-generated method stub
        Answer answer = this.findById(id);
        // if (answer == null) {
        //     throw new Exception("not found answer");
        // }
        answer.setDeleted(true);
        answerRepo.save(answer);

    }

    @Override
    public Answer findById(Long id) {
        // TODO Auto-generated method stub
        Answer answer = answerRepo.findById(id).orElseThrow(()-> new IllegalStateException("answer not found"));
        return answer;
    }

    @Override
    public AnswerDTO viewAnswerDTO(Long id) throws Exception {
        // TODO Auto-generated method stub
        Answer answer = this.findById(id);
        // if (answer == null) {
        //     throw new Exception("not found answer");
        // }
        return new AnswerDTO(answer);
    }

    @Override
    public void updateAnswer(UpdateAnswerDTO answerDTO) throws Exception {
        // TODO Auto-generated method stub
        Answer answer = this.findById(answerDTO.getId());
        // if (answer == null) {
        //     throw new Exception("not found answer");
            
        // }

        for (Field field : answerDTO.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(answerDTO) != null) {
                for (Field fieldanswer : answer.getClass().getDeclaredFields()) {
                    fieldanswer.setAccessible(true);
                    
                    if (field.getName() == fieldanswer.getName()) {
                        // System.out.println(field.get(dto));
                        // System.out.println(field.get(dto));
                        fieldanswer.set(answer, field.get(answerDTO));
                    }
                }
            }

        }
        // System.out.println(answer.getPrice());
        answerRepo.save(answer);
    }


}
