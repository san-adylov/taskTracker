package com.app.tasktracker.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "boards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(generator = "board_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "board_gen", sequenceName = "board_seq", allocationSize = 1, initialValue = 31)
    private Long id;

    private String title;

    @jakarta.persistence.Column(name = "back_ground")
    private String backGround;

    @OneToMany(cascade = {ALL}, mappedBy = "board", orphanRemoval = true)
    private List<Favorite> favorites;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private WorkSpace workSpace;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH})
    private List<User> members;

    @OneToMany(cascade = ALL, mappedBy = "board")
    private List<Column> columns;
}