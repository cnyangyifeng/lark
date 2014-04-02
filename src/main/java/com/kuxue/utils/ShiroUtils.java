package com.kuxue.utils;import java.util.List;import com.kuxue.model.organization.RoleEnum;import com.kuxue.model.organization.UserRole;import com.kuxue.service.ShiroDbRealm;import org.apache.commons.lang3.StringUtils;import org.apache.shiro.SecurityUtils;import org.apache.shiro.subject.Subject;/** * 用户权限校验工具类 */public class ShiroUtils {    private static final String ROLE_NAMES_DELIMETER = ",";    /**     * 获取人员拥有的最高角色     *     * @param userRole     * @return     */    public static RoleEnum getUserHightRole(List<UserRole> userRole) {        if (userRole == null)            return null;        RoleEnum defaultRole = RoleEnum.default_role;        for (UserRole role : userRole) {            if (role.getRoleEnum().getIndex() > defaultRole.getIndex()) {                defaultRole = role.getRoleEnum();            }        }        return defaultRole;    }    public static boolean checkHasRole(List<UserRole> userRoles,                                       RoleEnum roleEnum) {        if (userRoles == null)            return false;        for (UserRole userRole : userRoles) {            if (userRole.getRoleEnum() == roleEnum)                return true;        }        return false;    }    // jack,sawyer,jacob,kate    public static boolean checkUserName(String username) {        if (StringUtils.isBlank(username))            return false;		if (StringUtils.isNotEmpty(username)) {            return true;        }        		/***		if (username.equals("jack") || username.equals("sawyer")                || username.equals("jacob") || username.equals("kate") || username.equals("admin")) {            return true;        }		***/        return false;    }    /**     * 查看用户是否有最高权限(角色为admin)     *     * @return     */    public static boolean isAdmin() {        Subject currentUser = SecurityUtils.getSubject();        return currentUser.hasRole(RoleEnum.admin.getKey());    }    /**     * 查询用户有某一项权限     *     * @param role     * @return     */    public static boolean hasRole(RoleEnum role) {        Subject currentUser = SecurityUtils.getSubject();        return currentUser.hasRole(role.getKey());    }    public static boolean hasAnyRoles(String roleNames) {        boolean hasAnyRole = false;        Subject subject = getSubject();        if (subject != null) {            for (String role : roleNames.split(ROLE_NAMES_DELIMETER)) {                if (subject.hasRole(role.trim())) {                    hasAnyRole = true;                    break;                }            }        }        return hasAnyRole;    }    public static Subject getSubject() {        try {            return SecurityUtils.getSubject();        } catch (Exception e) {            return null;        }    }    /**     * 查询用户有某一项权限     *     * @param role     * @return     */    public static boolean hasRole(String role) {        Subject currentUser = SecurityUtils.getSubject();        return currentUser.hasRole(role);    }    public static boolean hasSecurity() {        return SecurityUtils.getSubject().getPrincipal() != null;    }    /**     * 获取当前用户的缓存Key     *     * @return     */    public static Object getPrincipal() {        return SecurityUtils.getSubject().getPrincipals();    }    public static ShiroDbRealm.ShiroUser getUser() {        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils                .getSubject().getPrincipal();        return user;    }}