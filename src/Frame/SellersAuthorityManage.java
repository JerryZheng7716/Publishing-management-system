package Frame;

import Util.FillNumber;
import Util.LoginInfo;
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
import java.util.Objects;

public class SellersAuthorityManage extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton initPwdButton;


    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("商家权限管理");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        initTable();
        initText();

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                textField1.setText((String) table1.getValueAt(index, 0));
                textField2.setText((String) table1.getValueAt(index, 1));
                initPwdButton.setEnabled(true);
                super.mousePressed(e);
            }
        });

        initPwdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "UPDATE Sellers SET selPwd=? where selNo = ?";
                String[] ps = getStrings();
                SqlFunction.doSqlUpdate(sql, ps);
                JOptionPane.showMessageDialog(null, "成功重置商家密码");
            }
        });
    }

    @Override
    public void initText() {

    }

    @Override
    public void initTable() {
        final Object[] columnNames = {"商家编号", "商家名称", "联系人姓名", "联系电话"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);
        String sqlLanguage = "SELECT * FROM Sellers";
        OtherFunction.setTable(sqlLanguage, new String[]{}, table1);
    }

    @Override
    public String[] getStrings() {
        String selNo, selPwd = "c984aed014aec7623a54f0591da07a85fd4b762d";
        selNo = textField1.getText();
        return new String[]{selPwd, selNo};
    }
}
