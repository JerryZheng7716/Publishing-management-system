package Frame;

import Util.BookDetail;
import Util.GBC;
import Util.ControlFunction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BookDetails extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton okButton;
    private JPanel detailPanel;
    private ArrayList<BookItem> itemArrayList;
    private JTextField focusTextField;

    public BookDetails() {
        itemArrayList = new ArrayList<>();
        addItem();
        JFrame jFrame = this;
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookDetail bookDetail = new BookDetail();
                BookDetail.clearAllBook();
                for (BookItem item : itemArrayList) {
                    if (!item.getBookid().equals("")) {
                        try {
                            Integer.parseInt(item.getBookQuantity());
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, "您的订单数量填写有误，请填写有效数字");
                            return;
                        }
                        bookDetail.addBook(item.getBookid(), item.getBookQuantity());
                    }
                }
                jFrame.dispose();
            }
        });
    }

    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("购书详单");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(300, 200);
        this.setVisible(true);
        initTable();
        ArrayList<BookDetail.Book> bookArrayList = BookDetail.getAllBook();
        if (bookArrayList.size() != 0) {
            itemArrayList.get(0).idTextField.setText(bookArrayList.get(0).getBookID());
            itemArrayList.get(0).quantityTextField.setText(bookArrayList.get(0).getQuantity());
        }
        for (int i = 1; i < bookArrayList.size(); i++) {
            addItem();
            itemArrayList.get(i).idTextField.setText(bookArrayList.get(i).getBookID());
            itemArrayList.get(i).quantityTextField.setText(bookArrayList.get(i).getQuantity());
        }

    }

    private void initTable() {
        final Object[] columnNames = {"图书编号", "书名", "单价", "字数", "首版时间", "最新版次", "当前量"};
        Object[][] rowData = {};
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(dataModel);
        String sqlLanguage = "SELECT * FROM Books";
        ControlFunction.setTable(sqlLanguage, new String[]{}, table1);

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table1.getSelectedRow();
                if (focusTextField == null) {//如果用户从未点击输入框，那么将字段写入第一个输入框
                    itemArrayList.get(0).idTextField.setText((String) table1.getValueAt(index, 0));
                } else {
                    focusTextField.setText((String) table1.getValueAt(index, 0));
                }
                super.mousePressed(e);
            }
        });
    }

    private void addItem() {
        BookItem bookItem = new BookItem();
        bookItem.remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("点击了减少" + itemArrayList.size());
                detailPanel.remove(bookItem.jPanel);
                itemArrayList.remove(bookItem);
                BookItem bookItemLast = itemArrayList.get(itemArrayList.size() - 1);
                bookItemLast.add.setVisible(true);
                bookItemLast.jPanel.remove(bookItemLast.nonePanel1);
                detailPanel.revalidate();
                detailPanel.repaint();
                super.mouseClicked(e);
            }
        });
        detailPanel.add(bookItem.jPanel, new GBC(0, itemArrayList.size() * 4, 3, 3).
                setFill(GBC.BOTH).setIpad(0, 0).setWeight(0, 0));
        itemArrayList.add(bookItem);
        for (int i = 0; i < itemArrayList.size() - 1; i++) {
            itemArrayList.get(i).add.setVisible(false);
            BookItem item = itemArrayList.get(i);
            item.jPanel.add(item.nonePanel1);
        }
        BookItem item = itemArrayList.get(0);
        item.remove.setVisible(false);
        item.jPanel.add(item.nonePanel2);
        detailPanel.revalidate();
        detailPanel.repaint();
    }

    private class BookItem {
        private JLabel idLabel, quantityLabel, add, remove;
        private JTextField idTextField, quantityTextField;
        private JPanel jPanel = new JPanel();
        private JPanel nonePanel1 = new JPanel();
        private JPanel nonePanel2 = new JPanel();

        private BookItem() {
            JPanel none = new JPanel();
            JPanel none2 = new JPanel();
            none.setSize(3, 1);
            none2.setSize(1, 1);
            nonePanel1.setSize(1, 1);
            nonePanel2.setSize(1, 1);

            idLabel = new JLabel("图书编号");
            quantityLabel = new JLabel("订购数量");
            add = new JLabel("+");
            remove = new JLabel("-");
            idTextField = new JTextField(15);
            quantityTextField = new JTextField(15);

            jPanel.add(this.idLabel);
            jPanel.add(this.idTextField);
            jPanel.add(none);
            jPanel.add(this.quantityLabel);
            jPanel.add(this.quantityTextField);
            jPanel.add(this.remove);
            jPanel.add(none2);
            jPanel.add(this.add);
            add.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    addItem();
                    super.mouseClicked(e);
                }

            });

            idTextField.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    focusTextField = idTextField;
                    System.out.println("点击了idTextField");
                    super.mousePressed(e);
                }
            });
        }

        private String getBookid() {
            return idTextField.getText();
        }

        private String getBookQuantity() {
            return quantityTextField.getText();
        }
    }

    public static void main(String[] args) {
        BookDetails bookDetails = new BookDetails();
        bookDetails.showFrame();
    }
}
