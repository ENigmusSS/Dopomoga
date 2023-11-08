package ua.holovchenko.dopomoga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.holovchenko.dopomoga.entity.CivilUndercared;

import java.util.Optional;

public interface CivilUndercaredRepository extends JpaRepository<CivilUndercared, String>, JpaSpecificationExecutor<CivilUndercared> {
    Optional<CivilUndercared> findByPassport(String passport);

    Optional<CivilUndercared> findByTaxpayerID(String taxpayerID);

    Optional<CivilUndercared> findByPhoneNumber(String phoneNumber);

}