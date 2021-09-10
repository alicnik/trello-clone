package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cards")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "card_author")
    @JsonIgnoreProperties({"boards", "cards", "cardMemberships"})
    private AppUser author;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "board_cards")
    @JsonIgnoreProperties({"lists", "cards"})
    private Board board;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "card_list")
    @JsonIgnoreProperties({"cards", "board"})
    private BoardList boardList;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinTable(
            name = "card_members",
            joinColumns = {@JoinColumn(name = "card_id")},
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> members;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinTable(
            name = "card_labels",
            joinColumns = {@JoinColumn(name = "card_id")},
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private List<Label> labels;

    @Column
    @OneToMany(mappedBy = "parentCard", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
