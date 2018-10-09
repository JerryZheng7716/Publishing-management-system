package Frame;

import Util.LoginInfo;
import Util.NoName;
import Util.ControlFunction;
import Util.SqlFunction;

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

public class WarehouseManage extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox comboBox1;
    private JButton addButton;
    private JButton changeButton;
    private JButton delButton;
    private NoName noName;
    private String oldName = "防止重名没有作用DEFE32";

    public WarehouseManage() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                textField1.setText((String) table1.getValueAt(index, 0));
                ControlFunction.setComboBoxSelect(comboBox1, noName.getName((String) table1.getValueAt(index, 1)));
                textField2.setText((String) table1.getValueAt(index, 2));
                textField3.setText(((String) table1.getValueAt(index, 3)).trim());
                textField4.setText((String) table1.getValueAt(index, 4));
                textField5.setText((String) table1.getValueAt(index, 5));
                delButton.setEnabled(true);
                changeButton.setEnabled(true);
                oldName = textField1.getText();
                super.mousePressed(e);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //添加仓库
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(), 2)) {
                    return;
                }
                oldName = "防止重名没有作用DEFE32";
                String sqlLanguage = "INSERT Warehouse VALUES(?,?,?,?,?,?)";
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
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改仓库
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(), 2)) {
                    return;
                }
                String sqlLanguage = "UPDATE Warehouse SET whNo=?, empNo=?, whTelephone=?, whAddress=?, whArea=?, whRemark=?" +
                        " where whNo = ?";
                String[] psString = new String[7];
                String[] ps = getStrings();
                if (ps == null) {
                    return;
                }
                System.arraycopy(ps, 0, psString, 0, 6);
                psString[6] = oldName;
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
                //删除仓库
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(), 2)) {
                    return;
                }
                String sqlLanguage = "DELETE Warehouse where whNo = ?";
                String[] psString = {oldName};
                if (psString[0].equals("")) {
                    return;
                }
                int res = JOptionPane.showConfirmDialog(null, "是否删除”" + oldName + "“的信息", "是否修改", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    int x = SqlFunction.doSqlUpdate(sqlLanguage, psString);
                    if (x > 0) {

                        JOptionPane.showMessageDialog(null, "删除成功");
                        initTable();
                        delButton.setEnabled(false);
                        changeButton.setEnabled(false);
                    }
                } else {
                    return;
                }
            }
        });
    }

    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("仓库管理");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        initTable();
        initText();
        delButton.setEnabled(false);
        changeButton.setEnabled(false);
        noName = new NoName("empNo", "empName", "Employee");
    }

    @Override
    public void initText() {
        String sql = "SELECT empName FROM Employee";
        ControlFunction.setComboBoxItem(sql, comboBox1);
    }

    @Override
    public void initTable() {
        final Object[] columnNames = {"仓库编号", "仓库负责人", "联系电话", "所在位置", "面积", "备注"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);

        String sqlLanguage = "SELECT * FROM Warehouse";
        ControlFunction.setTable(sqlLanguage, new String[]{}, table1);
    }

    @Override
    public String[] getStrings() {
        String whNo, empNo, whTelephone, whAddress, whArea, whRemark;
        whNo = textField1.getText();
        whTelephone = textField2.getText();
        whAddress = textField3.getText();
        whArea = textField4.getText();
        whRemark = textField5.getText();
        try {
            if (Integer.valueOf(whNo) >= 100) {
                JOptionPane.showMessageDialog(null, "仓库编号为2位数字!!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "仓库编号必须为2位数字!!");
        }
        empNo = noName.getNo(Objects.requireNonNull(comboBox1.getSelectedItem()).toString());
        String sqlLanguage1 = "SELECT * FROM Warehouse WHERE whNo = ?";
        String[] psString1 = {whNo};
        try {
            if (SqlFunction.doSqlSelect(sqlLanguage1, psString1, false).next()
                    && !whNo.equals(oldName)) {
                JOptionPane.showMessageDialog(null, "已经存在当前仓库编号!!");
                oldName = whNo;
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[]{whNo, empNo, whTelephone, whAddress, whArea, whRemark};
    }
}
