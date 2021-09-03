package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(nullable = false)
    @Getter @Setter
    private String username;

    @Column(name = "email_address", nullable = false)
    @Getter @Setter
    private String emailAddress;

    @Column(nullable = false)
    @JsonProperty(access = WRITE_ONLY)
    @Getter @Setter
    private String password;

    @Column
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @Getter @Setter
    private List<Board> boards;

    @Column
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @Getter @Setter
    private List<Card> cards;

    @JsonIgnore
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
    @Getter @Setter
    private List<Card> cardMemberships;

    public static class UserLogin {
        public String username;
        public String password;
    }
}
