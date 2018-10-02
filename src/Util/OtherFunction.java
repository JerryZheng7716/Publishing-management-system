package Util;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class OtherFunction {
	public static void selectTable(String sqlLanguage,String[] psString,JTable jTable1) {
		int count=0;
		((DefaultTableModel) jTable1.getModel()).getDataVector().clear(); //移除原来的数据
		DefaultTableModel defaultTableModel = (DefaultTableModel) jTable1.getModel();
		ResultSet resultSet = null;
		resultSet = SqlFunction.doSqlSelect(sqlLanguage, psString, true);
		try {
			while (resultSet.next()) {
				Vector vector = new Vector();
				for (int i = 0; i < jTable1.getColumnCount(); i++) {
					vector.add(resultSet.getString(i+1));
				}
				defaultTableModel.addRow(vector);
				count++;
			}
			if (count==0) {
				Vector vector = new Vector();
				for (int i = 0; i < jTable1.getColumnCount(); i++) {
					vector.add("NULL");
				}
				defaultTableModel.addRow(vector);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void setComboBoxItem(String sqlLanguage,JComboBox jComboBox1){
		ResultSet resultSet = null;
		String[] psString = {};
		resultSet = SqlFunction.doSqlSelect(sqlLanguage, psString, true);
		try {
			while (resultSet.next()) {
				jComboBox1.addItem(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SqlFunction.closeAllLink();
	}
	
	public static void setComboboxSelect(JComboBox jComboBox1,String selectString) {
		int count = jComboBox1.getItemCount();
		for (int i = 0; i < count; i++) {
			if (jComboBox1.getItemAt(i).equals(selectString)) {
				jComboBox1.setSelectedIndex(i);
				break;
			}
		}
	}

	public static String Login(String AdminName,String AdminPassWord){
		if (StringUtil.isEmpty(AdminName) || StringUtil.isEmpty(AdminPassWord)) {
			JOptionPane.showMessageDialog(null, "工号或密码不能为空！");
			return "CanNotNull";
		} else {
			AdminPassWord=SHA1.encode(AdminPassWord);
			String sqlLanguage = "SELECT * FROM Employee WHERE empNo=? and empPwd=?";
			String[] psString = { AdminName, AdminPassWord };
			ResultSet resultSet=null;
			resultSet=SqlFunction.doSqlSelect(sqlLanguage,psString,false);
			try {
				if (resultSet.next()) {
					return "Success";
				} else {
					sqlLanguage = "SELECT * FROM Employee WHERE empNo = ?";
					psString = new String[]{ AdminName};
					if (SqlFunction.doSqlSelect(sqlLanguage,psString,false).next()) {
						JOptionPane.showMessageDialog(null,"密码错误！");
						return "passwordErro";
					}
					JOptionPane.showMessageDialog(null,"不存在当前工号！");
					return "NoneAcount";
				}
			}  catch (SQLException e) {
				e.printStackTrace();
			}
			SqlFunction.closeAllLink();
		}
		return null;
	}
}


