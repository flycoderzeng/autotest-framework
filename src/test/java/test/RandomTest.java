package test;

import cn.hutool.core.util.RandomUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.autotest.framework.utils.PairwiseTestUtils.RANDOM_STRING_PY_PATH;
import static com.autotest.framework.utils.PairwiseTestUtils.randomStringByRegex;

public class RandomTest {
    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        System.out.println(RandomUtil.randomString(128));
        File file = new File("http://192.168.23.15/dabase-platform/dev-1.8.1/database_meta.war");
        System.out.println(file.getName());
        System.out.println(RandomUtil.randomInt(0, 9));
        System.out.println(RANDOM_STRING_PY_PATH);

        String output = randomStringByRegex("[0-9]{5}[a-z]{6}[A-Z]{7,10}");
        System.out.println(output);
    }
}
