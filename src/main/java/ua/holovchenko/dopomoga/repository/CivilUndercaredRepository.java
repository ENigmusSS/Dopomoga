package ua.holovchenko.dopomoga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.holovchenko.dopomoga.entity.CivilUndercared;

public interface CivilUndercaredRepository extends JpaRepository<CivilUndercared, String> {
    CivilUndercared findByPassport(String passport);
    CivilUndercared findByTaxpayerID(String taxpayerID);

}