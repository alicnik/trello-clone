package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String boardName;

    private String description;
    private String background;

    @Column(name = "created", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime created;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    }, optional = false)
    @JoinColumn(name = "board_owner")
    @JsonIgnoreProperties({"boards", "cards"})
    private AppUser owner;

    @Column
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("board")
    private List<BoardList> lists;

    @Column
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("board")
    private List<Card> cards;

}
