package com.eryce.sportsclub.constants;

import static com.eryce.sportsclub.constants.Roles.*;

public class Authorize {
    public static final String HAS_MANAGER_ROLE = "hasAuthority('" + MANAGER + "')";
    public static final String HAS_MEMBER_ROLE = "hasAuthority('" + MEMBER + "')";
    public static final String HAS_COACH_OR_MANAGER_ROLE = "hasAnyAuthority('" + COACH + "','" + MANAGER + "')";
    public static final String HAS_MANAGER_OR_MEMBER_ROLE = "hasAnyAuthority('" + MEMBER + "','" + MANAGER + "')";
    public static final String HAS_ANY_ROLE = "hasAnyAuthority('" + COACH + "','" + MANAGER + "','" + MEMBER + "')";
}
