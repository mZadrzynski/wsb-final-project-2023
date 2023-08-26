package com.wsb.wsbfinalproject2022.authority;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Authority {
    @Id
    @GeneratedValue
    Long id;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    AuthorityName name;
    public Authority(AuthorityName name) {
        this.name = name;
    }
}
