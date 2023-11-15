package com.app.tasktracker.models;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import lombok.*;
import com.app.tasktracker.enums.ReminderType;

import java.time.ZonedDateTime;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "estimations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estimation {

    @Id
    @GeneratedValue(generator = "estimation_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "estimation_gen", sequenceName = "estimation_seq", allocationSize = 1,initialValue = 6)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReminderType reminderType;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "due_date")
    private ZonedDateTime finishDate;

    private ZonedDateTime startTime;

    private ZonedDateTime finishTime;

    private ZonedDateTime notificationTime;

    @OneToOne(cascade = {DETACH,MERGE,REFRESH})
    private Card card;


    @OneToOne(cascade = {DETACH,MERGE,REFRESH,REMOVE})
    private Notification notification;
}