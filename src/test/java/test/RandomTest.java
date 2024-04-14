package test;

import cn.hutool.core.util.RandomUtil;

import java.io.File;

public class RandomTest {
    public static void main(String[] args) {
        System.out.println(RandomUtil.randomString(128));
        File file = new File("http://192.168.23.15/dabase-platform/dev-1.8.1/database_meta.war");
        System.out.println(file.getName());
    }
}
