package Frame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JPanel panel1;
    private JTable table1;

    private  void  init(){
        final Object[] columnNames = {"姓名", "性别", "家庭地址","电话号码", "生日", "工作", "收入", "婚姻状况","恋爱状况"};
        Object[][] rowData = {                {"ddd", "男", "江苏南京", "1378313210", "03/24/1985", "学生", "寄生中", "未婚", "没"},
                {"eee", "女", "江苏南京", "13645181705", "xx/xx/1985", "家教", "未知", "未婚", "好象没"},
                {"fff", "男", "江苏南京", "13585331486", "12/08/1985", "汽车推销员", "不确定", "未婚", "有"},
                {"ggg", "女", "江苏南京", "81513779", "xx/xx/1986", "宾馆服务员", "确定但未知", "未婚", "有"},
                {"hhh", "男", "江苏南京", "13651545936", "xx/xx/1985", "学生", "流放中", "未婚", "无数次分手后没有"}
        };        Object obj = "Hello";
        TableColumn column = new TableColumn();
        column.setHeaderValue(columnNames);//
        table1.addColumn(column);//
        TableModel dataModel = new DefaultTableModel(rowData,columnNames);
        table1.setModel(dataModel);
        table1.updateUI();
    }

    public static void main(String[] args) {

        JFrame jFrame = new JFrame("出版社管理系统");
        Main main =new Main();
        jFrame.setContentPane(main.panel1);
        main.init();

        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu = new JMenu("图书印刷管理");
        JMenuItem menuFirstPrint = new JMenuItem("首版书印刷");
        JMenuItem menuAddPrint = new JMenuItem("加印");
        jMenu.add(menuFirstPrint);
        jMenu.add(menuAddPrint);
        jMenuBar.add(jMenu);
        //图书印刷管理

        JMenu jMenu1 = new JMenu("图书库存管理");
        JMenuItem menuBookAdd = new JMenuItem("图书入库");
        JMenuItem menuBookOut = new JMenuItem("图书出库");
        jMenu1.add(menuBookAdd);
        jMenu1.add(menuBookOut);
        jMenuBar.add(jMenu1);
        //图书库存管理

        JMenu jMenu2 = new JMenu("图书销售管理");
        JMenuItem menuOrder = new JMenuItem("订货");
        JMenuItem menuShip = new JMenuItem("发货");
        jMenu2.add(menuOrder);
        jMenu2.add(menuShip);
        jMenuBar.add(jMenu2);
        //图书销售管理

        JMenu jMenu3 = new JMenu("基本信息管理");
        JMenuItem menuDeptManage = new JMenuItem("部门管理");
        JMenuItem menuEmployeeManage = new JMenuItem("员工管理");
        JMenuItem menuBookManage = new JMenuItem("图书管理");
        JMenuItem menuAuthorManage = new JMenuItem("作者管理");
        JMenuItem menuMerchantManage = new JMenuItem("商家管理");
        jMenu3.add(menuDeptManage);
        jMenu3.add(menuEmployeeManage);
        jMenu3.add(menuBookManage);
        jMenu3.add(menuAuthorManage);
        jMenu3.add(menuMerchantManage);
        jMenuBar.add(jMenu3);
        DeptManage deptManage = new DeptManage();
        menuDeptManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deptManage.showFrame();
            }

        });
        EmployeeManage employeeManage = new EmployeeManage();
        menuEmployeeManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeeManage.showFrame();
            }

        });
        //基本信息管理

        JMenu jMenu4 = new JMenu("查询统计");
        JMenuItem menuInventoryInquiry = new JMenuItem("库存查询统计");
        JMenuItem menuSalesInquiry = new JMenuItem("销售查询统计");
        jMenu4.add(menuInventoryInquiry);
        jMenu4.add(menuSalesInquiry);
        jMenuBar.add(jMenu4);
        //查询统计


        JMenu jMenu5 = new JMenu("系统维护");
        JMenuItem menuUserManage = new JMenuItem("用户管理");
        JMenuItem menuDataMaintenance = new JMenuItem("数据维护");
        jMenu5.add(menuUserManage);
        jMenu5.add(menuDataMaintenance);
        jMenuBar.add(jMenu5);
        //系统维护
        jFrame.setJMenuBar(jMenuBar);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setSize(800,500);
        jFrame.setLocation(500,260);

    }

}
