package Frame;

import Util.LoginInfo;
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

public class PressAuthorityManage extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JRadioButton printRB0;
    private JRadioButton printRB1;
    private JRadioButton printRB2;
    private JRadioButton inventoryRB0;
    private JRadioButton inventoryRB1;
    private JRadioButton inventoryRB2;
    private JRadioButton sellRB0;
    private JRadioButton sellRB1;
    private JRadioButton sellRB2;
    private JRadioButton infoRB0;
    private JRadioButton infoRB1;
    private JRadioButton infoRB2;
    private JRadioButton searchRB0;
    private JRadioButton searchRB1;
    private JRadioButton searchRB2;
    private JRadioButton sysRB0;
    private JRadioButton sysRB1;
    private JRadioButton sysRB2;
    private JRadioButton permissionRB0;
    private JRadioButton permissionRB1;
    private JRadioButton permissionRB2;
    private JRadioButton checkRB0;
    private JRadioButton checkRB2;
    private JButton okButton;
    private JTextField textFieldId;
    private JTextField textFieldName;
    private JButton allButton;
    private JButton clearButton;

    public PressAuthorityManage() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                textFieldId.setText((String) table1.getValueAt(index, 0));
                textFieldName.setText((String) table1.getValueAt(index, 1));
                setRbSelect((String) table1.getValueAt(index, 6));
                okButton.setEnabled(true);
                super.mousePressed(e);
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx权限管理(), 2)) {
                    return;
                }
                String sql = "UPDATE Employee SET empAuthority = ? WHERE empNo = ?";
                String[] ps = getStrings();
                if (ps == null) {
                    return;
                }
                int res = JOptionPane.showConfirmDialog(null, "是否修改记录”" + ps[1] + "“的权限", "是否修改", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    SqlFunction.doSqlUpdate(sql, ps);
                    JOptionPane.showMessageDialog(null, "修改成功");
                    initTable();
                    okButton.setEnabled(false);
                }
            }
        });
        allButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBigGod();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCivilian();
            }
        });
    }

    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("出版社权限管理");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        initTable();
        initText();
        okButton.setEnabled(false);
    }

    @Override
    public void initText() {
        ButtonGroup printRB = new ButtonGroup(), inventoryRB = new ButtonGroup(), sellRB = new ButtonGroup(),
                infoRB = new ButtonGroup(), searchRB = new ButtonGroup(), sysRB = new ButtonGroup(), permissionRB = new ButtonGroup(),
                checkRB = new ButtonGroup();

        printRB.add(printRB0);
        printRB.add(printRB1);
        printRB.add(printRB2);

        inventoryRB.add(inventoryRB0);
        inventoryRB.add(inventoryRB1);
        inventoryRB.add(inventoryRB2);

        sellRB.add(sellRB0);
        sellRB.add(sellRB1);
        sellRB.add(sellRB2);

        infoRB.add(infoRB0);
        infoRB.add(infoRB1);
        infoRB.add(infoRB2);

        searchRB.add(searchRB0);
        searchRB.add(searchRB1);
        searchRB.add(searchRB2);

        sysRB.add(sysRB0);
        sysRB.add(sysRB1);
        sysRB.add(sysRB2);

        permissionRB.add(permissionRB0);
        permissionRB.add(permissionRB1);
        permissionRB.add(permissionRB2);

        checkRB.add(checkRB0);
        checkRB.add(checkRB2);

        setCivilian();
    }

    @Override
    public void initTable() {
        final Object[] columnNames = {"员工编号", "员工姓名", "性别", "联系电话", "邮箱", "所属部门", "权限"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);
        String sqlLanguage = "SELECT empNo,empName,empSex,empTelephone,empEmail,deptNo,empAuthority FROM Employee";
        ControlFunction.setTable(sqlLanguage, new String[]{}, table1);
    }

    @Override
    public String[] getStrings() {
        String loginAuthority = "";
        loginAuthority = loginAuthority + getRbSelect(printRB0, printRB2) + getRbSelect(inventoryRB0, inventoryRB2)
                + getRbSelect(sellRB0, sellRB2) + getRbSelect(infoRB0, infoRB2)
                + getRbSelect(searchRB0, searchRB2) + getRbSelect(sysRB0, sysRB2)
                + getRbSelect(permissionRB0, permissionRB2) + getRbSelect(checkRB0, checkRB2);
        return new String[]{loginAuthority, textFieldId.getText()};
    }

    /**
     * 将选择的按钮，翻译成012三种值
     *
     * @param none 没有权限按钮
     * @param god  完全控制按钮
     * @return 应该是对应哪一种权限的值
     */
    private String getRbSelect(JRadioButton none, JRadioButton god) {
        if (none.isSelected()) {//如果不具备权限被选中返回0
            return "0";
        } else if (god.isSelected()) {//如果完全控制被选中返回2
            return "2";
        } else {//否则返回1
            return "1";
        }
    }

    /**
     * 将8位权限值，设置到8组权限单选框中
     *
     * @param authority 权限值
     */
    private void setRbSelect(String authority) {
        String[] s = new String[8];
        for (int i = 0; i < s.length; i++) {
            s[i] = authority.substring(i, i + 1);
        }
        if (s[0].equals("0")) {
            printRB0.setSelected(true);
        } else if (s[0].equals("1")) {
            printRB1.setSelected(true);
        } else {
            printRB2.setSelected(true);
        }

        if (s[1].equals("0")) {
            inventoryRB0.setSelected(true);
        } else if (s[1].equals("1")) {
            inventoryRB1.setSelected(true);
        } else {
            inventoryRB2.setSelected(true);
        }

        if (s[2].equals("0")) {
            sellRB0.setSelected(true);
        } else if (s[2].equals("1")) {
            sellRB1.setSelected(true);
        } else {
            sellRB2.setSelected(true);
        }

        if (s[3].equals("0")) {
            infoRB0.setSelected(true);
        } else if (s[3].equals("1")) {
            infoRB1.setSelected(true);
        } else {
            infoRB2.setSelected(true);
        }

        if (s[4].equals("0")) {
            searchRB0.setSelected(true);
        } else if (s[4].equals("1")) {
            searchRB1.setSelected(true);
        } else {
            searchRB2.setSelected(true);
        }

        if (s[5].equals("0")) {
            sysRB0.setSelected(true);
        } else if (s[5].equals("1")) {
            sysRB1.setSelected(true);
        } else {
            sysRB2.setSelected(true);
        }

        if (s[6].equals("0")) {
            permissionRB0.setSelected(true);
        } else if (s[6].equals("1")) {
            permissionRB1.setSelected(true);
        } else {
            permissionRB2.setSelected(true);
        }

        if (s[7].equals("0")) {
            checkRB0.setSelected(true);
        } else {
            checkRB2.setSelected(true);
        }


    }

    /**
     * 选中所有无权限单选框
     */
    private void setCivilian() {
        setGodOrCivilian(printRB0, inventoryRB0, sellRB0, infoRB0, searchRB0, sysRB0, permissionRB0, checkRB0);
    }

    /**
     * 选中所有完全控制单选框
     */
    private void setBigGod() {
        setGodOrCivilian(printRB2, inventoryRB2, sellRB2, infoRB2, searchRB2, sysRB2, permissionRB2, checkRB2);
    }

    private void setGodOrCivilian(JRadioButton printRB2, JRadioButton inventoryRB2, JRadioButton sellRB2, JRadioButton infoRB2, JRadioButton searchRB2, JRadioButton sysRB2, JRadioButton permissionRB2, JRadioButton checkRB2) {
        printRB2.setSelected(true);
        inventoryRB2.setSelected(true);
        sellRB2.setSelected(true);
        infoRB2.setSelected(true);
        searchRB2.setSelected(true);
        sysRB2.setSelected(true);
        permissionRB2.setSelected(true);
        checkRB2.setSelected(true);
    }
}
