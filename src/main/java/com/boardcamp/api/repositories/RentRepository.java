package com.boardcamp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.boardcamp.api.models.RentModel;

@Repository
public interface RentRepository extends JpaRepository<RentModel, Long> {
    @Query(value = "SELECT count(*) FROM rentals WHERE rentals.game_id = :gameId AND rentals.return_date IS NULL", nativeQuery = true)
    int countByAvaiableGameId(@Param("gameId") Long gameId);
}
