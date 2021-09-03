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
@Table(name = "labels")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    @Column
    @Getter
    @Setter
    private String background;

    @JsonIgnore
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            })
    @JoinTable(
            name = "card_labels",
            joinColumns = {@JoinColumn(name = "label_id")},
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    @Getter
    @Setter
    private List<Card> cards;

    @Getter
    @Setter
    private String coverSize;

    @Getter
    @Setter
    private String coverBackground;

    @Column(name = "created", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    @Getter
    @Setter
    private LocalDateTime created;

    @Getter
    @Setter
    int position;

    @Getter
    @Setter
    boolean archived;


}
