package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "boards")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "board_sequence")
    private Long id;

    @Column(nullable = false)
    private String boardName;
    private String description;
    private String background;

    @Column(name = "created", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime created;

    @JsonIgnore
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    }, optional = false)
    @JoinColumn(name = "board_owner")
    private User owner;

    @Column
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardList> lists;

    @Column
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Card> cards;

    public static class NewBoard {
        public String username;
        public String boardName;
    }
}
