package Util;

public class FillNumber {
    /**
     * 将某个数字前面用0快速填充为指定长度
     * @param num 需要被填充的数字
     * @param length 需要填充的长度
     * @return 填充好的数字
     */
    public static String fill(String num,int length){
        while (num.length()<length){
            num="0"+num;
        }
        return num;
    }
}
