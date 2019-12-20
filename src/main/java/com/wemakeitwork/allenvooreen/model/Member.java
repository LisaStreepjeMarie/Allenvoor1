package com.wemakeitwork.allenvooreen.model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "members")
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId = 0;
    // @Column(name = "membername", unique = true)
    private String membername;
    // @Column(name = "password")
    private String password;
    // @Column(name = "rol")
    private String rol;

    @ManyToMany
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "member_team", joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))


    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        System.out.println(authorities);
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
    public void setPassword(String password) {
        this.password = password;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
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