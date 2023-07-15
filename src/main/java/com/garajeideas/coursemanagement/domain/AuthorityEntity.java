package com.garajeideas.coursemanagement.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "authority")
public class AuthorityEntity implements Serializable {

    @Id
    @Column(name = "name", length = 50)
    private String name;
}
