package Frame;

import Util.OtherFunction;
import Util.SqlFunction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.Objects;

public class EmployeeManage extends JFrame implements Frame{
    private JPanel panel1;
    private JTable table1;
    private JButton addButton;
    private JButton changeButton;
    private JButton delButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JComboBox comboBox1;
    private OtherFunction otherFunction = new OtherFunction();
    private SqlFunction sqlFunction = new SqlFunction();
    private String oldName = "防止重名没有作用DEFE32";
    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("人员管理");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        initTable();
        initText();
        delButton.setEnabled(false);
        changeButton.setEnabled(false);
    }

    @Override
    public void initText() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
        textField7.setText("");
        textField8.setText("");
        textField9.setText("");
        textField10.setText("");
        textField11.setText("");
        textField12.setText("");
    }

    @Override
    public void initTable() {
        final Object[] columnNames = {"员工编号", "员工姓名", "性别", "出生年月", "入职时间","所在省","所在城市","邮编","联系地址","联系电话","邮箱","所属部门","登录密码"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);
        String sqlLanguage = "SELECT * FROM Employee";
        otherFunction.selectTable(sqlLanguage, new String[]{}, table1);
    }

    @Override
    public String[] getStrings() {
        String empNo, empName, empSex, empBirthday, empEntrytime, empProvince, lempCity, empempZip, empAdress, empTelephone, empEmail, deptNo, empPwd;
        empNo = textField1.getText();
        empPwd = textField12.getText();
        empName = textField3.getText();
        empSex = textField4.getText();
        empBirthday = textField5.getText();
        empEntrytime = textField6.getText();
        empProvince = textField7.getText();
        lempCity = textField8.getText();
        empempZip = textField9.getText();
        empAdress = textField10.getText();
        empTelephone = textField11.getText();
        empEmail = textField12.getText();
        deptNo = Objects.requireNonNull(comboBox1.getSelectedItem()).toString();


        if (empNo.equals("")) {
            JOptionPane.showMessageDialog(null, "员工编号不能为空!!");
            return null;
        }
        if (empPwd.equals("")) {
            JOptionPane.showMessageDialog(null, "登录密码不能为空!!");
            return null;
        }
        if (empName.equals("")) {
            JOptionPane.showMessageDialog(null, "姓名不能为空!!");
            return null;
        }
        if (empSex.equals("")) {
            JOptionPane.showMessageDialog(null, "性别不能为空!!");
            return null;
        }
        if (empTelephone.equals("")) {
            JOptionPane.showMessageDialog(null, "联系电话不能为空!!");
            return null;
        }
        String sqlLanguage1 = "SELECT * FROM Employee WHERE empNo = ?";
        String[] psString1 = {empNo};
        try {
            if (sqlFunction.doSqlSelect(sqlLanguage1, psString1, false).next()
                    && !empNo.equals(oldName)) {
                JOptionPane.showMessageDialog(null, "已经存在当前人员编号!!");
                oldName = empNo;
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[]{empNo, empName, empSex, empBirthday, empEntrytime, empProvince, lempCity, empempZip, empAdress, empTelephone, empEmail, deptNo, empPwd};
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
