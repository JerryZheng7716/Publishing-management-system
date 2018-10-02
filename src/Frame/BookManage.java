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
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookManage extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JTable table2;
    private JButton addButton;
    private JButton changeButton;
    private JButton delButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextField textField5;
    private JTextField textField6;
    private JComboBox comboBox4;
    private JTextField textField7;
    private DateCombobox dateCombobox;
    private String oldName = "防止重名没有作用DEFE32";
    private NoName noNameType;
    BookManage() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                textField1.setText((String) table1.getValueAt(index, 0));
                textField2.setText((String) table1.getValueAt(index, 1));
                textField3.setText((String) table1.getValueAt(index, 2));
                textField4.setText((String) table1.getValueAt(index, 3));
                String[] timeStrings = ((String) table1.getValueAt(index, 4))
                        .split("-");
                timeStrings[1] = (Integer.parseInt(timeStrings[1])) + "";//转换成int 再转 String 把前面的0去掉
                timeStrings[2] = (Integer.parseInt(timeStrings[2])) + "";//转换成int 再转 String 把前面的0去掉
                OtherFunction.setComboBoxSelect(comboBox1, timeStrings[0]);
                OtherFunction.setComboBoxSelect(comboBox2, timeStrings[1]);
                OtherFunction.setComboBoxSelect(comboBox3, timeStrings[2]);
                textField5.setText((String) table1.getValueAt(index, 5));
                textField6.setText((String) table1.getValueAt(index, 6));
                OtherFunction.setComboBoxSelect(comboBox4,(String) table1.getValueAt(index, 7));

                String sql = "SELECT typeNo FROM BookType WHERE bkNo=?";
                String[] ps = new String[]{(String) table1.getValueAt(index, 0)};
                ResultSet resultSet = SqlFunction.doSqlSelect(sql,ps,false);
                textField7.setText("");
                try{
                    while (resultSet.next()){
                        String type = noNameType.getName(resultSet.getString(1));
                        if (textField7.getText().equals("")){
                            textField7.setText(type);
                        }else {
                            textField7.setText(textField7.getText()+";"+type);
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
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
                String sqlLanguage = "INSERT Books VALUES(?,?,?,?,?,?,?,?) ";
                String[] psString = getStrings();
                if (psString==null){
                    return;
                }
                BasicOperation.add(sqlLanguage,psString);
                String[] types = textField7.getText().split(";");
                delType(psString[0]);
                addType(psString[0],types);
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                initTable();
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改
                delType(oldName);
                String sqlLanguage = "UPDATE Books SET bkNo=?, bkTitle=?, bkPrice=?, bkWords=?, bkFirstTime=?, bkLastNumber=?, bkPrtQty=?, whNo=? where bkNo = ?";
                String[] ps = getStrings();
                if (ps==null){
                    return;
                }
                BasicOperation.change(sqlLanguage,ps,oldName);
                String[] types = textField7.getText().split(";");
                addType(ps[0],types);
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                initTable();
            }
        });
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //删除
                delType(oldName);
                String sqlLanguage = "DELETE Books where bkNo = ?";
                BasicOperation.del(sqlLanguage,oldName);
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                initTable();
            }
        });
        table2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table2.getSelectedRow();
                String s = ((String) table2.getValueAt(index, 1)).trim();
                if (textField7.getText().equals("")){
                    textField7.setText(s);
                }else {
                    textField7.setText(textField7.getText()+";"+s);
                }

                super.mousePressed(e);
            }
        });
    }

    /**
     * 将图书类目添加到虚表BookType中
     */
    private void addType(String bkNo,String[] types){
        //再添加记录
        for (int i = 0; i < types.length; i++) {
            types[i]=noNameType.getNo(types[i]);
        }
        String sql = "INSERT into BookType(typeNo,bkNo) VALUES(?,?)";
        for (int i = 0; i < types.length; i++) {
            boolean isHave = false;
            for (int j = 0; j < i; j++) {
                if (types[i].equals(types[j])){//去重
                    isHave=true;
                }
            }
            if (!isHave){
                String[] ps = new String[]{types[i],bkNo};
                SqlFunction.doSqlUpdate(sql, ps);
            }
        }

    }

    private void delType(String bkNo){
        //先删除原来的记录
        String sql = "DELETE BookType WHERE bkNo=?";
        String[] ps = new String[]{bkNo};
        SqlFunction.doSqlUpdate(sql,ps);
    }
    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("图书管理");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        initTable();
        initText();
        delButton.setEnabled(false);
        changeButton.setEnabled(false);
        noNameType = new NoName("TypeNo","TypeTitle","types");
    }

    @Override
    public void initText() {
        String sql = "SELECT whNo FROM Warehouse";
        OtherFunction.setComboBoxItem(sql,comboBox4);
    }

    @Override
    public void initTable() {
        final Object[] columnNames = {"图书编号", "书名", "单价", "字数", "首版时间", "最新版次", "当前量", "所在仓库"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);
        String sqlLanguage = "SELECT * FROM Books";
        OtherFunction.setTable(sqlLanguage, new String[]{}, table1);

        final Object[] columnNames1 = {"类型编号", "类型名称"};
        Object[][] rowData1 = {};
        TableColumn column1 = new TableColumn();
        column.setHeaderValue(columnNames1);//
        table2.addColumn(column1);//
        TableModel dataModel1 = new DefaultTableModel(rowData1, columnNames1);
        table2.setModel(dataModel1);
        String sqlLanguage1 = "SELECT typeNo,typeTitle FROM Types";
        OtherFunction.setTable(sqlLanguage1, new String[]{}, table2);
        dateCombobox = new DateCombobox(comboBox1,comboBox2,comboBox3);
    }

    @Override
    public String[] getStrings() {
        String bkNo, bkTitle, bkPrice, bkWords, bkFirstTime, bkLastNumber, bkPrtQty, whNo;
        bkNo = textField1.getText();
        bkTitle = textField2.getText();
        bkPrice = textField3.getText();
        bkWords = textField4.getText();
        bkFirstTime = comboBox1.getSelectedItem()+"-"+comboBox2.getSelectedItem()+"-"+comboBox3.getSelectedItem();
        bkLastNumber = textField5.getText();
        bkPrtQty = textField6.getText();
        whNo = comboBox4.getSelectedItem()+"";
        dateCombobox.initDayCombobox();
        if (bkNo.equals("")) {
            JOptionPane.showMessageDialog(null, "图书编号不能为空!!");
            return null;
        }
        try {
            if (Integer.parseInt(bkNo) >= 100000) {
                JOptionPane.showMessageDialog(null, "图书编号为5位数字");
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "图书编号为5位数字");
            return null;
        }
        bkNo=FillNumber.fill(bkNo,5);
        if (bkTitle.equals("")) {
            JOptionPane.showMessageDialog(null, "书名不能为空!!");
            return null;
        }
        if (bkPrice.equals("")) {
            JOptionPane.showMessageDialog(null, "单价不能为空!!");
            return null;
        }
        String sqlLanguage1 = "SELECT * FROM Books WHERE bkNo = ?";
        String[] psString1 = {bkNo};
        try {
            if (SqlFunction.doSqlSelect(sqlLanguage1, psString1, false).next()
                    && !bkNo.equals(oldName)) {
                JOptionPane.showMessageDialog(null, "已经存在当前图书编号!!");
                oldName = bkNo;
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[]{bkNo, bkTitle, bkPrice, bkWords, bkFirstTime, bkLastNumber, bkPrtQty, whNo};
    }

}
