package ua.holovchenko.dopomoga.repository.criteria;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DateFilter {
    private String columnName;
    private Date columnValue;
}
