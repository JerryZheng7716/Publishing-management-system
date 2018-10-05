package Frame;

import Util.LoginInfo;
import Util.OtherFunction;
import Util.SqlFunction;

import javax.swing.*;
import java.awt.event.*;
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
    private JRadioButton pressRadioButton;
    private JRadioButton sellersRadioButton;
    private JLabel initPwd;
    private JMenuBar jMenuBar = new JMenuBar();
    private ButtonGroup buttonGroup;
    private boolean isPressModel=true;
    private JMenu jMenu = new JMenu("图书印刷管理");
    private JMenuItem menuFirstPrint = new JMenuItem("首版书印刷");
    private JMenuItem menuAddPrint = new JMenuItem("加印");

    private JMenu jMenu1 = new JMenu("图书库存管理");
    private JMenuItem menuBookAdd = new JMenuItem("图书入库");
    private JMenuItem menuBookOut = new JMenuItem("图书出库");

    private JMenu jMenu2 = new JMenu("图书销售管理");
    private JMenuItem menuOrder = new JMenuItem("订货");
    private JMenuItem menuShip = new JMenuItem("发货");

    private JMenu jMenu3 = new JMenu("基本信息管理");
    private JMenuItem menuDeptManage = new JMenuItem("部门管理");
    private JMenuItem menuEmployeeManage = new JMenuItem("员工管理");
    private JMenuItem menuBookTypeManage = new JMenuItem("图书类目管理");
    private JMenuItem menuBookManage = new JMenuItem("图书管理");
    private JMenuItem menuAuthorManage = new JMenuItem("作者管理");
    private JMenuItem menuMerchantManage = new JMenuItem("商家管理");
    private JMenuItem menuWarehouseManage = new JMenuItem("仓库管理");

    private JMenu jMenu4 = new JMenu("查询统计");
    private JMenuItem menuInventoryInquiry = new JMenuItem("库存查询统计");
    private JMenuItem menuSalesInquiry = new JMenuItem("销售查询统计");

    private JMenu jMenu5 = new JMenu("系统维护");
    private JMenuItem menuUserManage = new JMenuItem("用户管理");
    private JMenuItem menuDataMaintenance = new JMenuItem("数据维护");
    private JMenuItem menuPressAuthorityManage = new JMenuItem("出版社权限管理");
    private JMenuItem menuSellersAuthorityManage = new JMenuItem("商家权限管理");

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
    }
    private  void  init(){
//        jMenuBar.setVisible(false);
        initButton();
        initLogin();
        showFrame();
    }

    private void initButton(){
        buttonGroup = new ButtonGroup();
        buttonGroup.add(pressRadioButton);
        buttonGroup.add(sellersRadioButton);
        pressRadioButton.setSelected(true);
        pressRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLoginModel();
            }
        });

        sellersRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLoginModel();
            }
        });
        initPwd.setVisible(false);
        initPwd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Frame frame = new InitPwd(isPressModel);
                frame.showFrame();
                super.mousePressed(e);
            }
        });
    }

    private void setLoginModel(){
        isPressModel = pressRadioButton.isSelected();
    }

    private void initLogin(){
        登录Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loginID,loginPwd;
                loginID = textField1.getText();
                loginPwd = new String(passwordField1.getPassword());
                if(LoginInfo.isIsLogin()){
                    sellersRadioButton.setEnabled(true);
                    pressRadioButton.setEnabled(true);
                    initPwd.setVisible(false);
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
                    if (OtherFunction.Login(loginID,loginPwd,isPressModel).equals("Success")){
                        sellersRadioButton.setEnabled(false);
                        pressRadioButton.setEnabled(false);
                        initPwd.setVisible(true);
                        tittle.setText("欢迎您");
                        登录Button.setText("注销登录");
                        LoginInfo.setIsLogin(true);
                        LoginInfo.setEmpNo(loginID);
                        String sql;
                        if (isPressModel){
                            sql = "SELECT empName,deptNo,empAuthority FROM  Employee WHERE empNo = ?";
                        }else {
                            setSellersLogin();
                            sql = "SELECT selTitle FROM  Sellers WHERE selNo = ?";
                        }
                        ResultSet resultSet = SqlFunction.doSqlSelect(sql,
                                new String[]{loginID},false);
                        try {
                            resultSet.next();
                            if (isPressModel){
                                LoginInfo.setEmpName(resultSet.getString(1));
                                LoginInfo.setDept(resultSet.getString(2));
                                LoginInfo.setEmpAuthority(resultSet.getString(3));
                            }else {
                                LoginInfo.setEmpName(resultSet.getString(1));
                                LoginInfo.setDept("商家");
                                LoginInfo.setEmpAuthority("00200000");
                            }

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
        jMenuBar.add(jMenu);
        jMenu.add(menuFirstPrint);
        jMenu.add(menuAddPrint);
        menuFirstPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书印刷(),1)){
                    return;
                }
                Frame frame = new BookPrint();
                frame.showFrame();
            }
        });
        //图书印刷管理

        jMenu1.add(menuBookAdd);
        jMenu1.add(menuBookOut);
        menuBookAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx图书库存管理(),1)){
                    return;
                }
                Frame frame = new BookAdd();
                frame.showFrame();
            }
        });
        jMenuBar.add(jMenu);
        jMenuBar.add(jMenu1);
        //图书库存管理

        jMenu2.add(menuOrder);
        jMenu2.add(menuShip);
        jMenuBar.add(jMenu2);
        //图书销售管理


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
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(),1)){
                    return;
                }
                Frame frame = new DeptManage();
                frame.showFrame();
            }

        });
        menuEmployeeManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(),1)){
                    return;
                }
                Frame frame = new EmployeeManage();
                frame.showFrame();
            }

        });

        menuAuthorManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(),1)){
                    return;
                }
                Frame frame = new AuthorManage();
                frame.showFrame();
            }
        });
        menuWarehouseManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(),1)){
                    return;
                }
                Frame frame = new WarehouseManage();
                frame.showFrame();
            }
        });
        menuBookTypeManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(),1)){
                    return;
                }
                Frame frame = new BookTypeManage();
                frame.showFrame();
            }
        });

        menuBookManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(),1)){
                    return;
                }
                Frame frame = new BookManage();
                frame.showFrame();
            }
        });

        menuMerchantManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx基本信息管理(),1)){
                    return;
                }
                Frame frame = new SellersManage();
                frame.showFrame();
            }
        });

        //基本信息管理

        jMenu4.add(menuInventoryInquiry);
        jMenu4.add(menuSalesInquiry);
        jMenuBar.add(jMenu4);
        //查询统计

        jMenu5.add(menuUserManage);
        jMenu5.add(menuDataMaintenance);
        jMenu5.add(menuPressAuthorityManage);
        jMenu5.add(menuSellersAuthorityManage);
        menuSellersAuthorityManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!LoginInfo.testAuthority(LoginInfo.getQx权限管理(),1)){
                    return;
                }
                Frame frame = new SellersAuthorityManage();
                frame.showFrame();
            }
        });
        jMenuBar.add(jMenu5);
        //系统维护

        jFrame.setJMenuBar(jMenuBar);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setSize(800,500);
        jFrame.setLocation(500,260);
    }

    private void setSellersLogin(){
        jMenu.setVisible(false);
        jMenu1.setVisible(false);
        jMenu3.setVisible(false);
        jMenu4.setVisible(false);
        jMenu5.setVisible(false);
        menuShip.setVisible(false);
    }
}
