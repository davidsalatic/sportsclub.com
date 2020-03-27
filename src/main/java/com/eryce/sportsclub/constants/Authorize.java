package com.eryce.sportsclub.constants;

public class Authorize {
    public static final String HAS_MANAGER_ROLE="hasAuthority('"+Roles.MANAGER+"')";
    public static final String HAS_MEMBER_ROLE="hasAuthority('"+Roles.MEMBER+"')";
    public static final String HAS_COACH_OR_MANAGER_ROLE="hasAnyAuthority('"+Roles.COACH+"','"+Roles.MANAGER+"')";
    public static final String HAS_MANAGER_OR_MEMBER_ROLE="hasAnyAuthority('"+Roles.MEMBER+"','"+Roles.MANAGER+"')";
    public static final String HAS_ANY_ROLE="hasAnyAuthority('"+Roles.COACH+"','"+Roles.MANAGER+"','"+Roles.MEMBER+"')";
}
