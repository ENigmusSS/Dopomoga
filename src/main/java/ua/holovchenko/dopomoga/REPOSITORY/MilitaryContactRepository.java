package ua.holovchenko.dopomoga.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.holovchenko.dopomoga.Entity.MilitaryContact;

public interface MilitaryContactRepository extends JpaRepository<MilitaryContact, String> {
}