package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_sequence")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "password", nullable = false)
    private String password;

    @Column
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Board> boards;

    @Column
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
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
    private List<Card> cardMemberships;
}
