package Frame;

import Util.OtherFunction;
import Util.SqlFunction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class DeptManage extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JButton addButton;
    private JButton delButton1;
    private JButton changeButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JScrollPane scroll;
    private String oldName = "防止重名没有作用DEFE32";

//    public DeptManage(){
//        initTable();
//    }

    DeptManage() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //点击表单获取数据
                int index = table1.getSelectedRow();
                textField1.setText((String) table1.getValueAt(index, 0));
                textField2.setText((String) table1.getValueAt(index, 1));
                textField3.setText((String) table1.getValueAt(index, 2));
                textField4.setText((String) table1.getValueAt(index, 3));
                textField5.setText((String) table1.getValueAt(index, 4));
                delButton1.setEnabled(true);
                changeButton.setEnabled(true);
                oldName = textField1.getText();
                super.mouseClicked(e);
            }
        });
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //添加部门
                oldName = "防止重名没有作用DEFE32";
                String sqlLanguage = "INSERT Departments VALUES(?,?,?,?,?) ";
                String[] psString = getStrings();
                if (psString == null) {
                    return;
                }
                int x = SqlFunction.doSqlUpdate(sqlLanguage, psString);
                if (x > 0) {
                    JOptionPane.showMessageDialog(null, "插入成功");
                    initTable();
                    delButton1.setEnabled(false);
                    changeButton.setEnabled(false);
                }
                super.mouseClicked(e);
            }
        });
        changeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //修改部门
                String sqlLanguage = "UPDATE Departments SET deptNO = ?,deptTitle = ?,deptManager = ?, deptTelephone = ? ,deptAddress = ? where deptNO = ?";
                String[] psString = new String[6];
                String[] ps = getStrings();
                if (ps==null){
                    return;
                }
                System.arraycopy(ps, 0, psString, 0, 5);
                psString[5]=oldName;
                if (psString[0].equals("")) {
                    return;
                }
                int x = SqlFunction.doSqlUpdate(sqlLanguage, psString);
                if (x > 0) {
                    JOptionPane.showMessageDialog(null, "修改成功");
                    initTable();
                    delButton1.setEnabled(false);
                    changeButton.setEnabled(false);
                }
                super.mouseClicked(e);
            }
        });
        delButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //删除管理员
                String sqlLanguage = "DELETE Departments where deptNO = ?";
                String[] psString = {oldName};
                if (psString[0].equals("")) {
                    return;
                }
                int res=JOptionPane.showConfirmDialog(null, "是否删除”"+oldName+"“的信息", "是否修改", JOptionPane.YES_NO_OPTION);
                if(res==JOptionPane.YES_OPTION){
                    int x = SqlFunction.doSqlUpdate(sqlLanguage, psString);
                    if (x > 0) {

                        JOptionPane.showMessageDialog(null, "删除成功");
                        initTable();
                        delButton1.setEnabled(false);
                        changeButton.setEnabled(false);
                    }
                }else{
                    return;
                }
                super.mouseClicked(e);
            }
        });
    }

    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("部门管理");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        initTable();
        initText();
        delButton1.setEnabled(false);
        changeButton.setEnabled(false);
    }

    @Override
    public void initTable() {
        final Object[] columnNames = {"部门编号", "部门名称", "部门负责人", "负责人电话", "部门地址"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);

        String sqlLanguage = "SELECT * FROM Departments";
        OtherFunction.selectTable(sqlLanguage, new String[]{}, table1);
    }

    @Override
    public void initText() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }

    @Override
    public String[] getStrings() {//获取增删改的内容
        String deptNO, deptTitle, deptManager, deptTelephone, deptAddress;
        deptNO = textField1.getText();
        deptTitle = textField2.getText();
        deptManager = textField3.getText();
        deptTelephone = textField4.getText();
        deptAddress = textField5.getText();

        if (deptNO.equals("")) {
            JOptionPane.showMessageDialog(null, "部门编号不能为空!!");
            return null;
        }
        if (Integer.parseInt(deptNO) >= 100) {
            JOptionPane.showMessageDialog(null, "部门编号为2位数字");
            return null;
        }
        if (deptTitle.equals("")) {
            JOptionPane.showMessageDialog(null, "部门名不能为空!!");
            return null;
        }
        if (deptManager.equals("")) {
            JOptionPane.showMessageDialog(null, "部门负责人不能为空!!");
            return null;
        }
        if (deptTelephone.equals("")) {
            JOptionPane.showMessageDialog(null, "负责人电话不能为空!!");
            return null;
        }
        if (deptAddress.equals("")) {
            JOptionPane.showMessageDialog(null, "部门地址不能为空!!");
            return null;
        }
        String sqlLanguage1 = "SELECT * FROM Departments WHERE deptNO = ?";
        String[] psString1 = {deptNO};
        try {
            if (SqlFunction.doSqlSelect(sqlLanguage1, psString1, false).next()
                    && !deptNO.equals(oldName)) {
                JOptionPane.showMessageDialog(null, "已经存在当前部门编号!!");
                oldName = deptNO;
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[]{deptNO, deptTitle, deptManager, deptTelephone,deptAddress};
    }
}
