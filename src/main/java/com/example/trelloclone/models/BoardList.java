package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lists")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonIgnoreProperties("lists")
    private Board board;

    @OrderColumn
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("boardList")
    private List<Card> cards;

    @Column
    private boolean archived;

}
