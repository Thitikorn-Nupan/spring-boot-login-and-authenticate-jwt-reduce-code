package com.ttknpdev.reducecode.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String roles;

}
