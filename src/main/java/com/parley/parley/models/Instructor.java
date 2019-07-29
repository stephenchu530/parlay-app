package com.parley.parley.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;

    @Column(unique = true)
    private String username;
    private String password;

    List<String> classes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "UserRoles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<RoleType> roleTypes = new HashSet<RoleType>();

    public boolean isAdmin() {
        List<String> roleNames = new ArrayList<String>();
        roleTypes.forEach(roleType -> roleNames.add(roleType.getRole()));
        if(roleNames.contains("admin")) {
            return true;
        } else {
            return false;
        }
    }


    //getters


    public UUID getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public List<String> getClasses() {
        return classes;
    }


    //setters

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roleTypes.forEach(roleType -> roles.add(new SimpleGrantedAuthority("role_" + roleType.getRole())));
        return roles;
    }

}
