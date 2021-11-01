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
import com.fantasticfour.shareyourrecipes.account.Utils;
import com.fantasticfour.shareyourrecipes.domains.Question;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.QuestionStatus;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.CreateQuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.QuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.UpdateQuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.repository.AnswerRepo;
import com.fantasticfour.shareyourrecipes.questionandanswer.repository.QuestionRepo;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final AnswerRepo answerRepo;
    private final UserRepo userRepo;
    private final QuestionRepo questionRepo;

    @Value("${lvl.app.maxSlugRandomStringLength}")
    private int SHORT_ID_LENGTH;

    private final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public String toSlug(String input) {
        String shortId = RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH);
        input = input + " " + shortId;
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return normalized.toLowerCase(Locale.ENGLISH);
    }

    @Autowired
    public QuestionServiceImpl(AnswerRepo answerRepo, UserRepo userRepo, QuestionRepo questionRepo) {
        this.answerRepo = answerRepo;
        this.userRepo = userRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public Page<QuestionDTO> findAll(Pageable pageable) {
        return questionRepo.findAll(pageable).map(QuestionDTO::new);
    }

    @Override
    public void createQuestion(CreateQuestionDTO createQuestionDTO) throws Exception {
        Question question = new Question();
        question.setTitle(createQuestionDTO.getTitle());
        question.setContent(createQuestionDTO.getContent());
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
        Question question = this.findById(id);
        // if (question == null) {
        // throw new Exception("not found creator");
        // }
        question.setDeleted(true);
        questionRepo.save(question);

    }

    @Override
    public Question findById(Long id) {
        Question question = questionRepo.findById(id)
                .orElseThrow(() -> new IllegalStateException("question not found"));
        return question;
    }

    @Override
    public QuestionDTO viewQuestionDTO(Long id) throws Exception {
        Question question = this.findById(id);
        // if (question == null) {
        // throw new Exception("not found creator");
        // }
        return new QuestionDTO(question);
    }

    @Override
    public void updateQuestion(UpdateQuestionDTO dto) throws Exception {
        Question question = this.findQuestionApprovedById(dto.getId());
        // if (question == null) {
        // throw new Exception("not found question");

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

    @Override
    public Question findQuestionApprovedById(Long id) {
        Question question = questionRepo.findQuestionApprovedById(id)
                .orElseThrow(() -> new IllegalStateException("question not found"));
        return question;
    }

    @Override
    public void approved(Long idQuestion) throws Exception {
        Question question = this.findById(idQuestion);
        if (question.getStatus() == QuestionStatus.PENDING) {
            question.setStatus(QuestionStatus.APPROVED);
            questionRepo.save(question);

        } else {
            throw new Exception("The question has already been approved");
        }

    }

    @Override
    public void deApproved(Long idQuestion) throws Exception {
        Question question = this.findById(idQuestion);
        if (question.getStatus() == QuestionStatus.APPROVED) {
            question.setStatus(QuestionStatus.PENDING);
            questionRepo.save(question);

        } else {
            throw new Exception("The question has already been pending");
        }

    }

    @Override
    public Page<QuestionDTO> findByStatus(String status, Pageable pageable) {
        return questionRepo.findByStatus(status, pageable).map(QuestionDTO::new);
    }

    @Override
    public QuestionDTO getQuestionBySlug(String slug) {
        Question question = questionRepo.findBySlug(slug)
                .orElseThrow(() -> new IllegalStateException("question not found"));
        return new QuestionDTO(question);
    }

}
