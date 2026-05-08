package com.qhx.back.context;

public class AddressContext {

    private static final ThreadLocal<String> addressHolder = new ThreadLocal<>();

    public static void setAddress(String address) {

        addressHolder.set(address);
    }

    public static String getAddress() {

        String address = addressHolder.get();

        System.out.println("当前登录地址：" + address);

        return address;
    }

    public static void clear() {

        addressHolder.remove();
    }
}