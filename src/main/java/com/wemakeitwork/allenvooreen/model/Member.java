package com.wemakeitwork.allenvooreen.model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "members")
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId = 0;

    //@Column (name = "membername", unique = true, nullable = false)
    private String membername;

    //@Column(name = "password", nullable = false)
    private String password;

    //@Column(name = "rol")
    private String rol;

    @ManyToMany(cascade = CascadeType.ALL)
    // @ManyToMany(cascade = CascadeType.ALL)
    // @JoinTable(name = "team_member", joinColumns = @JoinColumn(name = "teamMemberId"), inverseJoinColumns = @JoinColumn(name = "teamId"))
    private Set<Team> teamName = new HashSet<>();

    public Set<Team> getTeamName() {
        return teamName;
    }

    public void setTeamName(Set<Team> teamName) {
        this.teamName = teamName;
    }

    public String getRol() {
        return rol;
    }

    @ManyToMany
    @JoinTable(name = "member_team", joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getMembername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        if (password != null && password.isEmpty()) {
            return null;
        } else {
            return this.password = password;
        }
    }

    public String getMembername() {
        return membername;
    }

    public String setMembername(String membername) {
        if (membername != null && membername.isEmpty()) {
            return null;
        } else {
            return this.membername= membername;
        }
    }

//    @Override
//    public String toString() {
//        return "Member{" +
//                "memberId=" + memberId +
//                ", membername='" + membername + '\'' +
//                ", password='" + password + '\'' +
//                ", rol='" + rol + '\'' +
//                ", teamName=" + teamName +
//                '}';
//    }
}