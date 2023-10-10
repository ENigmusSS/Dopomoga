package ua.holovchenko.dopomoga.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "civil_undercared")
public class CivilUndercared {
    @Id
    private String passport;
    @Column(unique = true, nullable = false, updatable = false, name = "taxpayer_identification_number")
    private String taxpayerID;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column
    private String settlement;
    @Column
    private String address;
    @Column(name = "is_mobile")
    private boolean isMobile;
    @Column(name = "has_caretaker")
    private boolean hasCaretaker;
    @Column(name = "is_disabled")
    private boolean isDisabled;
    @Column(name = "is_elderly")
    private boolean isElderly;
    @Column(name = "has_many_children")
    private boolean hasManyChildren;
    @Column(name = "is_incurable")
    private boolean isIncurable;
    @Column(name = "is_bedridden")
    private boolean isBedridden;
    @Column(name = "is_lonely")
    private boolean isLonely;
    @Column(name = "last_official_taken_help")
    private Date lastTakenHelp;


}
