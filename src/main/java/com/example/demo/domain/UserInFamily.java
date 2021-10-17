package com.example.demo.domain;

import com.example.demo.domain.IdClasses.UserInFamilyIdClass;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;

@Entity
@Table(name = "users_in_families")
@IdClass(UserInFamilyIdClass.class)
public class UserInFamily implements Serializable {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Id
    @Column(name = "family_id")
    private int familyId;

    @Column(name = "role_id")
    private int roleId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "users", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "families", referencedColumnName = "id")
    private Family family;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles", referencedColumnName = "id")
    private Role role;

    public UserInFamily(){}

    public UserInFamily(User user, Family family) {
        this.user = user;
        this.family = family;
        userId = user.getId();
        familyId = family.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
        this.roleId = role.getId();
    }

    public HashMap<String, Object> getJson(){
        return new HashMap<>(){{
           put("userId", userId);
           put("familyId", familyId);
           put("user", user.getJson());
           put("family", family.getJson(false));
        }};
    }
}
