package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    @Column
    @OneToMany(mappedBy = "owner", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JsonIgnoreProperties({"owner"})
    private List<Board> boards;

    @Column
    @OneToMany(mappedBy = "author", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JsonIgnoreProperties("author")
    private List<Card> cards;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinTable(
            name = "card_members",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    @JsonIgnoreProperties("members")
    private List<Card> cardMemberships;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinTable(
            name = "starred_boards",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "board_id")
    )
    @JsonIgnoreProperties({"starredBy", "owner"})
    private List<Board> starredBoards;

    public static class UserLogin {
        public String username;
        public String password;
    }
}
