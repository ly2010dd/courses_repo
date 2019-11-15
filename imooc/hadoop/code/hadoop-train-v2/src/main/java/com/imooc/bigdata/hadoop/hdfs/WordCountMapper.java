package com.imooc.bigdata.hadoop.hdfs;

public class WordCountMapper implements ImoocMapper {
    public void map(String line, ImoocContext context) {
        String[] words = line.split("\t");
        for (String word : words) {
            Object value = context.get(word);
            if (value == null) {    //没出现过该单词
                context.write(word, 1);
            } else {
                int v = Integer.parseInt(value.toString());
                context.write(word, v + 1); //出现了该单词就加一
            }
        }
    }
}
