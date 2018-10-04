package Util;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个工具类,用以数据库的编号和名称的快速转换,如将部门名称转换为部门编号
 * Create by JerryZheng
 * 温州商学院 2018-9-26
 */
public class NoName {
    private ArrayList<NoNameItem> noName=new ArrayList<>();//记录编号和名称的对应关系
    private String noCol;
    private String nameCol;
    private String tableName;
    private SqlFunction sqlFunction = new SqlFunction();
    public NoName(String noCol,String nameCol,String tableName){
        this.nameCol=nameCol;
        this.noCol=noCol;
        this.tableName=tableName;
        reset();
    }

    /**
     * 重置对应列表,当数据库的对应关系发生变化时调用
     */
    public void reset(){
        noName.clear();
        String sql="SELECT "+noCol+", "+nameCol+" FROM "+tableName;
        ResultSet resultSet = sqlFunction.doSqlSelect(sql,new String[]{},false);
        try {
            while (resultSet.next()){
                NoNameItem noNameItem = new NoNameItem();
                noNameItem.no=resultSet.getString(1).trim();
                noNameItem.name=resultSet.getString(2).trim();
                noName.add(noNameItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将编号转换为名称
     * @param number 编号
     * @return 名称
     */
    public String getName(String number){
        number=number.trim();
        for (NoNameItem noNameItem : noName
             ) {
            if (noNameItem.no.equals(number)){
                return noNameItem.name;
            }
        }
        return null;
    }

    /**
     * 将名称转换为编号
     * @param name 名称
     * @return 编号
     */
    public String getNo(String name){
        name=name.trim();
        for (NoNameItem noNameItem : noName
                ) {
            if (noNameItem.name.equals(name)){
                return noNameItem.no;
            }
        }
        return null;
    }

    /**
     * 获取所有编号
     * @return 所有编号
     */
    public ArrayList<String> getAllNo() {
        ArrayList<String> no = new ArrayList<>();
        for (NoNameItem n : noName
                ) {
            no.add(n.no);
        }
        return  no;
    }

    /**
     * 获取所有名称
     * @return 所有名称
     */
    public ArrayList<String> getAllName() {
        ArrayList<String> name = new ArrayList<>();
        for (NoNameItem n : noName
                ) {
            name.add(n.name);
        }
        return  name;
    }

    class NoNameItem{
        String no;
        String name;
    }
}
