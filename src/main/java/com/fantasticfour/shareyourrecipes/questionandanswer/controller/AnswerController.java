package com.fantasticfour.shareyourrecipes.questionandanswer.controller;

import java.util.List;

import com.fantasticfour.shareyourrecipes.questionandanswer.dto.AnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.CreateAnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.UpdateAnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.service.AnswerService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    // @GetMapping("/")
    // public ResponseEntity<?> answer(Pageable page) {
    // // Page page =new Page();
    // // page.getP
    // List<AnswerDTO> answerDTOs = answerService.findAll();
    // if (answerDTOs.size() > 0) {
    // return new ResponseEntity<List<AnswerDTO>>(answerDTOs, HttpStatus.OK);
    // }
    // return ResponseEntity.badRequest().body("error : " + "list is empty");
    // }
    @GetMapping("")
    public ResponseEntity<?> answer(Pageable pageable) {
        // List<AnswerDTO> answerDTOs = answerService.findAll();
        // if (answerDTOs.size() > 0) {
        //     return new ResponseEntity<List<AnswerDTO>>(answerDTOs, HttpStatus.OK);
        // }
        // return ResponseEntity.badRequest().body("error : " + "list is empty");
        return ResponseEntity.ok().body(answerService.findAll(pageable));
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateAnswerDTO answerDTO) {
        try {
            answerService.createAnswer(answerDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "add answer success");
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateAnswerDTO answerDTO) {
        try {
            answerService.updateAnswer(answerDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
        return ResponseEntity.ok().body("message : update answer success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            answerService.deleteAnswer(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }

        return ResponseEntity.ok().body("message: " + "delete answer success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        AnswerDTO answerDTO;
        try {
            answerDTO = answerService.viewAnswerDTO(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return new ResponseEntity<AnswerDTO>(answerDTO, HttpStatus.OK);
    }



}
