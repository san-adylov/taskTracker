package com.app.tasktracker.models;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import lombok.*;

import java.time.ZonedDateTime;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(generator = "comment_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comment_gen", sequenceName = "comment_seq", allocationSize = 1, initialValue = 6)
    private Long id;

    private String comment;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Card card;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User member;

    public Comment(String comment, ZonedDateTime now, Card card, User user) {
        this.comment = comment;
        this.createdDate = now;
        this.card = card;
        this.member = user;
    }
}