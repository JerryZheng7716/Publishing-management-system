package Frame;

import Util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JComboBox comboBox6;
    private String oldName = "防止重名没有作用DEFE32";
    private DateCombobox dateComboboxBirthday = new DateCombobox(comboBox2,comboBox3,null);
    private DateCombobox dateComboboxEntrytime = new DateCombobox(comboBox4,comboBox5,comboBox6);
    private NoName noNameDept;

    public EmployeeManage() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                textField1.setText((String) table1.getValueAt(index, 0));
                textField2.setText("");
                textField3.setText((String) table1.getValueAt(index, 1));
                if (table1.getValueAt(index, 2).equals("1")){
                    textField4.setText("男");
                }else{
                    textField4.setText("女");
                }
                String[] timeStrings = ((String) table1.getValueAt(index, 3))
                        .split("-");
                timeStrings[1] = (Integer.parseInt(timeStrings[1])) + "";//转换成int 再转 String 把前面的0去掉
                timeStrings[2] = (Integer.parseInt(timeStrings[2])) + "";//转换成int 再转 String 把前面的0去掉
                OtherFunction.setComboBoxSelect(comboBox2, timeStrings[0]);
                OtherFunction.setComboBoxSelect(comboBox3, timeStrings[1]);

                timeStrings = ((String) table1.getValueAt(index, 4))
                        .split("-");
                timeStrings[1] = (Integer.parseInt(timeStrings[1])) + "";//转换成int 再转 String 把前面的0去掉
                timeStrings[2] = (Integer.parseInt(timeStrings[2])) + "";//转换成int 再转 String 把前面的0去掉
                OtherFunction.setComboBoxSelect(comboBox4, timeStrings[0]);
                OtherFunction.setComboBoxSelect(comboBox5, timeStrings[1]);
                OtherFunction.setComboBoxSelect(comboBox6, timeStrings[2]);

                textField7.setText((String) table1.getValueAt(index, 5));
                textField8.setText((String) table1.getValueAt(index, 6));
                textField9.setText((String) table1.getValueAt(index, 7));
                textField10.setText((String) table1.getValueAt(index, 8));
                textField11.setText((String) table1.getValueAt(index, 9));
                textField12.setText((String) table1.getValueAt(index, 10));
                OtherFunction.setComboBoxSelect(comboBox1, noNameDept.getName((String) table1.getValueAt(index, 11)));
                changeButton.setEnabled(true);
                delButton.setEnabled(true);
                oldName = textField1.getText();
                super.mousePressed(e);
            }
        });
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //添加人员
                oldName = "防止重名没有作用DEFE32";
                String sqlLanguage = "INSERT Employee VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                String[] psString = getStrings();
                if (psString == null) {
                    return;
                }
                int x = SqlFunction.doSqlUpdate(sqlLanguage, psString);
                if (x > 0) {
                    JOptionPane.showMessageDialog(null, "插入成功");
                    initTable();
                    delButton.setEnabled(false);
                    changeButton.setEnabled(false);
                }
                super.mouseClicked(e);
            }
        });
        changeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //修改人员
                String sqlLanguage = "UPDATE Employee SET empNo=?, empName=?, empSex=?, empBirthday=?, empEntrytime=?, empProvince=?, empCity=?, " +
                        "empempZip=?, empAdress=?, empTelephone=?, empEmail=?, deptNo=?, empPwd=? where empNo = ?";
                String[] psString = new String[14];
                String[] ps = getStrings();
                if (ps==null){
                    return;
                }
                System.arraycopy(ps, 0, psString, 0, 13);
                psString[13]=oldName;
                if (psString[0].equals("")) {
                    return;
                }
                int x = SqlFunction.doSqlUpdate(sqlLanguage, psString);
                if (x > 0) {
                    JOptionPane.showMessageDialog(null, "修改成功");
                    initTable();
                    delButton.setEnabled(false);
                    changeButton.setEnabled(false);
                }
                super.mouseClicked(e);
            }
        });
        delButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //删除管理员
                String sqlLanguage = "DELETE Employee where empNo = ?";
                String[] psString = {oldName};
                if (psString[0].equals("")) {
                    return;
                }
                int res=JOptionPane.showConfirmDialog(null, "是否删除”"+oldName+"“的信息", "是否修改", JOptionPane.YES_NO_OPTION);
                if(res==JOptionPane.YES_OPTION){
                    int x = SqlFunction.doSqlUpdate(sqlLanguage, psString);
                    if (x > 0) {

                        JOptionPane.showMessageDialog(null, "删除成功");
                        initTable();
                        delButton.setEnabled(false);
                        changeButton.setEnabled(false);
                    }
                }else{
                    return;
                }
                super.mouseClicked(e);
            }
        });
    }

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
        noNameDept = new NoName("deptNo","deptTitle","Departments");
        delButton.setEnabled(false);
        changeButton.setEnabled(false);
    }

    @Override
    public void initText() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField7.setText("");
        textField8.setText("");
        textField9.setText("");
        textField10.setText("");
        textField11.setText("");
        textField12.setText("");
        String sql="SELECT deptTitle FROM Departments";
        OtherFunction.setComboBoxItem(sql,comboBox1);
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
        OtherFunction.setTable(sqlLanguage, new String[]{}, table1);
    }

    @Override
    public String[] getStrings() {
        String empNo, empName, empSex, empBirthday, empEntrytime, empProvince, empCity, empempZip, empAdress, empTelephone, empEmail, deptNo, empPwd;
        empNo = textField1.getText();
        if (textField2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "登录密码不能为空!!");
            return null;
        }
        empPwd = SHA1.encode(textField2.getText());
        empName = textField3.getText();
        empSex = textField4.getText().equals("女")? "0":"1";
        empBirthday = comboBox2.getSelectedItem().toString()+"-"+comboBox3.getSelectedItem().toString()+"-"+"01";
        empEntrytime = comboBox4.getSelectedItem().toString()+"-"+comboBox5.getSelectedItem().toString()+"-"+comboBox6.getSelectedItem().toString();
        empProvince = textField7.getText();
        empCity = textField8.getText();
        empempZip = textField9.getText();
        empAdress = textField10.getText();
        empTelephone = textField11.getText();
        empEmail = textField12.getText();
        String deptTitle = Objects.requireNonNull(comboBox1.getSelectedItem()).toString();
        deptNo = noNameDept.getNo(deptTitle);


        if (empNo.equals("")) {
            JOptionPane.showMessageDialog(null, "员工编号不能为空!!");
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
            if (SqlFunction.doSqlSelect(sqlLanguage1, psString1, false).next()
                    && !empNo.equals(oldName)) {
                JOptionPane.showMessageDialog(null, "已经存在当前人员编号!!");
                oldName = empNo;
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[]{empNo, empName, empSex, empBirthday, empEntrytime, empProvince, empCity, empempZip, empAdress, empTelephone, empEmail, deptNo, empPwd};
    }

}
