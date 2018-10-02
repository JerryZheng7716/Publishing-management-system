package Util;

public class FillNumber {
    public static String fill(String num,int length){
        while (num.length()<length){
            num="0"+num;
        }
        return num;
    }
}
