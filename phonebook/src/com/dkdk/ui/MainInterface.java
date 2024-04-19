package com.dkdk.ui;

import com.dkdk.component.BackgroundPanel;
import com.dkdk.utils.ScreenUtil;
import com.dkdk.utils.UserUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainInterface {
    private JFrame jFrame = new JFrame("提瓦特大陆通讯录"); //主窗口
    public final int WIDTH = 1078; //窗口的宽度
    public final int HEIGHT = 594; //窗口的高度
    private final String passwordName = "password.txt"; //固定用户密码文件名
    private final String dataName = "data.dat";

    public void init() {
        //设置窗口的大小和初始位置
        jFrame.setBounds((ScreenUtil.getDefaultWindowsWidth() - WIDTH) / 2,
                (ScreenUtil.getDefaultWindowsHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        //创建主界面的背景
        BackgroundPanel mainBg = null;
        try{
            mainBg = new BackgroundPanel(ImageIO.read(new File("D:\\program\\IDEA_Javacode\\phonebook\\images\\MainBackground.jpg")));
        }catch (IOException ex) {
            ex.printStackTrace();
        }

        //账号相关组件
        JLabel accountJLable = new JLabel("账号："); //账号输入区域标志
        accountJLable.setForeground(Color.WHITE); //设置字体颜色为白色
        JTextField accountField = new JTextField(15); //账号输入区域

        //密码相关组件
        JLabel passwordJLable = new JLabel("密码："); //密码输入区域标志
        passwordJLable.setForeground(Color.WHITE); //设置字体颜色为白色
        JPasswordField passwordField = new JPasswordField(15); //密码输入区域

        //登录与注册相关组件
        JButton loginButton = new JButton("登录"); //登录按钮
        JButton registerButton = new JButton("注册"); //注册按钮

        //实现登录监听
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText().trim(); //.trim()的作用是去除多余空格
                String password = passwordField.getText().trim();

                //检测填写信息是否为空
                if(account == null || password == null) {
                    JOptionPane.showMessageDialog(jFrame, "填写信息不能为空");
                    return;
                }
                if(account.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(jFrame, "填写信息不能为空");
                    return;
                }

                //检测填写信息是否有空格
                for(int i = 0; i < account.length() ; i++)
                    if(account.charAt(i) == ' ') {
                        JOptionPane.showMessageDialog(jFrame, "填写信息不能含有空格");
                        return;
                    }
                for (int i = 0; i < password.length() ; i++)
                    if(password.charAt(i) == ' ') {
                        JOptionPane.showMessageDialog(jFrame, "填写信息不能含有空格");
                        return;
                    }


                File[] users = UserUtil.usersFile.listFiles(); //users数组存储所有用户的文件夹
                //检查是否无用户存在
                if(users == null) {
                    JOptionPane.showMessageDialog(jFrame, "不存在该用户");
                    return;
                }

                //找出用户所在的文件夹
                File[] userFiles = null; //存储登录用户存储信息的文件夹
                for(int i = 0; i < users.length ; i++) {
                    if(account.equals(users[i].getName())) {
                        userFiles = users[i].listFiles();
                        break;
                    }
                }

                //登录用户存储信息的文件夹为空
                if(userFiles == null) {
                    JOptionPane.showMessageDialog(jFrame, "不存在该用户");
                    return;
                }

                boolean flag = false; //存储用户是否登录成功

                //匹配用户登录密码
                for(int i = 0; i < userFiles.length ; i++) {
                    FileReader passwordFile = null;
                    if (passwordName.equals(userFiles[i].getName())) {
                        if(UserUtil.checkUserPassword(password, userFiles[i])) {
                            flag = true; //登录成功
                        }

                        break;
                    }
                }

                //登录结果反馈
                if(!flag) {
                    JOptionPane.showMessageDialog(jFrame, "登录失败！");
                }else {
                    File dataFile = null;
                    for(int i = 0; i < userFiles.length ; i++)
                    JOptionPane.showMessageDialog(jFrame, "登录成功！");
                    jFrame.dispose();
                    // TODO 登录用户界面
                }
            }
        });

        //实现注册监听
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterInterface().init(); //进入注册界面
                jFrame.dispose();
            }
        });

        //账号行组装
        Box userNameBox = Box.createHorizontalBox();
        userNameBox.add(accountJLable); //载入账号标志
        userNameBox.add(accountField); //载入账号输入区域

        //密码行组装
        Box userPswBox = Box.createHorizontalBox();
        userPswBox.add(passwordJLable); //载入密码标志
        userPswBox.add(passwordField); //载入密码输入区域

        //按钮行组装
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(loginButton); //载入登录按钮
        buttonBox.add(Box.createHorizontalStrut(90)); //在登录和注册加入空白间隙
        buttonBox.add(registerButton); //载入注册按钮

        //组装所有box组件为mainBox
        Box mainBox = Box.createVerticalBox();
        mainBox.add(Box.createVerticalStrut(200)); //加入空白，下调登录界面
        mainBox.add(userNameBox);
        mainBox.add(userPswBox);
        mainBox.add(Box.createVerticalStrut(10)); //在登录注册按钮与账号密码间加入空白间隙
        mainBox.add(buttonBox);

        //将mainBox放到主界面背景上
        mainBg.add(mainBox);

        jFrame.add(mainBg); //添加背景组件
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
    }

    // 客户端程序的入口
    public static void main(String[] args) {
        new MainInterface().init();
    }
}


