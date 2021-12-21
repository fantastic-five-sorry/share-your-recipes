package com.fantasticfour.shareyourrecipes.questionandanswer.controller;

import java.util.List;

import com.fantasticfour.shareyourrecipes.account.Utils;
import com.fantasticfour.shareyourrecipes.domains.enums.QuestionStatus;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.CreateQuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.QuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.UpdateQuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService QuestionService) {
        this.questionService = QuestionService;
    }

    @GetMapping("")
    public ResponseEntity<?> Question(Pageable pageable) {
        // List<QuestionDTO> QuestionDTOs = QuestionService.findAll();
        // System.out.println(QuestionDTOs.size());
        // if (QuestionDTOs.size() > 0) {
        // return new ResponseEntity<List<QuestionDTO>>(QuestionDTOs, HttpStatus.OK);
        // }
        // return ResponseEntity.badRequest().body("error : " + "list is empty");
        return ResponseEntity.ok().body(questionService.findAll(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateQuestionDTO questionDTO, Authentication auth) {
        try {
            Long uid = Utils.getIdFromRequest(auth).orElseThrow(() -> new IllegalStateException("user not found"));
            questionDTO.setCreatorId(uid);
            questionService.createQuestion(questionDTO);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "add Question success");
    }

    @PostMapping("/de-approved/{id}")
    public ResponseEntity<?> deApproved(@PathVariable("id") Long idQuestion) {
        try {
            questionService.deApproved(idQuestion);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "update status of question  success");
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approved(@PathVariable("id") Long idQuestion) {
        try {
            questionService.approved(idQuestion);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "update status of question  success");
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateQuestionDTO questionDTO) {
        try {
            questionService.updateQuestion(questionDTO);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }

        return ResponseEntity.ok().body("message: " + "update question success");
    }

    @DeleteMapping("/{idQuestion}")
    public ResponseEntity<?> delete(@PathVariable("idQuestion") Long idQuestion) {
        try {
            questionService.deleteQuestion(idQuestion);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }

        return ResponseEntity.ok().body("message: " + "delete Question success");
    }

    @GetMapping("/{idQuestion}")
    public ResponseEntity<?> findById(@PathVariable("idQuestion") Long idQuestion) {
        QuestionDTO QuestionDTO;
        try {
            QuestionDTO = questionService.viewQuestionDTO(idQuestion);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return new ResponseEntity<QuestionDTO>(QuestionDTO, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> findByStatus(@PathVariable("status") QuestionStatus status, Pageable pageable) {
        try {
            return new ResponseEntity<Page<QuestionDTO>>(questionService.findByStatus(status.toString(), pageable),
                    HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> findQuestionBySlug(@PathVariable("slug") String slug) {
        // Long id = Long.parseLong(idRecipe);

        QuestionDTO questionDTO;
        try {
            questionDTO = questionService.getQuestionBySlug(slug);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return new ResponseEntity<QuestionDTO>(questionDTO, HttpStatus.OK);
    }


    @GetMapping("/getQuestionApproved")
    public ResponseEntity<?> getQuestionApproved(Pageable  pageable) {
        return ResponseEntity.ok().body(questionService.findQuestionApproved(pageable));
    }

}
