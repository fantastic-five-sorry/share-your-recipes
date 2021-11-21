package com.fantasticfour.shareyourrecipes.votes.services;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.Comment;
import com.fantasticfour.shareyourrecipes.domains.ReplyComment;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.votes.dtos.NewReplyCommentDto;
import com.fantasticfour.shareyourrecipes.votes.dtos.ReplyCommentDto;
import com.fantasticfour.shareyourrecipes.votes.repos.CommentRepo;
import com.fantasticfour.shareyourrecipes.votes.repos.ReplyCommentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ReplyCommentImpl  implements ReplyCommentService {

    final private ReplyCommentRepo replyCommentRepo;
    final private UserRepo userRepo;
    final private CommentRepo commentRepo;

    @Autowired
    public ReplyCommentImpl(ReplyCommentRepo replyCommentRepo, UserRepo userRepo, CommentRepo commentRepo) {
        this.replyCommentRepo = replyCommentRepo;
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
    }


    @Override
    public Page<ReplyCommentDto> findReplyComment(Long idParent, Pageable pageable) {
        // TODO Auto-generated method stub
        return replyCommentRepo.findReplyComment(idParent, pageable).map(ReplyCommentDto::new);
    }


    @Override
    public ReplyCommentDto addReplyComment(NewReplyCommentDto newReplyCommentDto) {
        // TODO Auto-generated method stub
        User user = userRepo.findValidUserById(newReplyCommentDto.getWriterId());
        if (user == null) {
            throw new IllegalStateException("User not found");
        }

        Comment commentParent = commentRepo.findById(newReplyCommentDto.getParentId()).orElseThrow(() -> new IllegalStateException("Comment not found"));

        ReplyComment replyComment = new ReplyComment();
        replyComment.setContent(newReplyCommentDto.getContent());
        replyComment.setCreator(user);
        replyComment.setParent(commentParent);
        return new ReplyCommentDto(replyCommentRepo.save(replyComment));
    }


    @Override
    public void deleteReplyComment(Long id) {
        // TODO Auto-generated method stub
        replyCommentRepo.deleteById(id);
    }
    
}
