package com.example.simplecalculator;

public class Methods{

    public static String[] concat(String []A, String []B){
        String []C= new String[A.length+B.length];
        int i=0;
        for(String e : A)
            C[i++]=e;
        for(String e : B)
            C[i++]=e;
        return C;
    }
    public static String cancelLastChar(String str){
        return str.length()>0 ? str.substring(0,str.length() - 1) : "";
    }
    public static boolean isContains(String str, char c) {
        return str.indexOf(c) > -1;
    }
    public static int returnIndex(String[] arr, String search) {
        for(int i=0; i<arr.length; i++) {
            if(arr[i].equals(search))
                return i;
        }
        return -1;
    }
    public static String operation(String A, String B, String OprType) {
        String result = null;
        Boolean resIsFloat = Methods.isContains(A, '.') || Methods.isContains(B, '.');
        try {
            switch(OprType) {
                case "+":
                    result = Float.toString(Float.parseFloat(A) + Float.parseFloat(B));
                    break;
                case "-":
                    result = Float.toString(Float.parseFloat(A) - Float.parseFloat(B));
                    break;
                case "*":
                    result = Float.toString(Float.parseFloat(A) * Float.parseFloat(B));
                    break;
                case "/":
                    result = Float.toString(Float.parseFloat(A) / Float.parseFloat(B));
            }
            if(!resIsFloat)
                result = Integer.toString((int) Float.parseFloat(result));
        }catch(ArithmeticException e) {
            result="Exception, Division par 0";
        }
        return result;
    }

}