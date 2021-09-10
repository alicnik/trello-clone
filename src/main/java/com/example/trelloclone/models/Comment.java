package com.example.trelloclone.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String body;

    @Column(name = "created", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime created;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "comment_author")
    @JsonIgnoreProperties("comments")
    private AppUser author;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    }, optional = false)
    @JoinColumn(name = "comment_parent_card")
    @JsonIgnoreProperties("comments")
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
    @JsonIgnoreProperties("comments")
    private List<Card> linkedCards;

}
