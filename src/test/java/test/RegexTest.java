package test;

import cn.hutool.core.util.ReUtil;
import com.mifmif.common.regex.Generex;
import com.mifmif.common.regex.util.Iterator;
import nl.flotsam.xeger.Xeger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
        //String regex = "[ab]{4,6}c";
        String regex = "[ab]{2,8}.";
        Xeger generator = new Xeger(regex);
        String result = generator.generate();
        System.out.println(result);

        Generex generex = new Generex("[0-3]([a-c]|[e-g]{1,2})");
        String randomStr = generex.random();
        System.out.println(randomStr);// a random value from the previous String list
// generate the second String in lexicographical order that match the given Regex.
        String secondString = generex.getMatchedString(2);
        System.out.println(secondString);// it print '0b'
// Generate all String that matches the given Regex.
        List<String> matchedStrs = generex.getAllMatchedStrings();
// Using Generex iterator
        Iterator iterator = generex.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println("===>");
        System.out.println("===>");

        System.out.println(ReUtil.getGroup1(".+[ \t\r\n]+(.+\\..+)[ \r\n\t]*", "select * from test.aa").split("aaaaa")[0]);
    }
}
