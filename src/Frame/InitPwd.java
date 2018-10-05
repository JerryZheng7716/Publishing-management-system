package Frame;

import Util.LoginInfo;
import Util.SHA1;
import Util.SqlFunction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitPwd extends JFrame implements Frame  {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JButton 重置密码Button;
    public InitPwd(boolean isPress) {
        重置密码Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql;
                if (isPress){
                    sql = "UPDATE Employee SET empPwd=? where empNo = ?";
                }else {
                    sql = "UPDATE Sellers SET selPwd=? where selNo = ?";
                }
                String[] ps = getStrings();
                SqlFunction.doSqlUpdate(sql, ps);
                JOptionPane.showMessageDialog(null, "成功重置您的密码");
            }
        });
    }

    @Override
    public void showFrame() {
        this.setContentPane(this.panel1);
        this.setTitle("重置密码");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocation(600, 400);
        this.setVisible(true);
        initText();
    }

    @Override
    public void initText() {
        textField1.setText(LoginInfo.getEmpNo());
        textField2.setText(LoginInfo.getEmpName());
    }

    @Override
    public void initTable() {

    }

    @Override
    public String[] getStrings() {
        String id,pwd;
        id=LoginInfo.getEmpNo();
        pwd=new String(passwordField1.getPassword());
        pwd=SHA1.encode(pwd);
        return new String[]{pwd,id};
    }
}
