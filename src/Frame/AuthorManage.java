package Frame;

import Util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Objects;

public class AuthorManage extends JFrame implements Frame{
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
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JLabel idProvince;
    private String oldName = "防止重名没有作用DEFE32";

    public AuthorManage() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //添加作者
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(),2)){
                    return;
                }
                oldName = "防止重名没有作用DEFE32";
                String sqlLanguage = "INSERT Authors VALUES(?,?,?,?,?,?,?,?,?,?,?) ";
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
            }
        });
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String id;
                int province = comboBox1.getSelectedIndex()+1;
                if (province<=9){
                    id="0"+province;
                }else {
                    id=province+"";
                }
                idProvince.setText(id);
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                String idString = (String) table1.getValueAt(index, 0);
                int id = Integer.parseInt(idString);
                int id1 = id/10000;
                int id2 = id%10000;
                if (id1<=9){
                    idProvince.setText("0"+id1);
                }else {
                    idProvince.setText(id1+"");
                }

                textField1.setText(FillNumber.fill(String.valueOf(id2),4));
                textField2.setText((String) table1.getValueAt(index, 1));
                comboBox2.setSelectedIndex(Integer.parseInt((String)table1.getValueAt(index, 2))^1);
                textField4.setText((String) table1.getValueAt(index, 3));
                textField5.setText((String) table1.getValueAt(index, 4));
                String pv=(String) table1.getValueAt(index, 5);
                OtherFunction.setComboBoxSelect(comboBox1,pv.replaceAll(" ",""));
                textField7.setText((String) table1.getValueAt(index, 6));
                textField8.setText((String) table1.getValueAt(index, 7));
                textField9.setText((String) table1.getValueAt(index, 8));
                textField10.setText((String) table1.getValueAt(index, 9));
                textField11.setText((String) table1.getValueAt(index, 10));
                delButton.setEnabled(true);
                changeButton.setEnabled(true);
                oldName = idProvince.getText()+textField1.getText();
                super.mousePressed(e);
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改作者
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(),2)){
                    return;
                }
                String sqlLanguage = "UPDATE Authors SET  auNo=?, auName=?, auSex=?, auTitle=?, auTelephone=?, auProvince=?, auCity=?, auZip=?, auAddress=?, " +
                        "auEmail=?, auRemark=? WHERE auNo=?";
                String[] psString = new String[12];
                String[] ps = getStrings();
                if (ps==null){
                    return;
                }
                System.arraycopy(ps, 0, psString, 0, 11);
                psString[11]=oldName;
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
            }
        });
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //删除管理员
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(),2)){
                    return;
                }
                String sqlLanguage = "DELETE Authors where auNo = ?";
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
            }
        });
    }

    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("作者管理");
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
        Province.setComboBoxItem(comboBox1);
        comboBox2.addItem("男");
        comboBox2.addItem("女");
    }

    @Override
    public void initTable() {
        final Object[] columnNames = {"作者编号", "作者姓名", "作者性别", "作者职称", "联系电话", "所在省", "所在城市", "邮编", "联系地址", "邮箱", "备注"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);
        String sqlLanguage = "SELECT * FROM Authors";
        OtherFunction.setTable(sqlLanguage, new String[]{}, table1);
    }

    @Override
    public String[] getStrings() {
        String auNo, auName, auSex, auTitle, auTelephone, auProvince, auCity, auZip, auAddress, auEmail, auRemark;
        auNo = idProvince.getText()+FillNumber.fill(textField1.getText(),4);
        auName = textField2.getText();
        auSex = String.valueOf(comboBox2.getSelectedIndex()^1);
        auTitle = textField4.getText();
        auTelephone = textField5.getText();
        auProvince = Objects.requireNonNull(comboBox1.getSelectedItem()).toString();
        auCity = textField7.getText();
        auZip = textField8.getText();
        auAddress = textField9.getText();
        auEmail = textField10.getText();
        auRemark = textField11.getText();

        if (textField1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "作者编号不能为空!!");
            return null;
        }
        try {
            if (Integer.parseInt(textField1.getText()) >= 10000) {
                JOptionPane.showMessageDialog(null, "作者编号为4位数字");
                return null;
            }
        }catch (Exception e){
            System.out.println("作者编号为纯数字");
        }

        if (auName.equals("")) {
            JOptionPane.showMessageDialog(null, "作者名不能为空!!");
            return null;
        }
        if (auTelephone.equals("")) {
            JOptionPane.showMessageDialog(null, "联系电话不能为空!!");
            return null;
        }
        String sqlLanguage1 = "SELECT * FROM Authors WHERE auNo = ?";
        String[] psString1 = {auNo};
        try {
            if (SqlFunction.doSqlSelect(sqlLanguage1, psString1, false).next()
                    && !auNo.equals(oldName)) {
                JOptionPane.showMessageDialog(null, "已经存在当前作者编号!!");
                oldName = auNo;
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[]{auNo, auName, auSex, auTitle, auTelephone, auProvince, auCity, auZip, auAddress, auEmail, auRemark};
    }
}
