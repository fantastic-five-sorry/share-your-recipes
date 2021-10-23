package com.fantasticfour.shareyourrecipes.questionandanswer.controller;

import java.util.List;

import com.fantasticfour.shareyourrecipes.questionandanswer.dto.CreateQuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.QuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService QuestionService;
    @Autowired
    public QuestionController(QuestionService QuestionService) {
        this.QuestionService = QuestionService;
    }

    @GetMapping("/")
    public ResponseEntity<?> Question() {
        List<QuestionDTO> QuestionDTOs = QuestionService.findAll();
        System.out.println(QuestionDTOs.size());
        if (QuestionDTOs.size() > 0) {
            return new ResponseEntity<List<QuestionDTO>>(QuestionDTOs, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("error : " + "list is empty");
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateQuestionDTO QuestionDTO) {
        try {
            QuestionService.createQuestion(QuestionDTO);   
        } catch (Exception e) {
            //TODO: handle exception
            return ResponseEntity.badRequest().body("error : "  + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "add Question success");
    }

    @DeleteMapping("/{idQuestion}")
    public ResponseEntity<?> delete(@PathVariable("idQuestion") Long idQuestion) {
        try {
            QuestionService.deleteQuestion(idQuestion);
        } catch (Exception e) {
            //TODO: handle exception
            return ResponseEntity.badRequest().body("error : "  + e.getMessage());
        }

        return ResponseEntity.ok().body("message: " + "delete Question success");
    } 

    @GetMapping("/{idQuestion}")
    public ResponseEntity<?> findById(@PathVariable("idQuestion") Long idQuestion) {
        QuestionDTO QuestionDTO;
        try {
            QuestionDTO = QuestionService.viewQuestionDTO(idQuestion);
        } catch (Exception e) {
            //TODO: handle exception
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return new ResponseEntity<QuestionDTO>(QuestionDTO, HttpStatus.OK);
    }


}

