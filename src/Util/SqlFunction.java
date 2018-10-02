package Util;
import java.sql.*;


public class SqlFunction {
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String link = "jdbc:sqlserver://10.211.55.3:1433;DatabaseName=Publisher";
    private static String userName = "sa";
    private static String passWord = "123456";
    private static Connection connection= null;
    private static PreparedStatement preparedStatement = null;
    private static int x = 0;
    private static ResultSet resultSet=null;
    public static ResultSet doSqlSelect(String sqlLanguage , String[] psString,boolean isLike){
        try {
            Class.forName(driver);
            System.out.println("成功加载驱动");
            connection = DriverManager.getConnection(link, userName,passWord);
            System.out.println("成功连接数据库");
            preparedStatement=connection.prepareStatement(sqlLanguage);
            for (int i = 0; i < psString.length; i++) {
            	if(isLike){
            		preparedStatement.setString(i+1, "%"+psString[i]+"%");
            	}else {
            		preparedStatement.setString(i+1, psString[i]);
				}
				
			}
            resultSet = preparedStatement.executeQuery();       
            
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
        return resultSet;
    }
    
    public static int doSqlUpdate(String sqlLanguage , String[] psString){
        try {
            Class.forName(driver);
            System.out.println("成功加载驱动");
            connection = DriverManager.getConnection(link, userName,passWord);
            System.out.println("成功连接数据库");
            preparedStatement=connection.prepareStatement(sqlLanguage);
            for (int i = 0; i < psString.length; i++) {
				preparedStatement.setString(i+1, psString[i]);
			}
            x = preparedStatement.executeUpdate();                      
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
        closeAllLink();
		return x;
    }
    

    static void  closeAllLink(){
        try {
            if (connection!=null){
                connection.close();
            }
            if (preparedStatement!=null){
                preparedStatement.close();
            }if(resultSet!=null){
            	resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}

