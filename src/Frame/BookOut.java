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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookOut extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JTable table2;
    private JRadioButton orderRadioButton;
    private JRadioButton mfRadioButton;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JTextField textField2;
    private JTextField textField3;
    private JButton addButton;
    private JButton changeButton;
    private JButton delButton;
    private JTextField textField4;
    private JTextPane textPane1;
    private String oldName = "防止重名没有作用DEFE32";
    private String ordId = "防止重名没有作用DEFE32";
    private ButtonGroup buttonGroup;
    private boolean isOrderModel=true;
    private DateComboBox dateComboBox;
    BookOut() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isOrderModel){
                    int index = table1.getSelectedRow();
                    dateComboBox.setNow();
                    textField2.setText((String) table1.getValueAt(index, 5));
                    ordId = (String) table1.getValueAt(index, 0);

                    String sql1 = "SELECT bkNo,odQuantity FROM OrderDetails WHERE ordNo = ?";
                    String[] ps1 = new String[]{ordId};
                    ResultSet resultSet1 = SqlFunction.doSqlSelect(sql1,ps1,false);
                    String bookDetails = "";
                    int count = 0;
                    try{
                        while (resultSet1.next()){
                            if (count%2==0 && count!=0){
                                bookDetails = bookDetails+"\n";
                            }
                            bookDetails = bookDetails+"图书编号："+resultSet1.getString(1)+"; ";
                            bookDetails = bookDetails+"订购数量："+resultSet1.getString(2)+";     ";
                            count++;
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    textPane1.setText(bookDetails);
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
                if (!isOrderModel){
                    delButton.setEnabled(true);
                    changeButton.setEnabled(true);
                }
                oldName = textField1.getText();
                textPane1.setText("");
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
                String sqlLanguage;
                ArrayList<String[]> arrayList = getIoStrings();
                boolean kuCunBuZu=false;
                for (String[] psString :arrayList
                     ) {
                    String bkNo = psString[1];
                    int ioQuantity = Integer.parseInt(psString[4]);
                    int bkPrtQty=0;
                    sqlLanguage = "SELECT bkPrtQty FROM Books WHERE bkNo = ?";//查询书本库存数量
                    psString = new String[]{bkNo};
                    ResultSet resultSet = SqlFunction.doSqlSelect(sqlLanguage,psString,false);
                    try {
                        resultSet.next();
                        bkPrtQty=resultSet.getInt(1);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    if (bkPrtQty-ioQuantity<0){//如果库存不足
                        int res = JOptionPane.showConfirmDialog(null, "库存不足，出库失败！是否生成缺货记录？", "库存不足",
                                JOptionPane.YES_NO_OPTION);
                        if (res == JOptionPane.YES_OPTION) {
                            String sql = "INSERT INTO OutOfStock(bkNo,oosQuantity,empNo,oosTime,oosUse) VALUES(?,?,?,?,?)";
                            //流水号	oosId 图书编号	bkNo 数量	oosQuantity 记录人编号	empNo 记录时间	oosTime 用途	oosUse
                            Date date = new Date();
                            DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
                            String df = dateFormat.format(date);
                            String[] ps = new String[]{bkNo, String.valueOf((ioQuantity-bkPrtQty)),LoginInfo.getLoginNo(),df,"订单出库缺货"};
                            SqlFunction.doSqlUpdate(sql,ps);
                        }
                        kuCunBuZu=true;
                    }
                }
                if (kuCunBuZu){
                    return;
                }
                for (String[] psString :arrayList
                        ) {
                    //出库
                    String bkNo = psString[1];
                    int ioQuantity = Integer.parseInt(psString[4]);
                    int bkPrtQty=0;
                    sqlLanguage = "SELECT bkPrtQty FROM Books WHERE bkNo = ?";//查询书本库存数量
                    String[] ps = new String[]{bkNo};
                    ResultSet resultSet = SqlFunction.doSqlSelect(sqlLanguage,ps,false);
                    try {
                        resultSet.next();
                        bkPrtQty=resultSet.getInt(1);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    sqlLanguage = "INSERT INTO InoutWH(whNo, bkNo, ioType, ioTime, ioQuantity, empNo, prtRemark) VALUES(?,?,?,?,?,?,?) ";
                    if (BasicOperation.add(sqlLanguage,psString)){
                        if (isOrderModel){//如果是订单出库，那么将订单置为已出库状态
                            sqlLanguage = "UPDATE Orders SET ordState = '1' where ordNo = ?";
                            String[] ps1 = new String[]{ordId};
                            SqlFunction.doSqlUpdate(sqlLanguage,ps1);
                        }

                        sqlLanguage = "UPDATE Books SET bkPrtQty = ? where bkNo = ?";//削减图书库存
                        String[] ps2= new String[]{bkPrtQty-ioQuantity+"",bkNo};
                        SqlFunction.doSqlUpdate(sqlLanguage,ps2);
                    }
                }

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
        orderRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setModel();
            }
        });
        mfRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setModel();
            }
        });
    }
    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("图书出库");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        initTable();
        initText();
        buttonGroup=new ButtonGroup();
        buttonGroup.add(orderRadioButton);
        buttonGroup.add(mfRadioButton);
        orderRadioButton.setSelected(true);
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
        textField1.setText("自动生成，不用填写");
        textField3.setText("自动生成，不用填写");
        dateComboBox = new DateComboBox(comboBox3,comboBox4,comboBox5);
        dateComboBox.setNow();
    }

    @Override
    public void initTable() {
        final Object[] columnNames = {"订单编号", "商家编号", "经办人编号", "订购时间", "预定发货时间", "总数量","总价", "折扣", "备注","订单状态"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);
        table1.addColumn(column);
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);
        String sqlLanguage = "SELECT * FROM Orders WHERE ordState='0'";
        OtherFunction.setTable(sqlLanguage, new String[]{}, table1);

        final Object[] columnNames1 = {"流水号","仓库编号","图书编号","类型","时间","数量","经办人编号","备注"};
        Object[][] rowData1 = {};
        TableColumn column1 = new TableColumn();
        column.setHeaderValue(columnNames1);//
        table2.addColumn(column1);//
        TableModel dataModel1 = new DefaultTableModel(rowData1, columnNames1);
        table2.setModel(dataModel1);
        String sqlLanguage1 = "SELECT * FROM InoutWH  WHERE ioType ='2'";
        OtherFunction.setTable(sqlLanguage1, new String[]{}, table2);
    }

    @Override
    public String[] getStrings() {
        String whNo, bkNo, ioType, ioTime, ioQuantity, empNo, prtRemark;
        whNo=comboBox1.getSelectedItem().toString();
        bkNo=comboBox2.getSelectedItem().toString();
        ioType="2";
        ioTime=dateComboBox.getDate();
        ioQuantity=textField2.getText();
        empNo=LoginInfo.getLoginNo();
        prtRemark=textField4.getText();
        if (ioQuantity.equals("")) {
            JOptionPane.showMessageDialog(null, "出库数量不能为空!!");
            return null;
        }
        return new String[]{whNo, bkNo, ioType, ioTime, ioQuantity, empNo, prtRemark};
    }

    public ArrayList<String[]> getIoStrings() {
        ArrayList<String[]> arrayList = new ArrayList<>();
        String whNo, bkNo, ioType, ioTime, ioQuantity, empNo, prtRemark;
        whNo=comboBox1.getSelectedItem().toString();
        bkNo=comboBox2.getSelectedItem().toString();
        ioQuantity=textField2.getText();
        ioType="2";
        ioTime=dateComboBox.getDate();
        empNo=LoginInfo.getLoginNo();
        prtRemark=textField4.getText();
        if (isOrderModel){
            String sql = "SELECT bkNo,odQuantity FROM OrderDetails WHERE ordNo = ?";//从订单详情获取订单
            String[] ps = new String[]{ordId};
            ResultSet resultSet = SqlFunction.doSqlSelect(sql,ps,false);
            try{
                while (resultSet.next()){
                    bkNo=resultSet.getString(1);
                    ioQuantity=resultSet.getString(2);
                    String[] strings = new String[]{whNo, bkNo, ioType, ioTime, ioQuantity, empNo, prtRemark};
                    arrayList.add(strings);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            arrayList.add(new String[]{whNo, bkNo, ioType, ioTime, ioQuantity, empNo, prtRemark});
        }
        return arrayList;
    }

    private void setModel(){
        if (orderRadioButton.isSelected()){
            isOrderModel=true;
            comboBox2.setEnabled(false);
            comboBox3.setEnabled(false);
            comboBox4.setEnabled(false);
            comboBox5.setEnabled(false);
            textField2.setEnabled(false);
            changeButton.setEnabled(false);
            delButton.setEnabled(false);
            delButton.setEnabled(false);
        }else {
            isOrderModel=false;
            comboBox2.setEnabled(true);
            comboBox3.setEnabled(true);
            comboBox4.setEnabled(true);
            comboBox5.setEnabled(true);
            textField2.setEnabled(true);
            changeButton.setEnabled(true);
            delButton.setEnabled(true);
            delButton.setEnabled(true);
        }
    }
}
