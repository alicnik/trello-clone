package com.example.trelloclone.repositories;

import com.example.trelloclone.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {

    List<Card> findCardsByBoardListId(String boardListId);

}
