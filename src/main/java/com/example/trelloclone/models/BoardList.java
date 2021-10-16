package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lists")
@Data
//@Getter
//@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = true)
    private String title;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "list_board")
    @JsonIgnoreProperties("lists")
    private Board board;

    @Column
    @OneToMany(mappedBy = "boardList", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("boardList")
    private List<Card> cards;

    @Column(name = "position")
    private int position;

    @Column
    private boolean archived;


}
