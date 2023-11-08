package ua.holovchenko.dopomoga.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "civil_undercared")
public class CivilUndercared {
    @Column(unique = true, nullable = false, updatable = false, name = "passport")
    private String passport;
    @Id
    @Column(name = "taxpayer_id")
    private String taxpayerID;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "birthdate")
    private Date birthDate;
    @Column
    private String settlement;
    @Column
    private String address;
    @Column(name = "last_official_taken_help")
    private Date lastTakenHelp;
    @Column(name = "is_mobile")
    private Boolean isMobile;
    @Column(name = "has_caretaker")
    private Boolean hasCaretaker;
    @Column(name = "is_disabled")
    private Boolean isDisabled;
    @Column(name = "is_elderly")
    private Boolean isElderly;
    @Column(name = "has_many_children")
    private Boolean hasManyChildren;
    @Column(name = "is_incurable")
    private Boolean isIncurable;
    @Column(name = "is_bedridden")
    private Boolean isBedridden;
    @Column(name = "is_lonely")
    private Boolean isLonely;

    @ToString.Exclude
    @OneToMany(mappedBy = "requestedBy")
    private List<CivilRequest> requestList;

}
