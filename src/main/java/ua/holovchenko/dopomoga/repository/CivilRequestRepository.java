package ua.holovchenko.dopomoga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.holovchenko.dopomoga.entity.CivilRequest;
import ua.holovchenko.dopomoga.entity.CivilUndercared;

import java.util.List;
import java.util.UUID;

public interface CivilRequestRepository extends JpaRepository<CivilRequest, UUID>, JpaSpecificationExecutor<CivilRequest> {
}