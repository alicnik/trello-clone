package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Getter
    @Setter
    private Long id;

    @Column(name = "body")
    @Getter @Setter
    private String body;

    @Column(name = "created", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    @Getter @Setter
    private LocalDateTime created;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "comment_author")
    @Getter @Setter
    private AppUser author;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    }, optional = false)
    @JoinColumn(name = "comment_parent_card")
    @Getter @Setter
    private Card parentCard;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinTable(
            name = "card_labels",
            joinColumns = {@JoinColumn(name = "comment_id")},
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    @Getter @Setter
    private List<Card> linkedCards;

}
