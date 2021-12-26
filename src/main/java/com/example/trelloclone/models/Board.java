package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
            CascadeType.REFRESH,
        },
        optional = false
    )
    @JoinColumn(name = "board_owner")
    @JsonIgnoreProperties({"boards", "cards", "recentBoards", "starredBoards"})
    private AppUser owner;

    @OrderColumn
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JsonIgnoreProperties(value = "board", allowSetters = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private List<BoardList> lists;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
//                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinTable(
            name = "starred_boards",
            joinColumns = {@JoinColumn(name = "board_id")},
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties({"starredBoards", "boards", "recentBoards", "cards"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<AppUser> starredBy;

    @OrderColumn
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE })
    @JsonIgnoreProperties(
            value = {"starredBoards", "boards", "recentBoards", "cards"},
            allowSetters = true,
            allowGetters = true
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private List<AppUser> recentlyViewedBy;

}
