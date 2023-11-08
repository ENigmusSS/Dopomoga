package ua.holovchenko.dopomoga.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "military_contacts")
public class MilitaryContact {
    @Id
    @Column
    private String codename;
    @Column(name = "representer_of")
    private String representerOf;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column
    private String contact;
    @ToString.Exclude
    @Column(name = "request_list")
    @OneToMany(mappedBy = "contact")
    private List<MilitaryRequest> requestList;
    @Column(name = "comment")
    private String comment;
}
