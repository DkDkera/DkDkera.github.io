package com.dkdk.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UserUtil {
    public static final File usersFile = new File("users\\"); //用户存贮的根目录

    //检查用户输入密码是否与存储密码相同
    public static boolean checkUserPassword(String str, File passwordFile) {
        FileReader rd = null;
        try {
            rd = new FileReader(passwordFile);
            char[] chars = new char[100];
            int len = rd.read(chars);

            //用户密码不正确
            if(len != str.length()) {
                return false;
            }
            for(int i = 0; i < len ; i++)
                if(str.charAt(i) != chars[i]) {
                    return false;
                }

            //用户密码正确
            return true;
        }catch (FileNotFoundException ex) {
            System.out.println("找不到用户密码文件");
            ex.printStackTrace();
            return false;
        }catch (IOException ex) {
            System.out.println("读取用户密码文件失败");
            ex.printStackTrace();
            return false;
        }finally {
            //关闭读入流
            try {
                if(rd != null) {
                    rd.close();
                }
            }catch (IOException ex) {
                System.out.println("用户密码文件关闭流失败");
                ex.printStackTrace();
            }
        }
    }
}
