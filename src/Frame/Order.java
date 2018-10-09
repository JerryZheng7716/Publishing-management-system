package Frame;

import Util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JComboBox comboBox6;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton setBookButton;
    private JButton changeButton;
    private JButton delButton;
    private JButton addButton;
    private DateComboBox dateComboBoxOrder, dateComboBoxShip;
    private String oldName = "防止重名没有作用DEFE32";
    private boolean isChange = false;

    public Order() {
        setBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookDetails bookDetails = new BookDetails();
                bookDetails.showFrame();

                bookDetails.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        System.out.println("订书详情窗口已经被关闭");
                        //当订书详情窗口关闭时，自动填写总数量，总价格

                        int ordQuantity = 0, ordPayment = 0;
                        ArrayList<BookDetail.Book> bookArrayList = BookDetail.getAllBook();
                        for (int i = 0; i < bookArrayList.size(); i++) {
                            String bkPrice = "0";
                            ordQuantity += Integer.parseInt(bookArrayList.get(i).getQuantity());
                            ResultSet resultSet = SqlFunction.doSqlSelect("SELECT bkPrice FROM Books WHERE bkNo=?",
                                    new String[]{bookArrayList.get(i).getBookID()}, false);//获取图书单价
                            try {
                                resultSet.next();
                                bkPrice = resultSet.getString(1);
                            } catch (SQLException e1) {
                                JOptionPane.showMessageDialog(null, "您的详单中订单号为"
                                        + bookArrayList.get(i).getBookID() + "的书不存在，请检查");
                                textField4.setText("");//将数量置为空，将无法进行添加修改操作。
                                return;
                            }
                            ordPayment = ordPayment + (Integer.parseInt(bookArrayList.get(i).getQuantity()) * Integer.parseInt(bkPrice));
                        }
                        textField4.setText(ordQuantity + "");
                        textField5.setText(ordPayment + "");
                        super.windowDeactivated(e);
                    }
                });
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                textField1.setText((String) table1.getValueAt(index, 0));
                textField2.setText((String) table1.getValueAt(index, 1));
                textField3.setText((String) table1.getValueAt(index, 2));
                dateComboBoxOrder.setSelect(((String) table1.getValueAt(index, 3)).substring(0, 10));
                dateComboBoxShip.setSelect((String) table1.getValueAt(index, 4));
                textField4.setText((String) table1.getValueAt(index, 5));
                textField5.setText((String) table1.getValueAt(index, 6));
                textField6.setText((String) table1.getValueAt(index, 7));
                textField7.setText((String) table1.getValueAt(index, 8));
                String sql = "SELECT bkNo,odQuantity FROM OrderDetails WHERE ordNo=?";
                String[] ps = new String[]{(String) table1.getValueAt(index, 0)};
                ResultSet resultSet = SqlFunction.doSqlSelect(sql, ps, false);
                BookDetail bookDetail = new BookDetail();
                BookDetail.clearAllBook();
                try {
                    while (resultSet.next()) {
                        bookDetail.addBook(resultSet.getString(1), resultSet.getString(2));
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
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书销售管理(), 2)) {
                    return;
                }
                oldName = "防止重名没有作用DEFE32";
                String sqlLanguage, sql;
                if (LoginInfo.getDeptNo().equals("商家")) {
                    sqlLanguage = "INSERT INTO Orders(ordNo, selNo, ordTime, ordSendtime, ordQuantity, ordPayment, ordDiscount, prtRemark, ordState) " +
                            "VALUES(?,?,?,?,?,?,?,?,?) ";
                } else {
                    sqlLanguage = "INSERT INTO Orders(ordNo, selNo, empNo, ordTime, ordSendtime, ordQuantity, ordPayment, ordDiscount, prtRemark,ordState) " +
                            "VALUES(?,?,?,?,?,?,?,?,?,?) ";
                }
                String[] psString = getStrings();
                if (psString == null) {
                    return;
                }
                BasicOperation.add(sqlLanguage, psString);
                sql = "INSERT INTO OrderDetails(ordNo,bkNo,odQuantity) VALUES(?,?,?)";
                for (BookDetail.Book book : BookDetail.getAllBook()//将图书详情中的图书加入到订单详情中
                        ) {
                    String[] ps = new String[]{psString[0], book.getBookID(), book.getQuantity()};
                    SqlFunction.doSqlUpdate(sql, ps);
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
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书销售管理(), 2)) {
                    return;
                }
                isChange = true;
                String sqlLanguage, sql;
                if (LoginInfo.getDeptNo().equals("商家")) {
                    sqlLanguage = "UPDATE Orders SET ordNo = ?, selNo = ?, ordTime = ?, ordSendtime = ?, ordQuantity = ?, " +
                            "ordPayment = ?, ordDiscount = ?, prtRemark  = ?, ordState = ? " +
                            "WHERE ordNo = ?";
                } else {
                    sqlLanguage = "UPDATE Orders SET ordNo = ?, selNo = ?, empNo = ?, ordTime = ?, ordSendtime = ?, ordQuantity = ?, " +
                            "ordPayment = ?, ordDiscount = ?, prtRemark  = ?, ordState = ? " +
                            "WHERE ordNo = ?";
                }
                String[] ps = getStrings();
                if (ps == null) {
                    return;
                }
                BasicOperation.change(sqlLanguage, ps, oldName);
                //先删除订单详情原来的内容
                SqlFunction.doSqlUpdate("DELETE OrderDetails where ordNo = ?", new String[]{oldName});
                sql = "INSERT INTO OrderDetails(ordNo,bkNo,odQuantity) VALUES(?,?,?)";
                //在加入新内容
                for (BookDetail.Book book : BookDetail.getAllBook()//将图书详情中的图书加入到订单详情中
                        ) {
                    String[] psString = new String[]{ps[0], book.getBookID(), book.getQuantity()};
                    SqlFunction.doSqlUpdate(sql, psString);
                }
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                initTable();
                isChange = false;
            }
        });
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //删除
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书销售管理(), 2)) {
                    return;
                }
                String[] psString = {oldName};
                if (psString[0].equals("")) {
                    return;
                }
                int res = JOptionPane.showConfirmDialog(null, "是否删除”" + oldName + "“的信息", "是否修改", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    //先删除订单详情原来的内容
                    SqlFunction.doSqlUpdate("DELETE OrderDetails where ordNo = ?", new String[]{oldName});
                    String sqlLanguage = "DELETE Orders where ordNo = ?";
                    int x = SqlFunction.doSqlUpdate(sqlLanguage, psString);
                    if (x > 0) {
                        JOptionPane.showMessageDialog(null, "删除成功");
                    }
                }
                delButton.setEnabled(false);
                changeButton.setEnabled(false);
                initTable();
            }
        });
    }

    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("订单管理");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        dateComboBoxOrder = new DateComboBox(comboBox1, comboBox2, comboBox3);
        dateComboBoxShip = new DateComboBox(comboBox4, comboBox5, comboBox6);
        dateComboBoxShip.setNow();
        dateComboBoxOrder.setNow();
        initTable();
        initText();
        delButton.setEnabled(false);
        changeButton.setEnabled(false);
        BookDetail.clearAllBook();
        textField6.setText("1");
        if (LoginInfo.getDeptNo().equals("商家")) {
            textField2.setEnabled(false);
            textField6.setEnabled(false);
            textField2.setText("自动生成，无需填写");
        }
        textField1.setText("自动生成，无需填写");
        textField3.setText("自动生成，无需填写");
    }

    @Override
    public void initText() {

    }

    @Override
    public void initTable() {
        final Object[] columnNames = {"订单编号", "商家编号", "经办人编号", "订购时间", "预定发货时间", "总数量", "总价", "折扣", "备注", "订单状态"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);
        table1.addColumn(column);
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);
        String sqlLanguage = "SELECT * FROM Orders";
        if (LoginInfo.getDeptNo().equals("商家")) {//如果是商家登录，那么只能看到自己的订单
            sqlLanguage = "SELECT * FROM Orders WHERE selNo = " + LoginInfo.getLoginNo();
        }
        ControlFunction.setTable(sqlLanguage, new String[]{}, table1);
    }

    @Override
    public String[] getStrings() {
        String ordNo, selNo, empNo, ordTime, ordSendtime, ordQuantity, ordPayment, ordDiscount, prtRemark, ordState = "0";
        ordNo = getOrdNo();
        ordTime = dateComboBoxOrder.getDate();
        ordSendtime = dateComboBoxShip.getDate();
        ordQuantity = textField4.getText();
        ordPayment = textField5.getText();
        ordDiscount = textField6.getText();
        prtRemark = textField7.getText();
        String d1 = comboBox1.getSelectedItem() + "" + comboBox2.getSelectedItem() + "" + comboBox3.getSelectedItem();
        String d2 = comboBox4.getSelectedItem() + "" + comboBox5.getSelectedItem() + "" + comboBox6.getSelectedItem();
        if (Integer.valueOf(d1) >= Integer.valueOf(d2)) {
            JOptionPane.showMessageDialog(null, "订购时间应小于发货时间，当天订货最快次日发货!!");
            return null;
        }
        if (ordQuantity.equals("") || ordQuantity.equals("0")) {
            JOptionPane.showMessageDialog(null, "订购数量为0，请确认订购清单是否填写，或正确填写!!");
            return null;
        }
        if (isChange) {
            ordNo = oldName;
        }
        if (LoginInfo.getDeptNo().equals("商家")) {
            selNo = LoginInfo.getLoginNo();
            if (!isChange) {//如果是商家下单，折扣都是不打折
                ordDiscount = "1";
            }
            return new String[]{ordNo, selNo, ordTime, ordSendtime, ordQuantity, ordPayment, ordDiscount, prtRemark, ordState};
        } else {
            selNo = textField2.getText();
            if (selNo.equals("")) {
                JOptionPane.showMessageDialog(null, "请输入商家编号!!");
                return null;
            }
            empNo = LoginInfo.getLoginNo();
            return new String[]{ordNo, selNo, empNo, ordTime, ordSendtime, ordQuantity, ordPayment, ordDiscount, prtRemark, ordState};
        }
    }

    /**
     * 自动获取订单编号，如20181009001 前8位为日期，后三位为今日的序号
     *
     * @return 订单编号
     */
    private String getOrdNo() {
        String ordNo;
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String df = dateFormat.format(date);
        String sql = "SELECT top 1 ordNo FROM Orders ORDER BY ordNo desc";
        ResultSet resultSet = SqlFunction.doSqlSelect(sql, new String[]{}, false);
        ordNo = df + "001";
        try {
            while (resultSet.next()) {
                String last = resultSet.getString(1);
                int id = Integer.parseInt(last.substring(8, 11));
                last = last.substring(0, 8);
                if (last.equals(df)) {
                    String idNext = FillNumber.fill(String.valueOf(id + 1), 3);
                    ordNo = df + idNext;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordNo;
    }
}
