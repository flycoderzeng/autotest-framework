package test;

public class ReplaceTest {
    public static void main(String[] args) {
        String src = "insert into T_USERS_001 select * from T_USERS;\n" +
                "                insert into T_ACCOUNT_INFO_001 select * from T_ACCOUNT_INFO;";
        int i = 1;
        String res = "";
        while (i<=250) {
            String format = String.format("%03d", i);
            //System.out.println(format);
            res += src.replace("001", format);
            i++;
        }
        System.out.println(res);
    }
}
