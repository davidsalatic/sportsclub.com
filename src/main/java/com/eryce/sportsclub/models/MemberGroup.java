package com.eryce.sportsclub.models;

import javax.persistence.*;

@Entity
public class MemberGroup{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberGroupId;

    private String name;

    public MemberGroup()
    {

    }

    public MemberGroup(String name)
    {
        this.name=name;
    }

    public MemberGroup(int id,String name) {
        this.name = name;
        this.memberGroupId=id;
    }

    public Integer getId() {
        return memberGroupId;
    }

    public void setMemberGroupId(Integer memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
