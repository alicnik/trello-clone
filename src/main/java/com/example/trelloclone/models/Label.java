package com.example.trelloclone.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "labels")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String background;

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
    @JsonIgnoreProperties("labels")
    private List<Card> cards;

}
