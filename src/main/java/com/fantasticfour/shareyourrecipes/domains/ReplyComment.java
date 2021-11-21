package com.fantasticfour.shareyourrecipes.domains;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.CommentType;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "reply_comments", schema = "public")
public class ReplyComment extends AuditModel {

    @Id
    @GeneratedValue(generator = "reply_comments_generator")
    @SequenceGenerator(name = "reply_comments_generator", sequenceName = "reply_comments_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @Length(max = 65535)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)   
    private Comment parent;

    @Column(nullable = false)
    private Boolean deleted;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private CommentType type;

    public Boolean isDeleted() {
        return this.deleted;
    }

    public CommentType getType() {
        return this.type;
    }

    public void setType(CommentType type) {
        this.type = type;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Comment getParent() {
        return this.parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    

    public ReplyComment() {
        this.deleted = false;
        this.type = CommentType.TEXT;
    }

}
