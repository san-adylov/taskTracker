package com.app.tasktracker.models;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(generator = "items_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "items_gen", sequenceName = "items_seq", allocationSize = 1, initialValue = 6)
    private Long id;

    private String title;

    @Column(name = "is_done")
    private Boolean isDone;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    CheckList checkList;
}