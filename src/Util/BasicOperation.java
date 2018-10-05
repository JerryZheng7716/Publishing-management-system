package Util;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by JerryZheng
 * 对增删改三个基本方法进行封装
 */
public class BasicOperation {
    /**
     * 增加一条记录
     *
     * @param sqlLanguage 数据库语句
     * @param psString    需要增加的数据
     */
    public static void add(String sqlLanguage, String[] psString) {
        if (psString == null) {
            return;
        }
        int x = SqlFunction.doSqlUpdate(sqlLanguage, psString);
        if (x > 0) {
            JOptionPane.showMessageDialog(null, "操作成功");
        }
    }

    /**
     * 修改一条记录
     *
     * @param sqlLanguage 数据库语句
     * @param ps          需要增加的数据
     * @param oldName     需要修改的记录的原主键值
     */
    public static void change(String sqlLanguage, String[] ps, String oldName) {
        int count = appearNumber(sqlLanguage);
        String[] psString = new String[count];
        if (ps == null) {
            return;
        }
        int res = JOptionPane.showConfirmDialog(null, "是否修改记录”" + oldName + "“的信息", "是否修改", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            System.arraycopy(ps, 0, psString, 0, count - 1);
            psString[count - 1] = oldName;
            if (psString[0].equals("")) {
                return;
            }
            int x = SqlFunction.doSqlUpdate(sqlLanguage, psString);
            if (x > 0) {
                JOptionPane.showMessageDialog(null, "修改成功");
            }
        }
    }

    /**
     * 增加一条记录
     *
     * @param sqlLanguage 数据库语句
     * @param oldName     需要删除的记录的原主键值
     */
    public static void del(String sqlLanguage, String oldName) {
        String[] psString = {oldName};
        if (psString[0].equals("")) {
            return;
        }
        int res = JOptionPane.showConfirmDialog(null, "是否删除”" + oldName + "“的信息", "是否修改", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            int x = SqlFunction.doSqlUpdate(sqlLanguage, psString);
            if (x > 0) {
                JOptionPane.showMessageDialog(null, "删除成功");
            }
        }
    }

    /**
     * 获取?出现的次数
     *
     * @param srcText 源字符串
     * @return 出现的次数
     */
    private static int appearNumber(String srcText) {
        int count = 0;
        Pattern p = Pattern.compile("[?]");
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }
}
