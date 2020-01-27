package ru.spbu.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.inventory.model.Location;

@Transactional(readOnly = true)
public interface CrudLocationRepository extends JpaRepository<Location, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Location l WHERE l.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT l FROM Location l WHERE l.id=:id")
    Location get(@Param("id") int id);
}
