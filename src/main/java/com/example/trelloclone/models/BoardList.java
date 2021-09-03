package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lists")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class BoardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "list_board")
    @Getter @Setter
    private Board board;

    @Column
    @OneToMany(mappedBy = "boardList", cascade = CascadeType.ALL)
    @Getter @Setter
    private List<Card> cards;

    @Column(name = "position")
    @Getter @Setter
    private int position;

    @Column
    @Getter @Setter
    private boolean archived;


}
