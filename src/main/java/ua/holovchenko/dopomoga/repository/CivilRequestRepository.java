package ua.holovchenko.dopomoga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.holovchenko.dopomoga.entity.CivilRequest;

import java.util.UUID;

public interface CivilRequestRepository extends JpaRepository<CivilRequest, UUID> {
}