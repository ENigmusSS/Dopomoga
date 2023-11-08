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
    @ManyToOne
    @JoinColumn(name = "taxpayerID")
    private CivilUndercared requestedBy;
    @Column
    private String needs;
    @Column
    private RequestStatus status;
    @Column(name = "received_date")
    private Date receivedDate;
    @Column(name = "last_changed")
    private Date lastChanged;
    @Column
    private String comment;
}
