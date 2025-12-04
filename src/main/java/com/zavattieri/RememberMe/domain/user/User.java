package com.zavattieri.RememberMe.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users") //the name of the table in the database
@Entity //indicates that this class is a JPA entity
@Getter //generates getters for all fields
@Setter //generates setters for all fields
@NoArgsConstructor //generates a no-argument constructor
@AllArgsConstructor //generates a constructor with all fields as arguments
@EqualsAndHashCode(of="id") //generates equals and hashCode methods based on the id field

public class User implements UserDetails { //implements UserDetails interface from Spring Security

    @Id //indicates that the id field is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //indicates that the id field is the primary key and its value is generated automatically
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING) //indicates that the role field is an enum and its value is stored as a string in the database, If I don't add, JPA will store the ordinal (0, 1, 2, etc.) of the enum
    private UserRole role; //type Enum (ADMIN or USER)

    public User(String name, String email, String password, UserRole role) { //custom constructor without id to register new users
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
