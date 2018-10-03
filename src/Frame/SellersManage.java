package Frame;

import Util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Objects;

public class SellersManage extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JButton addButton;
    private JButton changeButton;
    private JButton delButton;
    private String oldName = "防止重名没有作用DEFE32";

    SellersManage() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                textField1.setText((String) table1.getValueAt(index, 0));
                textField2.setText((String) table1.getValueAt(index, 1));
                textField3.setText((String) table1.getValueAt(index, 2));
                textField4.setText((String) table1.getValueAt(index, 3));
                OtherFunction.setComboBoxSelect(comboBox1,table1.getValueAt(index, 4).toString().trim());
                textField5.setText((String) table1.getValueAt(index, 5));
                textField6.setText((String) table1.getValueAt(index, 6));
                textField7.setText((String) table1.getValueAt(index, 7));
                textField8.setText((String) table1.getValueAt(index, 8));
                textField9.setText((String) table1.getValueAt(index, 9));
                textField10.setText((String) table1.getValueAt(index, 10));
                delButton.setEnabled(true);
                changeButton.setEnabled(true);
                oldName = textField1.getText();
                super.mousePressed(e);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //添加
                oldName = "防止重名没有作用DEFE32";
                String sqlLanguage = "INSERT Sellers VALUES(?,?,?,?,?,?,?,?,?,?,?) ";
                String[] psString = getStrings();
                BasicOperation.add(sqlLanguage,psString);
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                initTable();
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改
                String sqlLanguage = "UPDATE Sellers SET selNo = ?, selTitle = ?, selName = ?, selTelephone = ?, selProvince = ?, " +
                        "selCity = ?, selZip = ?, selAddress = ?, selEmail = ?, selDeliAddress = ?, selRemark = ? " +
                        "where selNo = ?";
                String[] ps = getStrings();
                BasicOperation.change(sqlLanguage,ps,oldName);
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                initTable();
            }
        });
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //删除
                String sqlLanguage = "DELETE Sellers where selNo = ?";
                BasicOperation.del(sqlLanguage,oldName);
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                initTable();
            }
        });
    }

    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("商家管理");
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
    }

    @Override
    public void initTable() {
        final Object[] columnNames = {"商家编号","商家名称","联系人姓名","联系电话","所在省","所在城市","邮编","联系地址","邮箱","送货地址","备注"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);

        String sqlLanguage = "SELECT * FROM Sellers";
        OtherFunction.setTable(sqlLanguage, new String[]{}, table1);
    }

    @Override
    public String[] getStrings() {
        String selNo, selTitle, selName, selTelephone, selProvince, selCity, selZip, selAdress, selEmail, selDeliAdress, selRemark;
        selNo = textField1.getText();
        selTitle = textField2.getText();
        selName = textField3.getText();
        selTelephone = textField4.getText();
        selProvince = Objects.requireNonNull(comboBox1.getSelectedItem()).toString();
        selCity = textField5.getText();
        selZip = textField6.getText();
        selAdress = textField7.getText();
        selEmail = textField8.getText();
        selDeliAdress = textField9.getText();
        selRemark = textField10.getText();
        if (selNo.equals("")) {
            JOptionPane.showMessageDialog(null, "类型编号不能为空!!");
            return null;
        }
        try{
            if (Integer.parseInt(selNo) >= 100000) {
                JOptionPane.showMessageDialog(null, "类型编号为5位数字");
                return null;
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "类型编号为5位数字");
        }
        selNo=FillNumber.fill(selNo,5);
        if (selTitle.equals("")) {
            JOptionPane.showMessageDialog(null, "商家名称不能为空!!");
            return null;
        }
        if (selName.equals("")) {
            JOptionPane.showMessageDialog(null, "联系人不能为空!!");
            return null;
        }
        if (selTelephone.equals("")) {
            JOptionPane.showMessageDialog(null, "联系电话不能为空!!");
            return null;
        }
        String sqlLanguage1 = "SELECT * FROM Sellers WHERE selNo = ?";
        String[] psString1 = {selNo};
        try {
            if (SqlFunction.doSqlSelect(sqlLanguage1, psString1, false).next()
                    && !selNo.equals(oldName)) {
                JOptionPane.showMessageDialog(null, "已经存在当前商家编号!!");
                oldName = selNo;
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[]{selNo, selTitle, selName, selTelephone, selProvince, selCity, selZip, selAdress, selEmail, selDeliAdress, selRemark};
    }
}
