package com.fantasticfour.shareyourrecipes.questionandanswer.service;

import java.lang.reflect.Field;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.Question;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.CreateQuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.QuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.UpdateQuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.repository.AnswerRepo;
import com.fantasticfour.shareyourrecipes.questionandanswer.repository.QuestionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService{
    private final AnswerRepo answerRepo;
    private final UserRepo userRepo;
    private final QuestionRepo questionRepo;
    @Autowired 
    public QuestionServiceImpl(AnswerRepo answerRepo, UserRepo userRepo, QuestionRepo questionRepo) {
        this.answerRepo = answerRepo;
        this.userRepo =  userRepo;
        this.questionRepo = questionRepo;
    }

    private final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private  final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public List<QuestionDTO> findAll() {
        // TODO Auto-generated method stub
        return questionRepo.findAll().stream().map(QuestionDTO::new).collect(Collectors.toList());
    }

    @Override
    public void createQuestion(CreateQuestionDTO createQuestionDTO) throws Exception {
        // TODO Auto-generated method stub
        Question question = new Question();
        question.setTitle(createQuestionDTO.getTitle());
        question.setContent(createQuestionDTO.getContent());
        question.setStatus(createQuestionDTO.getStatus());
        question.setSlug(this.toSlug(createQuestionDTO.getTitle()));

        
        User user = userRepo.findValidUserById(createQuestionDTO.getCreatorId());
        if (user == null) {
            throw new Exception("not found creator");
        }
        question.setCreator(user);
        
        
 
        questionRepo.save(question);
    }

    @Override
    public void deleteQuestion(Long id) throws Exception {
        // TODO Auto-generated method stub
        Question question = this.findById(id);
        // if (question == null) {
        //     throw new Exception("not found creator");
        // }
        question.setDeleted(true);
        questionRepo.save(question);
        
    }

    @Override
    public Question findById(Long id) {
        // TODO Auto-generated method stub
        Question question = questionRepo.findById(id).orElseThrow(()-> new IllegalStateException("question not found"));
        return question;
    }

    @Override
    public QuestionDTO viewQuestionDTO(Long id) throws Exception  {
        // TODO Auto-generated method stub
        Question question = this.findById(id);
        // if (question == null) {
        //     throw new Exception("not found creator");
        // }
        return new QuestionDTO(question);
    }

    @Override
    public void updateQuestion(Long id, UpdateQuestionDTO dto) throws Exception {
        // TODO Auto-generated method stub
        Question question = this.findById(id);
        // if (question == null) {
        //     throw new Exception("not found question");
            
        // }

        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(dto) != null) {
                for (Field fieldquestion : question.getClass().getDeclaredFields()) {
                    fieldquestion.setAccessible(true);
                    
                    if (field.getName() == fieldquestion.getName()) {
                        // System.out.println(field.get(dto));
                        // System.out.println(field.get(dto));
                        fieldquestion.set(question, field.get(dto));
                    }
                }
            }

        }
        // System.out.println(question.getPrice());
        questionRepo.save(question);
    }
    
}
