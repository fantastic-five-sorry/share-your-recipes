package com.fantasticfour.shareyourrecipes.votes;

import com.fantasticfour.shareyourrecipes.account.Utils;
import com.fantasticfour.shareyourrecipes.votes.dtos.NewReplyCommentDto;
import com.fantasticfour.shareyourrecipes.votes.dtos.ReplyCommentDto;
import com.fantasticfour.shareyourrecipes.votes.services.ReplyCommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reply")
public class ReplyCommentController {
    
    final private ReplyCommentService  replyCommentService;

    @Autowired
    public ReplyCommentController(ReplyCommentService replyCommentService) {
        this.replyCommentService = replyCommentService;
    }


    @GetMapping("/{idParent}")
    public ResponseEntity<?> getReplyComments(Pageable pageable, @PathVariable("idParent") Long idParent) {
        
        try {
            return ResponseEntity.ok().body(replyCommentService.findReplyComment(idParent, pageable));
        } catch (Exception e) {
            //TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> replyToComment(@RequestBody NewReplyCommentDto newReplyCommentDto, Authentication authentication) {
        try {
            Long uid = Utils.getIdFromRequest(authentication).orElseThrow(() -> new IllegalStateException("user not found"));
            newReplyCommentDto.setWriterId(uid);
            ReplyCommentDto replyDto = replyCommentService.addReplyComment(newReplyCommentDto);
            return ResponseEntity.ok().body(replyDto);
        } catch (Exception e) {
            //TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable("replyId") Long replyId) {
        try {
            replyCommentService.deleteReplyComment(replyId);
            return ResponseEntity.ok().body("delete success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
    }

}
