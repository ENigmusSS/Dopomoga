package ua.holovchenko.dopomoga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.holovchenko.dopomoga.entity.MilitaryContact;

public interface MilitaryContactRepository extends JpaRepository<MilitaryContact, String> {
}