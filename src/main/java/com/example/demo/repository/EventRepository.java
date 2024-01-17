package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e " +
            "WHERE (:name IS NULL OR e.name LIKE %:name%) " +
            "AND (:location IS NULL OR e.location LIKE %:location%) " +
            "AND (:price IS NULL OR e.price = :price)")
    public List<Event> findByCriteria(
            @Param("name") String name,
            @Param("location") String location,
            @Param("price") Double price);

    @Query("SELECT e.participants FROM Event e WHERE e.id = :eventId")
    List<User> findParticipantsByEventId(@Param("eventId") Long eventId);
}




