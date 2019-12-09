package com.wemakeitwork.allenvooreen.model;
import javax.persistence.*;

@Entity
@Table(name= "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer memberId;

}
