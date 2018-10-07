package Frame;

import Util.LoginInfo;
import Util.OtherFunction;
import Util.SqlFunction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ship extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextPane textPane1;
    private JButton addButton;
    private JButton delButton;
    private String ordNo;

    public Ship() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                textField1.setText((String) table1.getValueAt(index, 0));
                textField2.setText((String) table1.getValueAt(index, 1));
                textField3.setText((String) table1.getValueAt(index, 2));
                textField4.setText((String) table1.getValueAt(index, 3));
                textField5.setText((String) table1.getValueAt(index, 4));
                textField6.setText((String) table1.getValueAt(index, 5));
                textField7.setText((String) table1.getValueAt(index, 6));
                textField8.setText((String) table1.getValueAt(index, 7));
                textField9.setText((String) table1.getValueAt(index, 8));
                delButton.setEnabled(true);
                addButton.setEnabled(true);
                ordNo=(String) table1.getValueAt(index, 0);

                String sql = "SELECT bkNo,odQuantity FROM OrderDetails WHERE ordNo = ?";
                String[] ps = new String[]{ordNo};
                ResultSet resultSet = SqlFunction.doSqlSelect(sql,ps,false);
                String bookDetails = "";
                int count = 0;
                try{
                    while (resultSet.next()){
                        if (count%2==0 && count!=0){
                            bookDetails = bookDetails+"\n";
                        }
                        bookDetails = bookDetails+"图书编号："+resultSet.getString(1)+"; ";
                        bookDetails = bookDetails+"订购数量："+resultSet.getString(2)+"; ";
                        count++;
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                textPane1.setText(bookDetails);
                super.mousePressed(e);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书销售管理(),2)){
                    return;
                }
                int res = JOptionPane.showConfirmDialog(null, "请核对销售单和出库单，核对信息是否确认无误", "是否确认无误", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    String sql = "UPDATE Orders SET ordState ='2' WHERE ordNo=?";
                    String[] ps = new String[]{ordNo};
                    SqlFunction.doSqlUpdate(sql,ps);
                    JOptionPane.showMessageDialog(null, "发货成功");
                }
            }
        });
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书销售管理(),2)){
                    return;
                }
                int res = JOptionPane.showConfirmDialog(null, "是否确认取消发货？", "确定取消发货", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    String sql = "UPDATE Orders SET ordState ='1' WHERE ordNo=?";
                    String[] ps = new String[]{ordNo};
                    SqlFunction.doSqlUpdate(sql,ps);
                    JOptionPane.showMessageDialog(null, "取消成功");
                }
            }
        });
    }

    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("发货管理");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        initTable();
        delButton.setEnabled(false);
        addButton.setEnabled(false);
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
        String sqlLanguage = "SELECT * FROM Orders WHERE ordState ='1' OR ordState ='2'";
        OtherFunction.setTable(sqlLanguage, new String[]{}, table1);
    }

    @Override
    public String[] getStrings() {
        return new String[0];
    }
}
