package Util;

public class LoginInfo {
    private static boolean isLogin=false;
    private static String empNo;
    private static String dept;
    private static String empName;

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
}
