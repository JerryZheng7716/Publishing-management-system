package Util;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 保存登录信息
 */
public class LoginInfo {
    private static boolean isLogin = false;
    private static String loginNo = "";
    private static String deptNo = "";
    private static String loginName = "";
    private static String loginAuthority = "0000000";//8位数字，8个权限，0代表没有权限，1代表可读取/查看，2代表可以查看可以操作
    //图书印刷权限，图书库存管理权限，图书销售管理权限，基本信息管理权限，查询统计权限，系统维护权限，权限管理权限,审核权限
    private static String qx图书印刷 = "0";
    private static String qx图书库存管理 = "0";
    private static String qx图书销售管理 = "0";
    private static String qx基本信息管理 = "0";
    private static String qx查询统计 = "0";
    private static String qx系统维护 = "0";
    private static String qx权限管理 = "0";
    private static String qx审核 = "0";


    public static String getLoginAuthority() {
        return loginAuthority;
    }

    public static void setLoginAuthority(String loginAuthority) {
        LoginInfo.loginAuthority = loginAuthority;
        String[] s = new String[8];
        for (int i = 0; i < s.length; i++) {
            s[i] = loginAuthority.substring(i, i + 1);
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

    public static String getLoginName() {
        return loginName;
    }

    public static void setLoginName(String loginName) {
        LoginInfo.loginName = loginName;
    }

    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        LoginInfo.isLogin = isLogin;
    }

    public static String getLoginNo() {
        return loginNo;
    }

    public static void setLoginNo(String loginNo) {
        LoginInfo.loginNo = loginNo;
    }

    public static String getDeptNo() {
        return deptNo;
    }

    public static void setDeptNo(String deptNo) {
        LoginInfo.deptNo = deptNo;
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

    /**
     * 执行登录操作
     * @param AdminName 用户名
     * @param AdminPassWord 密码
     * @return 登录结果
     */
    public static String Login(String AdminName,String AdminPassWord,boolean isPressModel){
        if (StringUtil.isEmpty(AdminName) || StringUtil.isEmpty(AdminPassWord)) {
            JOptionPane.showMessageDialog(null, "工号或密码不能为空！");
            return "CanNotNull";
        } else {
            AdminPassWord=SHA1.encode(AdminPassWord);
            String sqlLanguage;
            if (isPressModel){
                sqlLanguage = "SELECT * FROM Employee WHERE empNo=? and empPwd=?";
            }else {
                sqlLanguage = "SELECT * FROM Sellers WHERE selNo=? and selPwd=?";
            }

            String[] psString = { AdminName, AdminPassWord };
            ResultSet resultSet=null;
            resultSet=SqlFunction.doSqlSelect(sqlLanguage,psString,false);
            try {
                if (resultSet.next()) {
                    return "Success";
                } else {
                    if (isPressModel){
                        sqlLanguage = "SELECT * FROM Employee WHERE empNo = ?";
                    }else {
                        sqlLanguage = "SELECT * FROM Sellers WHERE selNo = ?";
                    }
                    psString = new String[]{ AdminName};
                    if (SqlFunction.doSqlSelect(sqlLanguage,psString,false).next()) {
                        JOptionPane.showMessageDialog(null,"密码错误！");
                        return "passwordError";
                    }
                    JOptionPane.showMessageDialog(null,"不存在当前工号！");
                    return "NoneAccount";
                }
            }  catch (SQLException e) {
                e.printStackTrace();
            }
            SqlFunction.closeAllLink();
        }
        return "Error";
    }
}
