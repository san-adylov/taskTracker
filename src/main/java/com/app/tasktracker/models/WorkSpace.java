package com.app.tasktracker.models;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "work_spaces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkSpace {

    @Id
    @GeneratedValue(generator = "workSpace_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "workSpace_gen", sequenceName = "workSpace_seq", allocationSize = 1, initialValue = 31)
    private Long id;

    private String name;

    @Column(name = "admin_id")
    private Long adminId;

    private ZonedDateTime createdDate;

    @ManyToMany(mappedBy = "workSpaces", cascade = {DETACH, REFRESH, MERGE})
    private List<User> members;

    @OneToMany(cascade = {ALL}, mappedBy = "workSpace", orphanRemoval = true)
    private List<Board> boards;

    @OneToMany(cascade = {ALL}, mappedBy = "workSpace")
    private List<Favorite> favorites;

    @OneToMany(cascade = {ALL}, mappedBy = "workSpace", orphanRemoval = true)
    private List<UserWorkSpaceRole> userWorkSpaceRoles;

    public WorkSpace(String name, Long adminId) {
        this.name = name;
        this.adminId = adminId;
    }
}