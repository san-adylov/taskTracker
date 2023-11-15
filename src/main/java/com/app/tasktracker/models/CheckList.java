package com.app.tasktracker.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "checkLists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckList {

    @Id
    @GeneratedValue(generator = "checkList_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "checkList_gen", sequenceName = "checkList_seq", allocationSize = 1, initialValue = 6)
    private Long id;

    private String title;

    private int percent;

    @OneToMany(cascade = {MERGE, REFRESH, DETACH, REMOVE}, mappedBy = "checkList", orphanRemoval = true)
    private List<Item> items;

    @ManyToOne(cascade = {MERGE, REFRESH, DETACH})
    private Card card;
}
