package com.example.trelloclone.repositories;

import com.example.trelloclone.models.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {
}
