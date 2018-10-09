package Frame;

import Util.BasicOperation;
import Util.LoginInfo;
import Util.ControlFunction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OutStock extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JButton addButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton changeButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private String oldId = "0";

    public OutStock() {
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
                oldId = (String) table1.getValueAt(index, 0);
                comboBox1.setSelectedIndex(Integer.parseInt((String) table1.getValueAt(index, 6)));
                addButton.setEnabled(true);
                changeButton.setEnabled(true);
                super.mousePressed(e);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书库存管理(), 2)) {
                    return;
                }
                String sqlLanguage = "UPDATE OutOfStock SET oosState = '1'" +
                        "where oosId = ?";
                String[] psString = new String[]{oldId};
                BasicOperation.change(sqlLanguage, new String[]{}, oldId);
                changeButton.setEnabled(false);
                addButton.setEnabled(false);
                initTable(String.valueOf(comboBox2.getSelectedIndex()));
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书库存管理(), 2)) {
                    return;
                }
                String sqlLanguage = "UPDATE OutOfStock SET oosQuantity=?,empNo=?,oosTime=?,oosUse=?,oosState=? " +
                        "where oosId = ?";
                String[] psString = getStrings();
                BasicOperation.change(sqlLanguage, psString, oldId);
                changeButton.setEnabled(false);
                addButton.setEnabled(false);
                initTable(String.valueOf(comboBox2.getSelectedIndex()));
            }
        });
        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                initTable(String.valueOf(comboBox2.getSelectedIndex()));
            }
        });
    }

    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("缺货记录管理");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        initTable();
        initText();
        changeButton.setEnabled(false);
        addButton.setEnabled(false);
    }

    @Override
    public void initText() {
        comboBox1.addItem("未处理");
        comboBox1.addItem("已补货");
        comboBox2.addItem("未处理");
        comboBox2.addItem("已补货");
    }

    @Override
    public void initTable() {
        initTable("%");
    }

    /**
     * 因为这个Table支持筛选，所以对接口的initTable方法进行重载，改成具有传入参数的的方法;
     *
     * @param state 筛选条件，既缺货状态
     */
    public void initTable(String state) {
        final Object[] columnNames = {"流水号", "图书编号", "数量", "记录人编号", "记录时间", "用途", "状态"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);
        table1.addColumn(column);
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);
        String sqlLanguage = "SELECT * FROM OutOfStock WHERE oosState like ?";
        ControlFunction.setTable(sqlLanguage, new String[]{state}, table1);
    }

    @Override
    public String[] getStrings() {
        String oosQuantity, empNo, oosTime, oosUse, oosState;
        oosQuantity = textField3.getText();
        empNo = LoginInfo.getLoginNo();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        oosTime = dateFormat.format(date);
        oosUse = textField6.getText();
        try {
            int x = Integer.parseInt(oosQuantity);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "缺货数量非有效数字！");
        }
        oosState = String.valueOf(comboBox1.getSelectedIndex());
        return new String[]{oosQuantity, empNo, oosTime, oosUse, oosState};
    }
}
