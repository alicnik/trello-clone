package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lists")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "list_board")
    private Board board;

    @Column
    @OneToMany(mappedBy = "boardList", cascade = CascadeType.ALL)
    private List<Card> cards;

    @Column(name = "position")
    private int position;

    @Column
    private boolean archived;


}
