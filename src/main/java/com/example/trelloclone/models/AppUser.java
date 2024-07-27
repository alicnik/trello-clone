package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_username",
                        columnNames = "username"
                ),
                @UniqueConstraint(
                        name = "unique_email",
                        columnNames = "email_address"
                )
        }
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AppUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUIDv4")
    @GenericGenerator(name = "UUIDv4", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    @Column
    @OneToMany(mappedBy = "owner", cascade = {
            CascadeType.DETACH,
//            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
//    @JsonIgnoreProperties({"owner"})
    private List<Board> boards;

    @Column
    @OneToMany(mappedBy = "author", cascade = {
            CascadeType.DETACH,
//            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JsonIgnoreProperties("author")
    private List<Card> cards;

    @ManyToMany(
            cascade = {
                    CascadeType.DETACH,
//                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinTable(
            name = "card_members",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    @JsonIgnoreProperties("members")
    private List<Card> cardMemberships = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "starred_boards",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "board_id")
    )
    @JsonIgnoreProperties({"starredBy", "owner"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Board> starredBoards;

    @OrderColumn
    @ManyToMany
    @JsonIgnoreProperties({"owner"})
    @JoinTable(
            name = "recent_boards",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name = "board_id")}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Board> recentBoards;



    public static class UserLogin {
        public String username;
        public String password;
    }
}
