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
@Entity
@Table(name = "military_requests")
public class MilitaryRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "codename")
    private MilitaryContact contact;
    @Column(name = "recieved_date")
    private Date receivedDate;
    @Column(name = "last_changed")
    private Date lastChanged;
    @Column
    private RequestStatus status;
    @Column
    private String needs;
    @Column
    private String comment;




}