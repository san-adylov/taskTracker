package com.app.tasktracker.models;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "favorites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(generator = "favorite_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "favorite_gen", sequenceName = "favorite_seq", allocationSize = 1, initialValue = 19)
    private Long id;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private Board board;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private WorkSpace workSpace;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User member;
}