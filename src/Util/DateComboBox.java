package Util;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用三个JComboBox制作一个万年历
 * 以便录入日期
 */
public class DateComboBox {
    private JComboBox cbMonth,cbYear,cbDay;
    public DateComboBox(JComboBox cbYear, JComboBox cbMonth, JComboBox cbDay){
        this.cbDay=cbDay;
        this.cbMonth=cbMonth;
        this.cbYear=cbYear;
        initYearCombobox();
        initMonthCombobox();
        initDayCombobox();
        cbYear.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                initDayCombobox();
            }
        });
        cbMonth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                initDayCombobox();
            }
        });
    }
    private void initMonthCombobox() {
        if (cbMonth==null){
            return;
        }
        cbMonth.removeAllItems();//月份
        for (int i = 1; i <= 12; i++) {
            cbMonth.addItem(i + "");
        }
    }

    public void initDayCombobox() {
        if (cbDay==null){
            return;
        }
        cbDay.removeAllItems();//日
        String month = (String) cbMonth.getSelectedItem();
        String year = (String) cbYear.getSelectedItem();
        if (month.equals("1") || month.equals("3") || month.equals("5")
                || month.equals("7") || month.equals("8") || month.equals("10")
                || month.equals("12")) {
            for (int i = 1; i <= 31; i++) {
                cbDay.addItem(i + "");
            }
        } else if (month.equals("4") || month.equals("6") || month.equals("9")
                || month.equals("11")) {
            for (int i = 1; i <= 30; i++) {
                cbDay.addItem(i + "");
            }
        } else if (month.equals("2") && Integer.parseInt(year) % 4 == 0
                && !year.equals("2100")) {
            for (int i = 1; i <= 29; i++) {
                cbDay.addItem(i + "");
            }
        } else {
            for (int i = 1; i <= 28; i++) {
                cbDay.addItem(i + "");
            }
        }
    }

    private void initYearCombobox(){
        if (cbYear==null){
            return;
        }
        cbYear.removeAllItems();
        for (int i = 1990; i <= 2100; i++) {
            cbYear.addItem(i + "");
        }
        cbYear.setSelectedIndex(30);
    }

    /**
     * 将时间选中为当前日期
     */
    public void setNow() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        String[] df = dateFormat.format(date).split("-");
        ControlFunction.setComboBoxSelect(cbYear,df[0]);
        ControlFunction.setComboBoxSelect(cbMonth,df[1]);
        ControlFunction.setComboBoxSelect(cbDay,df[2]);
    }

    /**
     * 选中指定的日期
     * @param date 日期
     */
    public void setSelect(String date){
        String[] timeStrings = date.split("-");
        timeStrings[1] = (Integer.parseInt(timeStrings[1])) + "";//转换成int 再转 String 把前面的0去掉
        timeStrings[2] = (Integer.parseInt(timeStrings[2])) + "";//转换成int 再转 String 把前面的0去掉
        ControlFunction.setComboBoxSelect(cbYear, timeStrings[0]);
        ControlFunction.setComboBoxSelect(cbMonth, timeStrings[1]);
        ControlFunction.setComboBoxSelect(cbDay, timeStrings[2]);
    }

    public String getDate(){
        return cbYear.getSelectedItem()+"-"+cbMonth.getSelectedItem()+"-"+cbDay.getSelectedItem();
    }
}
