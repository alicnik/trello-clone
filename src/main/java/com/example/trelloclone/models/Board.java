package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

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
    @Column(name = "id")
    @GeneratedValue(generator = "UUIDv4")
    @GenericGenerator(name = "UUIDv4", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private String boardName;

    private String description;
    private String background;
    private String backgroundThumbnail;

    @Column(name = "created", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime created;

    @ManyToOne(
        cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
        },
        optional = false
    )
    @JoinColumn(name = "board_owner")
    @JsonIgnoreProperties({"boards", "cards"})
    private AppUser owner;

    @OrderColumn
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("board")
    private List<BoardList> lists;

    @Column
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("board")
    private List<Card> cards;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinTable(
            name = "starred_boards",
            joinColumns = {@JoinColumn(name = "board_id")},
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties({"starredBoards", "boards"})
    private List<AppUser> starredBy;

}
