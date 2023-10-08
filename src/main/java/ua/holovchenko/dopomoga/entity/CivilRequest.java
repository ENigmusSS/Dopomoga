package ua.holovchenko.dopomoga.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "civil_requests")
public class CivilRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column
    private String name;
    @Column(name = "preferential_category")
    private String preferentialCategory;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column
    private String address;
    @Column
    private String needs;
    @Column
    private RequestStatus status;
    @Column(name = "recieved_date")
    private Date recievedDate;
    @Column(name = "last_changed")
    private Date lastChanged;
    @Column
    private String comment;

}
