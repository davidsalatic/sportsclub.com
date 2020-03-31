package com.eryce.sportsclub.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationValues {

    @Value("${values.membershipDeadline}")
    private String membershipDeadline;

    public String getMembershipDeadline() {
        return membershipDeadline;
    }

    public void setMembershipDeadline(String membershipDeadline) {
        this.membershipDeadline = membershipDeadline;
    }
}
