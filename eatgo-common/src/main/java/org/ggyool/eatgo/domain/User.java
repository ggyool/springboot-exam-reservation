package org.ggyool.eatgo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
    @NotNull
    private Long level;

    public boolean isAdmin(){
        return level >= 100;
    }

    public void update(String email, String name, Long level) {
        this.email = email;
        this.name = name;
        this.level = level;
    }

    public boolean isActive() {
        return level>0L;
    }

    public void deactivate() {
        level = 0L;
    }
}
