package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "members")
@JsonIgnoreProperties({ "password", "rol", "passwordConfirm", "hibernateLazyInitializer", "doneEvents",
        "allTeamsOfMemberSe", "enabled", "accountNonExpired", "credentialsNonExpired", "username", "authorities",
        "authority", "accountNonLocked"})
public class Member implements UserDetails {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId = 0;

    @JsonProperty("name")
    private String memberName;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String rol;

    @Transient
    @JsonIgnore
    private String passwordConfirm;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "doneByMember")
    private List<Event> doneEvents;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "team_membername", joinColumns = @JoinColumn(name = "membername_member_id"), inverseJoinColumns = @JoinColumn(name = "team_team_id"))
    private Set<Team> allTeamsOfMemberSet = new HashSet<>();

    @JsonIgnore
    public Set<Team> getAllTeamsOfMemberSet() {
        return allTeamsOfMemberSet;
    }

    public void setAllTeamsOfMemberSet(Set<Team> allTeamsOfMemberSet) {
        this.allTeamsOfMemberSet = allTeamsOfMemberSet;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getMemberName();
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

    public String getMemberName() {
        return memberName;
    }

    public String setMemberName(String memberName) {
        if (memberName != null && memberName.isEmpty()) {
            return null;
        } else {
            return this.memberName = memberName;
        }
    }

    public List<Event> getDoneEvents() {
        return doneEvents;
    }

    public void setDoneEvents(List<Event> doneEvents) {
        this.doneEvents = doneEvents;
    }

    public void removeTeamFromMember(Team team){
        allTeamsOfMemberSet.remove(team);
    }
}