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
        String s="{\n" +
                "    \"applicationId\": \"${$.applicationId}\",\n" +
                "    \"metadataStructureIds\": [\n" +
                "        \"${$.metadataStructureIds[0]}\"\n" +
                "    ],\n" +
                "    \"encryptPolicy\": {\n" +
                "        \"secretKeyCode\": \"${$.encryptPolicy.secretKeyCode}\",\n" +
                "        \"encryptAlgorithm\": \"${$.encryptPolicy.encryptAlgorithm}\",\n" +
                "        \"typeCode\": \"${$.encryptPolicy.typeCode}\",\n" +
                "        \"encryptMode\": \"${$.encryptPolicy.encryptMode}\",\n" +
                "        \"columnFormat\": \"${$.encryptPolicy.columnFormat}\",\n" +
                "        \"stage\": \"${$.encryptPolicy.stage}\",\n" +
                "        \"hasEncryptField\": \"${$.encryptPolicy.hasEncryptField}\"\n" +
                "    },\n" +
                "    \"hashPolicy\": {\n" +
                "        \"hashAlgorithm\": \"${$.hashPolicy.hashAlgorithm}\",\n" +
                "        \"hashSecretKeyCode\": \"${$.hashPolicy.hashSecretKeyCode}\",\n" +
                "        \"hashLen\": \"${$.hashPolicy.hashLen}\",\n" +
                "        \"hashVerify\": \"${$.hashPolicy.hashVerify}\",\n" +
                "        \"hasHashField\": \"${$.hashPolicy.hasHashField}\"\n" +
                "    }\n" +
                "}";
        System.out.println(s);
    }
}
