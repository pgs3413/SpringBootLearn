package com.pan.SpringBootLearn.test;


import java.io.IOException;
import java.net.Socket;

public class Test02 {
    public static void main(String[] args) {
        try {
            Socket socket=new Socket("192.168.1.247",5672);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
