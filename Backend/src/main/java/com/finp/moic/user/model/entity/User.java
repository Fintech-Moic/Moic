package com.finp.moic.user.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userSeq")
    private Long seq;

    @Column(name = "id", length = 20, unique = true, nullable = false)
    private String id;

    @Column(name = "password", length = 500, unique = false, nullable = false)
    private String pw;

    @Column(name = "name", length = 20, unique = false, nullable = false)
    private String name;
}
