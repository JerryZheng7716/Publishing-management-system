package Util;

import javax.swing.*;

public class Province {
    private static final String[] provinces={"北京市","天津市","上海市","重庆市","河北省","山西省","辽宁省","吉林省","黑龙江省","江苏省","浙江省","安徽省",
            "福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省","内蒙古","广西",
    "西藏","宁夏","新疆","香港","澳门","台湾省"};

    /**
     * 快速设置具有中国省份的ComboBox
     * @param jComboBox1 需要被设置的ComboBox
     */
    public static void setComboBoxItem(JComboBox jComboBox1){
        for (String s:provinces
             ) {
            jComboBox1.addItem(s);
        }
    }

    public static String[] getProvince(){
        return provinces;
    }
}
