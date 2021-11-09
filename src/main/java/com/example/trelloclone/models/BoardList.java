package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
    @Column(name = "id")
    @GeneratedValue(generator = "UUID2")
    @GenericGenerator(name = "UUID2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

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

    @Column
    private boolean archived;


}
