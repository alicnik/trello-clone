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
@Table(name = "boards")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Board {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(nullable = false)
    @Getter @Setter
    private String boardName;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String background;

    @Column(name = "created", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    @Getter @Setter
    private LocalDateTime created;

    @JsonIgnore
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    }, optional = false)
    @JoinColumn(name = "board_owner")
    @Getter @Setter
    private AppUser owner;

    @Column
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @Getter @Setter
    private List<BoardList> lists;

    @Column
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @Getter @Setter
    private List<Card> cards;

    public static class NewBoard {
        public String username;
        public String boardName;
    }
}
