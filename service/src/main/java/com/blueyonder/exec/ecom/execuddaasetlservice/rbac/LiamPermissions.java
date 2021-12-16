package com.blueyonder.exec.ecom.execud-daas-etl.rbac;

public class LiamPermissions {
    public static final String MANAGE = "@liam.hasPermission('by.ldf.starter.manage')";
    public static final String READ = "@liam.hasAnyPermission('by.ldf.starter.manage', 'by.ldf.starter.view')";

    private LiamPermissions() {
    }
}
