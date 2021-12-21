package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cards")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class Card {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUIDv4")
    @GenericGenerator(name = "UUIDv4", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name="created")
    @CreatedDate
    private LocalDateTime created;

    @Column(name = "title")
    private String title;

    @Column(length = 5000)
    private String description;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "card_author")
    @JsonIgnoreProperties({"boards", "cards", "cardMemberships", "recentBoards", "starredBoards"})
    private AppUser author;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.REFRESH,
    })
    @JsonIgnoreProperties(value = {"cards", "lists"}, allowSetters = true)
    private Board board;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnoreProperties(value = {"cards", "board"}, allowSetters = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private BoardList boardList;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
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
    @JsonIgnoreProperties("cards")
    private List<Label> labels;

    @Column
    @OneToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JsonIgnoreProperties(value = {"parentCard", "linkedCards"}, allowSetters = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private List<Comment> comments;

//    @ManyToMany(
//            fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.DETACH,
//                    CascadeType.MERGE,
//                    CascadeType.REFRESH
//            })
//    @JoinTable(
//            name = "comment_linked_card",
//            joinColumns = {@JoinColumn(name = "card_id")},
//            inverseJoinColumns = @JoinColumn(name = "comment_id")
//    )
//    private List<Comment> linkedComments;
}
