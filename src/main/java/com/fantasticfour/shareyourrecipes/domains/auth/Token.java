package com.fantasticfour.shareyourrecipes.domains.auth;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

@Entity
@Table(name = "tokens", schema = "public")
public class Token {
    @SequenceGenerator(name = "token_sequence", sequenceName = "token_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_sequence")
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime expiresAt;

    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime tokenUsedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ETokenPurpose purpose;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return this.expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getTokenUsedAt() {
        return this.tokenUsedAt;
    }

    public void setTokenUsedAt(LocalDateTime tokenUsedAt) {
        this.tokenUsedAt = tokenUsedAt;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ETokenPurpose getPurpose() {
        return this.purpose;
    }

    public void setPurpose(ETokenPurpose purpose) {
        this.purpose = purpose;
    }

    public Token() {
    }

    public Token(String token, LocalDateTime createdAt, LocalDateTime expiresAt, LocalDateTime tokenUsedAt, User user,
            ETokenPurpose purpose) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.tokenUsedAt = tokenUsedAt;
        this.user = user;
        this.purpose = purpose;
    }

}
