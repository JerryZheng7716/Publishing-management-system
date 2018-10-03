package Frame;

import Util.LoginInfo;
import Util.OtherFunction;
import Util.SqlFunction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private JPanel panel1;
    private JTextField textField1;
    private JButton 登录Button;
    private JPanel background;
    private JPasswordField passwordField1;
    private JLabel tittle;
    private JLabel gonghao;
    private JLabel 密码Label;
    private JMenuBar jMenuBar = new JMenuBar();


    private  void  init(){
//        jMenuBar.setVisible(false);
        initLogin();
        showFrame();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
    }

    private void initLogin(){
        登录Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empNo,empPwd;
                empNo = textField1.getText();
                empPwd = new String(passwordField1.getPassword());
                OtherFunction otherFunction = new OtherFunction();
                if(LoginInfo.isIsLogin()){
                    tittle.setText("登录您的账号");
                    登录Button.setText("登录");
                    LoginInfo.setIsLogin(false);
                    LoginInfo.setEmpNo("");
                    LoginInfo.setEmpName("");
                    gonghao.setText("工号");
                    textField1.setText("");
                    textField1.setEnabled(true);
                    passwordField1.setVisible(true);
                    passwordField1.setEnabled(true);
                    passwordField1.setText("");
                    密码Label.setVisible(true);
                    jMenuBar.setVisible(false);
                }else {
                    if (OtherFunction.Login(empNo,empPwd).equals("Success")){
                        tittle.setText("欢迎您");
                        登录Button.setText("注销登录");
                        LoginInfo.setIsLogin(true);
                        LoginInfo.setEmpNo(empNo);
                        ResultSet resultSet = SqlFunction.doSqlSelect("SELECT empName FROM  Employee WHERE empNo = ?",new String[]{empNo},false);
                        try {
                            resultSet.next();
                            LoginInfo.setEmpName(resultSet.getString(1));
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        gonghao.setText("用户名");
                        textField1.setText(LoginInfo.getEmpName());
                        textField1.setEnabled(false);
                        passwordField1.setVisible(false);
                        passwordField1.setEnabled(false);
                        密码Label.setVisible(false);
                        jMenuBar.setVisible(true);
                    }
                }
            }
        });
    }

    private void showFrame(){
        JFrame jFrame = new JFrame("出版社管理系统");
        jFrame.setContentPane(this.panel1);

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
        JMenuItem menuBookTypeManage = new JMenuItem("图书类目管理");
        JMenuItem menuBookManage = new JMenuItem("图书管理");
        JMenuItem menuAuthorManage = new JMenuItem("作者管理");
        JMenuItem menuMerchantManage = new JMenuItem("商家管理");
        JMenuItem menuWarehouseManage = new JMenuItem("仓库管理");
        jMenu3.add(menuDeptManage);
        jMenu3.add(menuEmployeeManage);
        jMenu3.add(menuBookTypeManage);
        jMenu3.add(menuBookManage);
        jMenu3.add(menuAuthorManage);
        jMenu3.add(menuMerchantManage);
        jMenu3.add(menuWarehouseManage);
        jMenuBar.add(jMenu3);
        menuDeptManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame frame = new DeptManage();
                frame.showFrame();
            }

        });
        menuEmployeeManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame frame = new EmployeeManage();
                frame.showFrame();
            }

        });

        menuAuthorManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame frame = new AuthorManage();
                frame.showFrame();
            }
        });
        menuWarehouseManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame frame = new WarehouseManage();
                frame.showFrame();
            }
        });
        menuBookTypeManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame frame = new BookTypeManage();
                frame.showFrame();
            }
        });

        menuBookManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame frame = new BookManage();
                frame.showFrame();
            }
        });

        menuMerchantManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame frame = new SellersManage();
                frame.showFrame();
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
