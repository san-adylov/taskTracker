package com.app.tasktracker.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "columns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Column {

    @Id
    @GeneratedValue(generator = "column_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "column_gen", sequenceName = "column_seq", allocationSize = 1, initialValue = 17)
    private Long id;

    private String title;

    @jakarta.persistence.Column(name = "is_archive")
    private Boolean isArchive;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH})
    private List<User> members;

    @OneToMany(cascade = {ALL}, mappedBy = "column")
    private List<Card> cards;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Board board;
}
