package ua.holovchenko.dopomoga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.holovchenko.dopomoga.entity.MilitaryRequest;

import java.util.UUID;

public interface MilitaryRequestRepository extends JpaRepository<MilitaryRequest, UUID> {
}