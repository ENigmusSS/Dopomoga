package ua.holovchenko.dopomoga.repository.criteria;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    private String columnName;
    private Object columnValue;
}
