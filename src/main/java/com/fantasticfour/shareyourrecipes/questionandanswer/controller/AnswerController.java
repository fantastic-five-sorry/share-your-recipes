package com.fantasticfour.shareyourrecipes.questionandanswer.controller;

import java.util.List;

import com.fantasticfour.shareyourrecipes.questionandanswer.dto.AnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.CreateAnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.service.AnswerService;

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
@RequestMapping("/api/answer")
public class AnswerController {

    private final  AnswerService answerService;
    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/")
    public ResponseEntity<?> answer() {
        List<AnswerDTO> answerDTOs = answerService.findAll();
        if (answerDTOs.size() > 0) {
            return new ResponseEntity<List<AnswerDTO>>(answerDTOs, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("error : " + "list is empty");
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateAnswerDTO answerDTO) {
        try {
            answerService.createAnswer(answerDTO);   
        } catch (Exception e) {
            //TODO: handle exception
            return ResponseEntity.badRequest().body("error : "  + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "add answer success");
    }

    @DeleteMapping("/{idanswer}")
    public ResponseEntity<?> delete(@PathVariable("idanswer") Long idanswer) {
        try {
            answerService.deleteAnswer(idanswer);
        } catch (Exception e) {
            //TODO: handle exception
            return ResponseEntity.badRequest().body("error : "  + e.getMessage());
        }

        return ResponseEntity.ok().body("message: " + "delete answer success");
    } 

    @GetMapping("/{idanswer}")
    public ResponseEntity<?> findById(@PathVariable("idanswer") Long idanswer) {
        AnswerDTO answerDTO;
        try {
            answerDTO = answerService.viewAnswerDTO(idanswer);
        } catch (Exception e) {
            //TODO: handle exception
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return new ResponseEntity<AnswerDTO>(answerDTO, HttpStatus.OK);
    }


}