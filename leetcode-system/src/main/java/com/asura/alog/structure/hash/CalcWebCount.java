package com.asura.alog.structure.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalcWebCount {
    /**
     * 解析得到输入中每个子域名对应的 计数配对域名
     *
     * 输入：cpdomains = ["900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"]
     * 输出：["901 mail.com","50 yahoo.com","900 google.mail.com","5 wiki.org","5 org","1 intel.mail.com","951 com"]
     *
     * @param cpdomains 域名统计数组
     * @return List<String>
     */
    public List<String> subdomainVisits(String[] cpdomains) {
        List<String> result = new ArrayList<>();
        // 每个子域名计数
        Map<String,Integer> wordCounts = new HashMap<>();
        for (int i = 0; i < cpdomains.length; i++) {
            String cpdomain = cpdomains[i];
            int index = cpdomain.indexOf(" ");
            int count = Integer.valueOf(cpdomain.substring(0, index));
            // 域名分词
            String[] words = cpdomain.substring(index + 1).split("\\.", -1);
            // 词可能存在的域名
            String key = "";
            for (int j = words.length - 1; j >= 0; j--) {
                key = key.equals("") ? words[j] : words[j] + "." + key;
                if (wordCounts.containsKey(key)) {
                    // 词存在计数叠加
                    wordCounts.put(key, wordCounts.get(key) + count);
                } else {
                    wordCounts.put(key, count);
                }
            }
        }
        for (Map.Entry<String,Integer> entry : wordCounts.entrySet()) {
            result.add(entry.getValue() + " " + entry.getKey());
        }
        return result;
    }
}
