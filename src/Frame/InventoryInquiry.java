package Frame;

import Util.NoName;
import Util.ControlFunction;
import Util.SqlFunction;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.Document;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryInquiry extends JFrame implements Frame {
    private JPanel panel1;
    private JTable table1;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private NoName noNameAuthor;
    private NoName noNameType;

    private void initListener() {
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String typeNo = noNameType.getNo(comboBox1.getSelectedItem().toString());
                String sqlLanguage = "SELECT bkPrtQty " +
                        "FROM Books,BookType " +
                        "WHERE Books.bkNo = BookType.bkNo " +
                        "AND BookType.typeNo = ? ";
                String[] ps = new String[]{typeNo};
                ResultSet resultSet = SqlFunction.doSqlSelect(sqlLanguage, ps, false);
                int typeCount = 0;
                try {
                    while (resultSet.next()) {
                        typeCount += resultSet.getInt(1);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                textField3.setText(String.valueOf(typeCount));
                doSearch();
            }
        });
        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                doSearch();
            }
        });
        Document dt = textField2.getDocument();
        dt.addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                doSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                doSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        Document dt1 = textField1.getDocument();
        dt1.addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                doSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                doSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                String bkNo = ((String) table1.getValueAt(index, 0));
                String typeNo = noNameType.getNo(comboBox1.getSelectedItem().toString());
                String sqlLanguage = "SELECT bkPrtQty " +
                        "FROM Books,BookType " +
                        "WHERE Books.bkNo = BookType.bkNo " +
                        "AND BookType.typeNo = ? ";
                String[] ps = new String[]{typeNo};
                ResultSet resultSet = SqlFunction.doSqlSelect(sqlLanguage, ps, false);
                int typeCount = 0;
                try {
                    while (resultSet.next()) {
                        typeCount += resultSet.getInt(1);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                String sqlLanguage1 = "SELECT bkPrtQty " +
                        "FROM Books " +
                        "WHERE Books.bkNo = ? ";
                String[] ps1 = new String[]{bkNo};
                int bkCount = 0;
                ResultSet resultSet1 = SqlFunction.doSqlSelect(sqlLanguage1, ps1, false);
                try {
                    while (resultSet1.next()) {
                        bkCount += resultSet1.getInt(1);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }


                String sqlLanguage2 = "SELECT bkPrtQty " +
                        "FROM Books";
                String[] ps2 = new String[]{};
                int allCount = 0;
                ResultSet resultSet2 = SqlFunction.doSqlSelect(sqlLanguage2, ps2, false);
                try {
                    while (resultSet2.next()) {
                        allCount += resultSet2.getInt(1);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                textField3.setText(String.valueOf(typeCount));
                textField4.setText(String.valueOf(bkCount));
                textField5.setText(String.valueOf(allCount));
                super.mousePressed(e);
            }
        });
    }

    /**
     * 进行对当前的筛选字段进行筛选操作
     */
    private void doSearch() {
        String typeNo = noNameType.getNo(comboBox1.getSelectedItem().toString());
        String bkNo = textField1.getText();
        String bkTitle = textField2.getText();
        String auNo = noNameAuthor.getNo(comboBox2.getSelectedItem().toString());
        if (typeNo.equals("全部")) {
            typeNo = "";
        }
        if (auNo.equals("全部")) {
            auNo = "";
        }
        initTable(typeNo, "%" + bkNo + "%", "%" + bkTitle + "%", auNo);
    }

    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("库存统计查询");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        noNameAuthor = new NoName("auNo", "auName", "Authors", true);
        noNameType = new NoName("typeNo", "typeTitle", "types", true);
        initTable();
        initText();
        initListener();
    }

    @Override
    public void initText() {
        noNameType.setNameComBoBox(comboBox1);
        noNameAuthor.setNameComBoBox(comboBox2);
    }

    @Override
    public void initTable() {
        initTable("%", "%", "%", "%");
    }

    public void initTable(String typeNo, String bkNo, String bkTitle, String auNo) {
        final Object[] columnNames = {"图书编号", "书名", "单价", "字数", "首版时间", "最新版次", "当前量", "所在仓库"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);
        String sqlLanguage = "SELECT DISTINCT Books.*  " +
                "FROM Books,BookType,Authored " +
                "WHERE Books.bkNo = BookType.bkNo " +
                "AND Books.bkNo = Authored.bkNo " +
                "AND BookType.bkNo = Authored.bkNo " +
                "AND BookType.typeNo like ? " +
                "AND Authored.auNo like ? " +
                "AND Books.bkNo like ? " +
                "AND Books.bkTitle like ? ";
        String[] ps = new String[]{typeNo, auNo, bkNo, bkTitle};
        ControlFunction.setTable(sqlLanguage, ps, table1);
    }

    @Override
    public String[] getStrings() {
        return new String[0];
    }
}
