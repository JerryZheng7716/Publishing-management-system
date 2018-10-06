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

public class BookAdd extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JTable table2;
    private JRadioButton printRadioButton;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton addButton;
    private JButton changeButton;
    private JButton delButton;
    private JRadioButton mFRadioButton;
    private String oldName = "防止重名没有作用DEFE32";
    private String prtID = "防止重名没有作用DEFE32";
    private ButtonGroup buttonGroup;
    private boolean isPrintModel=true;
    private DateComboBox dateComboBox;
    BookAdd() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isPrintModel){
                    int index = table1.getSelectedRow();
                    OtherFunction.setComboBoxSelect(comboBox2,(String) table1.getValueAt(index, 1));
                    dateComboBox.setNow();
                    textField2.setText((String) table1.getValueAt(index, 3));
                    prtID = (String) table1.getValueAt(index, 0);
                }
                super.mousePressed(e);
            }
        });
        table2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table2.getSelectedRow();
                textField1.setText((String) table2.getValueAt(index, 0));
                OtherFunction.setComboBoxSelect(comboBox1,(String) table2.getValueAt(index, 1));
                OtherFunction.setComboBoxSelect(comboBox2,(String) table2.getValueAt(index, 2));
                dateComboBox.setSelect((String) table2.getValueAt(index, 4));
                textField2.setText((String) table2.getValueAt(index, 5));
                textField3.setText((String) table2.getValueAt(index, 6));
                textField4.setText((String) table2.getValueAt(index, 7));
                if (!isPrintModel){
                    delButton.setEnabled(true);
                    changeButton.setEnabled(true);
                }
                oldName = textField1.getText();
                super.mousePressed(e);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //添加
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书印刷(),2)){
                    return;
                }
                oldName = "防止重名没有作用DEFE32";
                String sqlLanguage = "INSERT INTO InoutWH(whNo, bkNo, ioType, ioTime, ioQuantity, empNo, prtRemark) VALUES(?,?,?,?,?,?,?) ";
                String[] psString = getStrings();
                String bkNo = psString[1];
                int ioQuantity = Integer.parseInt(psString[4]);
                BasicOperation.add(sqlLanguage,psString);

                if (isPrintModel){//如果是从印刷入库，那么将印刷置为已入库状态
                    sqlLanguage = "UPDATE PubPrint SET prtState = '1' where prtID = ?";
                    psString = new String[]{prtID};
                    SqlFunction.doSqlUpdate(sqlLanguage,psString);
                }
                int bkPrtQty=0;
                sqlLanguage = "SELECT bkPrtQty FROM Books WHERE bkNo = ?";
                psString = new String[]{bkNo};
                ResultSet resultSet = SqlFunction.doSqlSelect(sqlLanguage,psString,false);
                try {
                    resultSet.next();
                    bkPrtQty=resultSet.getInt(1);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                sqlLanguage = "UPDATE Books SET bkPrtQty = ? where bkNo = ?";
                psString = new String[]{bkPrtQty+ioQuantity+"",bkNo};
                SqlFunction.doSqlUpdate(sqlLanguage,psString);

                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                initTable();
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书印刷(),2)){
                    return;
                }
                String sqlLanguage = "UPDATE InoutWH SET whNo = ?, bkNo = ?, ioType = ?, ioTime = ?, ioQuantity = ?, empNo = ?, prtRemark = ? " +
                        "where ioId = ?";
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
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书印刷(),2)){
                    return;
                }
                String sqlLanguage = "DELETE InoutWH where ioId = ?";
                BasicOperation.del(sqlLanguage,oldName);
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                initTable();
            }
        });
        printRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setModel();
            }
        });
        mFRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setModel();
            }
        });
    }
    private void setModel(){
        if (printRadioButton.isSelected()){
            isPrintModel=true;
            comboBox2.setEnabled(false);
            comboBox3.setEnabled(false);
            comboBox4.setEnabled(false);
            comboBox5.setEnabled(false);
            textField2.setEnabled(false);
            changeButton.setEnabled(false);
            delButton.setEnabled(false);
        }else {
            isPrintModel=false;
            comboBox2.setEnabled(true);
            comboBox3.setEnabled(true);
            comboBox4.setEnabled(true);
            comboBox5.setEnabled(true);
            textField2.setEnabled(true);
            changeButton.setEnabled(true);
            delButton.setEnabled(true);
        }
    }

    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("图书入库");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        initTable();
        initText();
        buttonGroup=new ButtonGroup();
        buttonGroup.add(printRadioButton);
        buttonGroup.add(mFRadioButton);
        printRadioButton.setSelected(true);
        setModel();
        delButton.setEnabled(false);
        changeButton.setEnabled(false);
    }

    @Override
    public void initText() {
        String sql = "SELECT whNo FROM Warehouse";
        OtherFunction.setComboBoxItem(sql,comboBox1);
        sql = "SELECT bkNo FROM Books";
        OtherFunction.setComboBoxItem(sql,comboBox2);
        textField1.setText("自动生成，无需填写");
        textField3.setText("自动生成，无需填写");
        dateComboBox = new DateComboBox(comboBox3,comboBox4,comboBox5);
        dateComboBox.setNow();
    }


    @Override
    public void initTable() {
        final Object[] columnNames = {"流水号","图书编号","送印时间","印刷数量","印刷版次","审核人编号","备注","印刷状态"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);
        String sqlLanguage = "SELECT * FROM PubPrint WHERE empNo IS NOT NULL AND prtState='0'";
        OtherFunction.setTable(sqlLanguage, new String[]{}, table1);

        final Object[] columnNames1 = {"流水号","仓库编号","图书编号","类型","时间","数量","经办人编号","备注"};
        Object[][] rowData1 = {};
        TableColumn column1 = new TableColumn();
        column.setHeaderValue(columnNames1);//
        table2.addColumn(column1);//
        TableModel dataModel1 = new DefaultTableModel(rowData1, columnNames1);
        table2.setModel(dataModel1);
        String sqlLanguage1 = "SELECT * FROM InoutWH";
        OtherFunction.setTable(sqlLanguage1, new String[]{}, table2);
    }

    @Override
    public String[] getStrings() {
        String whNo, bkNo, ioType, ioTime, ioQuantity, empNo, prtRemark;
        whNo=comboBox1.getSelectedItem().toString();
        bkNo=comboBox2.getSelectedItem().toString();
        ioType="1";
        ioTime=comboBox3.getSelectedItem()+"-"+comboBox4.getSelectedItem()+"-"+comboBox5.getSelectedItem();
        ioQuantity=textField2.getText();
        empNo=LoginInfo.getLoginNo();
        prtRemark=textField4.getText();
        if (ioQuantity.equals("")) {
            JOptionPane.showMessageDialog(null, "入库数量不能为空!!");
            return null;
        }
        return new String[]{whNo, bkNo, ioType, ioTime, ioQuantity, empNo, prtRemark};
    }
}
