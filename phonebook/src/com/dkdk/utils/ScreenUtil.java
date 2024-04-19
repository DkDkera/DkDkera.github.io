package com.dkdk.utils;

import java.awt.*;

public class ScreenUtil {
    //获取当前窗口的默认宽度
    public static int getDefaultWindowsWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    //获取当前窗口的默认高度
    public static int getDefaultWindowsHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }
}
