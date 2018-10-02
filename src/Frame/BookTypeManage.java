package Frame;

import Util.BasicOperation;
import Util.FillNumber;
import Util.OtherFunction;
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

public class BookTypeManage extends JFrame implements Frame {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton addButton;
    private JButton changeButton;
    private JButton delButton;
    private JTable table1;
    private String oldName = "防止重名没有作用DEFE32";

    BookTypeManage() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                textField1.setText((String) table1.getValueAt(index, 0));
                textField2.setText((String) table1.getValueAt(index, 1));
                textField3.setText((String) table1.getValueAt(index, 2));
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
                String sqlLanguage = "INSERT Types VALUES(?,?,?) ";
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
                String sqlLanguage = "UPDATE Types SET typeNo=?, typeTitle=?, typeRemark=? where typeNo = ?";
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
                String sqlLanguage = "DELETE Types where typeNo = ?";
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
        this.setTitle("图书类目管理");
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

    }

    @Override
    public void initTable() {
        final Object[] columnNames = {"类型编号", "类型名称", "备注"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);

        String sqlLanguage = "SELECT * FROM Types";
        OtherFunction.setTable(sqlLanguage, new String[]{}, table1);
    }

    @Override
    public String[] getStrings() {
        String typeNo, typeTitle, typeRemark;
        typeNo = textField1.getText();
        typeTitle = textField2.getText();
        typeRemark = textField3.getText();
        if (typeNo.equals("")) {
            JOptionPane.showMessageDialog(null, "类型编号不能为空!!");
            return null;
        }
        try{
            if (Integer.parseInt(typeNo) >= 100) {
                JOptionPane.showMessageDialog(null, "类型编号为2位数字");
                return null;
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "类型编号为2位数字");
        }
        typeNo=FillNumber.fill(typeNo,2);
        String sqlLanguage1 = "SELECT * FROM Types WHERE typeNo = ?";
        String[] psString1 = {typeNo};
        try {
            if (SqlFunction.doSqlSelect(sqlLanguage1, psString1, false).next()
                    && !typeNo.equals(oldName)) {
                JOptionPane.showMessageDialog(null, "已经存在当前类型编号!!");
                oldName = typeNo;
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[]{typeNo, typeTitle, typeRemark};
    }
}
