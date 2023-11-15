package com.app.tasktracker.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(generator = "card_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "card_gen",sequenceName = "card_seq",allocationSize = 1, initialValue = 19)
    private Long id;

    private String title;

    private String description;

    @jakarta.persistence.Column(name = "is_archive")
    private Boolean isArchive;

    private LocalDate createdDate;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH})
    private List<User> members;

    @ManyToMany(cascade = {ALL}, mappedBy = "cards")
    private List<Label> labels;

    @OneToMany(cascade = {ALL}, mappedBy = "card")

    private List<Notification> notifications;

    @OneToMany(cascade = {ALL}, mappedBy = "card")
    private List<Attachment> attachments;

    @OneToMany(cascade = {ALL}, mappedBy = "card")
    private List<Comment> comments;

    @OneToMany(cascade = {ALL}, mappedBy = "card", orphanRemoval = true)
    private List<CheckList> checkLists;

    @OneToOne(cascade = {ALL}, mappedBy = "card")
    private Estimation estimation;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Column column;

    private Long creatorId;

    public void addNotification(Notification notification) {

        if (notifications == null) {

            notifications = new ArrayList<>();
        }
        notifications.add(notification);
    }

}
