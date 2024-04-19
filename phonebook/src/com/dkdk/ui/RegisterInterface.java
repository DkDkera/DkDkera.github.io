package com.dkdk.ui;

import com.dkdk.component.BackgroundPanel;
import com.dkdk.utils.ImagesPathUtil;
import com.dkdk.utils.ScreenUtil;
import com.dkdk.utils.UserUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RegisterInterface {
    private JFrame jFrame = new JFrame("注册");
    private final int WIDTH = 1067; //窗口的宽
    private final int HEIGHT = 600; //窗口的高

    public void init() {
        //载入注册界面的背景
        BackgroundPanel registerBg = null;
        try {
            registerBg = new BackgroundPanel(ImageIO.read(new File(ImagesPathUtil.getImagesPath("RegisterBackground.jpg"))));
        }catch (IOException ex) {
            ex.printStackTrace();
        }

        //账号相关组件
        JLabel accountJLable = new JLabel("账号："); //账号输入区域标志
        accountJLable.setForeground(Color.WHITE); //设置字体颜色为白色
        JTextField accountField = new JTextField(15); //账号输入区域

        //密码相关组件
        JLabel passwordJLabel = new JLabel("密码："); //密码输入区域标志
        passwordJLabel.setForeground(Color.WHITE); //设置字体颜色为白色
        JPasswordField passwordField = new JPasswordField(15); //密码输入区域

        //确认密码相关组件
        JLabel confirmPasswordJLabel = new JLabel("确认密码："); //确认密码输入区域标志
        confirmPasswordJLabel.setForeground(Color.WHITE); //设置字体颜色为白色
        JPasswordField confirmPasswordField = new JPasswordField(15); //设置确认密码输入区域

        //注册与返回按钮组件
        JButton registerButton = new JButton("注册");
        JButton returnButton = new JButton("返回");

        //监听注册按钮
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText().trim(); //账号
                String password = passwordField.getText().trim(); //密码
                String confirmPassword = confirmPasswordField.getText().trim(); //确认密码

                //检测是否有信息未填写
                if(account == null || password == null || confirmPassword == null) {
                    JOptionPane.showMessageDialog(jFrame, "注册信息不能为空");
                    return;
                }
                //检测是否有信息未填写
                if(account.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(jFrame, "注册信息不能为空");
                    return;
                }
                //检测填写的信息是否含有空格
                for(int i = 0; i < account.length() ; i++)
                    if(account.charAt(i) == ' ') {
                        JOptionPane.showMessageDialog(jFrame, "填写的信息不能含有空格");
                        return;
                    }
                for(int i = 0; i < password.length() ; i++)
                    if(password.charAt(i) == ' ') {
                        JOptionPane.showMessageDialog(jFrame, "填写的信息不能含有空格");
                        return;
                    }
                for(int i = 0; i < confirmPassword.length() ; i++)
                    if(confirmPassword.charAt(i) == ' ') {
                        JOptionPane.showMessageDialog(jFrame, "填写的信息不能含有空格");
                        return;
                    }

                //判断密码与确认密码是否相同
                if(!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(jFrame, "密码与确认密码不同");
                    return;
                }

                //判断账号与密码的长度不能大于20
                if(account.length() > 20 || password.length() > 20) {
                    JOptionPane.showMessageDialog(jFrame, "账号或密码的长度不能大于20");
                    return;
                }

                //判断注册的用户名是否已被注册过
                File[] files = UserUtil.usersFile.listFiles();
                if(files != null) {
                    //循环遍历用户
                    for(int i = 0; i < files.length ; i++)
                        if(account.equals(files[i].getName())) {
                            JOptionPane.showMessageDialog(jFrame, "当前用户名已被注册！");
                            return;
                        }
                }

                //创建用户文件夹
                File userFile = new File(UserUtil.usersFile, account);
                userFile.mkdir();

                //创建用户密码文件
                File userPasswordFile = new File(userFile, "password.txt");
                try{
                    userPasswordFile.createNewFile();
                }catch (IOException ex) {
                    System.out.println("创建用户密码文件失败！");
                }

                //创建用户数据文件
                File userDataFile = new File(userFile, "data.dat");
                try {
                    userDataFile.createNewFile();
                }catch (IOException ex) {
                    System.out.println("创建用户数据文件失败！");
                }

                //写入用户密码
                FileWriter writePassword = null;
                try {
                    writePassword = new FileWriter(userPasswordFile);
                    writePassword.write(password);
                }catch (IOException ex) {
                    JOptionPane.showMessageDialog(jFrame, "注册失败！"); //注册失败提示
                    ex.printStackTrace();
                    return;
                }finally {
                    //关闭输入流
                    try {
                        if(writePassword != null) {
                            writePassword.close();
                        }
                    }catch (IOException ex) {
                        System.out.println("关闭流失败！");
                        ex.printStackTrace();
                        return;
                    }
                }

                JOptionPane.showMessageDialog(jFrame, "注册成功！");
                new MainInterface().init(); //返回主界面
                jFrame.dispose();
            }
        });

        //监听返回按钮
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainInterface().init();
                jFrame.dispose();
            }
        });

        //账号box
        Box accountBox = Box.createHorizontalBox(); //创建账号区域box
        accountBox.add(accountJLable); //载入账号输入区域标志
        accountBox.add(Box.createHorizontalStrut(26)); //加入空白间隙
        accountBox.add(accountField); //载入账号输入区域

        //密码box
        Box passwordBox = Box.createHorizontalBox(); //创建密码区域box
        passwordBox.add(passwordJLabel); //载入密码输入区域标志
        passwordBox.add(Box.createHorizontalStrut(26)); //加入空白间隙
        passwordBox.add(passwordField); //载入密码输入区域

        //确认密码box
        Box confirmPasswordBox = Box.createHorizontalBox(); //创建确认密码区域box
        confirmPasswordBox.add(confirmPasswordJLabel); //载入确认密码输入区域标志
        confirmPasswordBox.add(confirmPasswordField); //载入确认密码输入区域

        //按钮box
        Box buttonBox = Box.createHorizontalBox(); //创建按钮box
        buttonBox.add(registerButton); //载入注册按钮
        buttonBox.add(Box.createHorizontalStrut(125)); //加入空白间隙
        buttonBox.add(returnButton); //载入返回按钮

        //整体box
        Box registerBox = Box.createVerticalBox(); //创建整体box
        registerBox.add(Box.createVerticalStrut(220)); //加入空白间隙
        registerBox.add(accountBox); //载入账号输入区域
        registerBox.add(passwordBox); //载入密码输入区域
        registerBox.add(confirmPasswordBox); //载入确认密码输入区域
        registerBox.add(Box.createVerticalStrut(10)); //加入空白间隙
        registerBox.add(buttonBox); //载入按钮

        //把整体box放在注册界面上
        registerBg.add(registerBox);

        //把注册界面载入窗口
        jFrame.add(registerBg);

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setBounds((ScreenUtil.getDefaultWindowsWidth() - WIDTH) / 2,
                (ScreenUtil.getDefaultWindowsHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
    }

    public static void main(String[] args) {
        new RegisterInterface().init();
    }
}
