package test;

import lombok.Data;

public class HashCodeTest {
    public static void main(String[] args) {
        TestC testC = new TestC();
        testC.setA("1");
        testC.setB("2");
        System.out.println(testC.hashCode());
    }

    @Data
    public static class TestC {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;

    }
}
