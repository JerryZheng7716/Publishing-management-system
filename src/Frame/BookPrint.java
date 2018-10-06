package Frame;

import Util.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.Document;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookPrint extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextField textField3;
    private JComboBox comboBox4;
    private JTextField textField4;
    private JTextField textField5;
    private JButton addButton;
    private JButton changeButton;
    private JButton delButton;
    private JButton checkButton;
    private JTable table2;
    private JButton cancelButton;
    private JComboBox comboBox5;
    private JComboBox comboBox6;
    private boolean isGod = false;
    private DateComboBox dateComboBox;
    private String oldName = "防止重名没有作用DEFE32";

    BookPrint() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                textField1.setText((String) table1.getValueAt(index, 0));
                textField2.setText((String) table1.getValueAt(index, 1));
                String[] timeStrings = ((String) table1.getValueAt(index, 2))
                        .split("-");
                timeStrings[1] = (Integer.parseInt(timeStrings[1])) + "";//转换成int 再转 String 把前面的0去掉
                timeStrings[2] = (Integer.parseInt(timeStrings[2])) + "";//转换成int 再转 String 把前面的0去掉
                OtherFunction.setComboBoxSelect(comboBox1, timeStrings[0]);
                OtherFunction.setComboBoxSelect(comboBox2, timeStrings[1]);
                OtherFunction.setComboBoxSelect(comboBox3, timeStrings[2]);
                textField3.setText((String) table1.getValueAt(index, 3));
                OtherFunction.setComboBoxSelect(comboBox4, (String) table1.getValueAt(index, 4));
                textField4.setText((String) table1.getValueAt(index, 5));
                textField5.setText((String) table1.getValueAt(index, 6));
                delButton.setEnabled(true);
                changeButton.setEnabled(true);
                if (isGod) {
                    checkButton.setEnabled(true);
                    cancelButton.setEnabled(true);
                }
                oldName = textField1.getText();
                super.mousePressed(e);
            }
        });
        table2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table2.getSelectedRow();
                textField2.setText((String) table2.getValueAt(index, 0));
                super.mousePressed(e);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //添加
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书印刷(), 2)) {
                    return;
                }
                oldName = "防止重名没有作用DEFE32";
                String sqlLanguage = "INSERT INTO PubPrint(bkNo, prtTime, prtQuantity, prtNumber, prtRemark, prtState) VALUES(?,?,?,?,?,?) ";
                String[] psString = getStrings();
                BasicOperation.add(sqlLanguage, psString);
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                checkButton.setEnabled(false);
                cancelButton.setEnabled(false);
                initText();
                initTable();
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书印刷(), 2)) {
                    return;
                }
                String sqlLanguage = "UPDATE PubPrint SET bkNo = ?, prtTime = ?, prtQuantity = ?, prtNumber = ?, prtRemark = ? ,prtState = ? " +
                        "where prtID = ?";
                String[] ps = getStrings();
                BasicOperation.change(sqlLanguage, ps, oldName);
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                checkButton.setEnabled(false);
                cancelButton.setEnabled(false);
                initText();
                initTable();
            }
        });
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //删除
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书印刷(), 2)) {
                    return;
                }
                String sqlLanguage = "DELETE PubPrint where prtID = ?";
                BasicOperation.del(sqlLanguage, oldName);
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                checkButton.setEnabled(false);
                cancelButton.setEnabled(false);
                initText();
                initTable();
            }
        });
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "UPDATE PubPrint set empNo=? WHERE prtID = ?";
                String[] ps = new String[]{LoginInfo.getLoginNo(), oldName};
                int x = SqlFunction.doSqlUpdate(sql, ps);
                if (x > 0) {
                    JOptionPane.showMessageDialog(null, "审核成功");
                }
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                checkButton.setEnabled(false);
                cancelButton.setEnabled(false);
                initTable();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "UPDATE PubPrint set empNo=null WHERE prtID = ?";
                String[] ps = new String[]{oldName};
                int x = SqlFunction.doSqlUpdate(sql, ps);
                if (x > 0) {
                    JOptionPane.showMessageDialog(null, "撤销审核成功");
                }
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                checkButton.setEnabled(false);
                cancelButton.setEnabled(false);
                initTable();
            }
        });
        Document dt = textField2.getDocument();
        dt.addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setLastNumber();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setLastNumber();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        comboBox5.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                doFilter(comboBox5.getSelectedIndex(), comboBox6.getSelectedIndex());
            }
        });
        comboBox6.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                doFilter(comboBox5.getSelectedIndex(), comboBox6.getSelectedIndex());
            }
        });
    }

    private void setLastNumber() {
        comboBox4.removeAllItems();
        String sql = "SELECT bkLastNumber FROM Books WHERE bkNo=?";
        String[] ps = new String[]{textField2.getText()};
        int lastNumber = 0;
        ResultSet resultSet = SqlFunction.doSqlSelect(sql, ps, false);
        try {
            while (resultSet.next()) {
                lastNumber = resultSet.getInt(1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        for (int i = 1; i <= lastNumber; i++) {
            comboBox4.addItem(i + "");
        }
    }

    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("图书印刷管理");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        dateComboBox = new DateComboBox(comboBox1, comboBox2, comboBox3);
        initTable();
        initText();
        delButton.setEnabled(false);
        changeButton.setEnabled(false);
        String dept = LoginInfo.getDeptNo();
        checkButton.setEnabled(false);
        cancelButton.setEnabled(false);
        if (LoginInfo.getQx审核().equals("2")) {
            isGod = true;
        }
    }

    @Override
    public void initText() {
        textField1.setText("自动生成，无需填写");
        textField4.setText("自动生成，无需填写");
        comboBox5.addItem(("全部"));
        comboBox5.addItem(("等待印刷"));
        comboBox5.addItem(("已入库"));

        comboBox6.addItem(("全部"));
        comboBox6.addItem(("未审核"));
        comboBox6.addItem(("已审核"));
        dateComboBox.setNow();
    }

    @Override
    public void initTable() {
        final Object[] columnNames = {"流水号", "图书编号", "送印时间", "印刷数量", "印刷版次", "审核人编号", "备注", "印刷状态"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);
        String sqlLanguage = "SELECT * FROM PubPrint";
        OtherFunction.setTable(sqlLanguage, new String[]{}, table1);

        final Object[] columnNames1 = {"图书编号", "书名", "最新版次"};
        Object[][] rowData1 = {};
        TableColumn column1 = new TableColumn();
        column.setHeaderValue(columnNames1);//
        table2.addColumn(column1);//
        TableModel dataModel1 = new DefaultTableModel(rowData1, columnNames1);
        table2.setModel(dataModel1);
        String sqlLanguage1 = "SELECT bkNo,bkTitle,bkLastNumber FROM Books";
        OtherFunction.setTable(sqlLanguage1, new String[]{}, table2);
    }

    /**
     * 对印刷表进行筛选
     *
     * @param add   入库情况 0全部，1等待印刷，2已经入库
     * @param check 审核情况 0全部，1未审核，2已经审核
     */
    private void doFilter(int add, int check) {
        String sqlLanguage = "SELECT * FROM PubPrint ";
        String empNo, prtState, where, and;
        switch (check) {
            case 0:
                empNo = "";
                break;
            case 2:
                empNo = "empNo IS NOT NULL ";
                break;
            case 1:
                empNo = "empNo IS NULL ";
                break;
            default:
                empNo = "";
        }
        switch (add) {
            case 0:
                prtState = "";
                break;
            case 2:
                prtState = "prtState='1'";
                break;
            case 1:
                prtState = "prtState='0'";
                break;
            default:
                prtState = "";
        }
        if (!empNo.equals("") && !prtState.equals("")) {
            and = "AND ";
        } else {
            and = "";
        }
        if (!empNo.equals("") || !prtState.equals("")) {
            where = "WHERE ";
        } else {
            where = "";
        }
        sqlLanguage = sqlLanguage + where + empNo + and + prtState;
        OtherFunction.setTable(sqlLanguage, new String[]{}, table1);
    }

    @Override
    public String[] getStrings() {
        String bkNo, prtTime, prtQuantity, prtNumber, prtRemark, prtState;
        bkNo = textField2.getText();
        prtTime = comboBox1.getSelectedItem() + "-" + comboBox2.getSelectedItem() + "-" + comboBox3.getSelectedItem();
        prtQuantity = textField3.getText();
        prtNumber = comboBox4.getSelectedItem() + "";
        prtRemark = textField5.getText();
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
        }
        if (prtQuantity.equals("")) {
            JOptionPane.showMessageDialog(null, "印刷数量不能为空!!");
            return null;
        }
        bkNo = FillNumber.fill(bkNo, 5);
        prtState = "0";
        return new String[]{bkNo, prtTime, prtQuantity, prtNumber, prtRemark, prtState};
    }
}
