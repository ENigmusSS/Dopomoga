package ua.holovchenko.dopomoga.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.holovchenko.dopomoga.Entity.CivilRequest;

import java.util.UUID;

public interface CivilRequestRepository extends JpaRepository<CivilRequest, UUID> {
}