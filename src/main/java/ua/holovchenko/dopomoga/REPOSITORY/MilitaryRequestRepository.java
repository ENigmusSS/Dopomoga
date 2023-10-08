package ua.holovchenko.dopomoga.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.holovchenko.dopomoga.Entity.MilitaryRequest;

import java.util.UUID;

public interface MilitaryRequestRepository extends JpaRepository<MilitaryRequest, UUID> {
}