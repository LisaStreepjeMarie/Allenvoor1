package com.wemakeitwork.allenvooreen.model;
import javax.persistence.*;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer memberId;

    @Column(name = "membername", unique = true, nullable = false)
    private String membername;

    @Column(name = "password", nullable = false)
    private String password;

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
        if (password != null && password.isEmpty()) {
            this.password = null;
        } else {
            this.password = password;
        }
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        if (membername != null && membername.isEmpty()) {
            this.membername = null;
        } else {
            this.membername = membername;
        }
    }
}

