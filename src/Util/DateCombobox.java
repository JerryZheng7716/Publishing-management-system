package Util;

import javax.swing.*;

public class DateCombobox {
    private JComboBox cbMonth,cbYear,cbDay;
    public DateCombobox(JComboBox cbYear,JComboBox cbMonth,JComboBox cbDay){
        this.cbDay=cbDay;
        this.cbMonth=cbMonth;
        this.cbYear=cbYear;
    }
    public void initMonthCombobox() {
        cbMonth.removeAllItems();//月份
        for (int i = 1; i <= 12; i++) {
            cbMonth.addItem(i + "");
        }
    }

    public void initDayCombobox() {
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

    public void initYearCombobox(){
        cbYear.removeAllItems();
        for (int i = 1990; i <= 2100; i++) {
            cbYear.addItem(i + "");
        }
        cbYear.setSelectedIndex(30);
    }
}
