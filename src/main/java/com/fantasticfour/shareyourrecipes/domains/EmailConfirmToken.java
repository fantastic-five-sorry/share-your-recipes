// package com.fantasticfour.shareyourrecipes.domains;

// import java.time.LocalDateTime;

// import javax.persistence.*;

// import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

// @Entity
// @Table(schema = "public")
// public class EmailConfirmToken {
//     @SequenceGenerator(name = "confirmation_token_sequence", sequenceName = "confirmation_token_sequence", allocationSize = 1)
//     @Id
//     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_token_sequence")
//     private Long id;

//     @Column(nullable = false)
//     private String token;

//     @Column(nullable = false, columnDefinition = "TIMESTAMP")
//     @Convert(converter = LocalDateTimeConverter.class)

//     private LocalDateTime createdAt;

//     @Column(nullable = false, columnDefinition = "TIMESTAMP")
//     @Convert(converter = LocalDateTimeConverter.class)
//     private LocalDateTime expiresAt;

//     @Column(nullable = true, columnDefinition = "TIMESTAMP")
//     @Convert(converter = LocalDateTimeConverter.class)
//     private LocalDateTime confirmedAt;

//     @ManyToOne
//     @JoinColumn(nullable = false, name = "user_id")
//     private User user;

//     public EmailConfirmToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
//         this.token = token;
//         this.createdAt = createdAt;
//         this.expiresAt = expiresAt;
//         this.user = user;
//     }

//     public Long getId() {
//         return this.id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getToken() {
//         return this.token;
//     }

//     public void setToken(String token) {
//         this.token = token;
//     }

//     public LocalDateTime getCreatedAt() {
//         return this.createdAt;
//     }

//     public void setCreatedAt(LocalDateTime createdAt) {
//         this.createdAt = createdAt;
//     }

//     public LocalDateTime getExpiresAt() {
//         return this.expiresAt;
//     }

//     public void setExpiresAt(LocalDateTime expiresAt) {
//         this.expiresAt = expiresAt;
//     }

//     public LocalDateTime getConfirmedAt() {
//         return this.confirmedAt;
//     }

//     public void setConfirmedAt(LocalDateTime confirmedAt) {
//         this.confirmedAt = confirmedAt;
//     }

//     public User getUser() {
//         return this.user;
//     }

//     public void setUser(User user) {
//         this.user = user;
//     }

//     public EmailConfirmToken() {
//     }

// }
