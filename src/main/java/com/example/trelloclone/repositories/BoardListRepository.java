package com.example.trelloclone.repositories;

import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {

    public List<BoardList> getBoardListByBoard(Board board);
}
