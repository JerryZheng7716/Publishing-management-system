package Util;

import javax.swing.*;

/**
 * 保存登录信息
 */
public class LoginInfo {
    private static boolean isLogin = false;
    private static String empNo = "";
    private static String dept = "";
    private static String empName = "";
    private static String empAuthority = "0000000";//8位数字，8个权限，0代表没有权限，1代表可读取/查看，2代表可以查看可以操作
    //图书印刷权限，图书库存管理权限，图书销售管理权限，基本信息管理权限，查询统计权限，系统维护权限，权限管理权限,审核权限
    private static String qx图书印刷 = "2";
    private static String qx图书库存管理 = "2";
    private static String qx图书销售管理 = "2";
    private static String qx基本信息管理 = "2";
    private static String qx查询统计 = "2";
    private static String qx系统维护 = "2";
    private static String qx权限管理 = "2";
    private static String qx审核 = "2";


    public static String getEmpAuthority() {
        return empAuthority;
    }

    public static void setEmpAuthority(String empAuthority) {
        LoginInfo.empAuthority = empAuthority;
        String[] s = new String[8];
        for (int i = 0; i < s.length; i++) {
            s[i] = empAuthority.substring(i, i + 1);
        }
        qx图书印刷 = s[0];
        qx图书库存管理 = s[1];
        qx图书销售管理 = s[2];
        qx基本信息管理 = s[3];
        qx查询统计 = s[4];
        qx系统维护 = s[5];
        qx权限管理 = s[6];
        qx审核 = s[7];
    }
    public static String getQx审核() {
        return qx审核;
    }

    public static String getQx图书印刷() {
        return qx图书印刷;
    }

    public static String getQx图书库存管理() {
        return qx图书库存管理;
    }

    public static String getQx图书销售管理() {
        return qx图书销售管理;
    }

    public static String getQx基本信息管理() {
        return qx基本信息管理;
    }

    public static String getQx查询统计() {
        return qx查询统计;
    }

    public static String getQx系统维护() {
        return qx系统维护;
    }

    public static String getQx权限管理() {
        return qx权限管理;
    }

    public static String getEmpName() {
        return empName;
    }

    public static void setEmpName(String empName) {
        LoginInfo.empName = empName;
    }

    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        LoginInfo.isLogin = isLogin;
    }

    public static String getEmpNo() {
        return empNo;
    }

    public static void setEmpNo(String empNo) {
        LoginInfo.empNo = empNo;
    }

    public static String getDept() {
        return dept;
    }

    public static void setDept(String dept) {
        LoginInfo.dept = dept;
    }

    public static boolean testAuthority(String authority, int needLevel) {
        int au;
        try {
            au = Integer.parseInt(authority);
        }catch (Exception e){
            return false;
        }
        if (au<needLevel) {
            JOptionPane.showMessageDialog(null, "您没有权限访问此功能，请联系管理员");
            return false;
        } else {
            return true;
        }
    }

}
