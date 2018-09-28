package Util;
import java.sql.*;

public class SqlFunction {
    private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String link = "jdbc:sqlserver://10.211.55.3:1433;DatabaseName=Publisher";
    private String userName = "sa";
    private String passWord = "123456";
    private Connection connection= null;
    private PreparedStatement preparedStatement = null;
    private int x = 0;
    private ResultSet resultSet=null;
    public ResultSet doSqlSelect(String sqlLanguage , String[] psString,boolean isLike){
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
    
    public int doSqlUpdate(String sqlLanguage , String[] psString){
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
    

    void closeAllLink(){
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

