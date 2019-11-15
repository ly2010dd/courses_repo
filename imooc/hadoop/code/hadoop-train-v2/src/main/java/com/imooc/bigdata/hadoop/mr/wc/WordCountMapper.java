package com.imooc.bigdata.hadoop.mr.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * KEYIN: Map任务读数据的key类型，offset，是每行数据起始位置的偏移量，Long
 * VALUEIN: Map任务读数据的value类型，就是一行行的字符串，String
 *
 * eg:
 * hello world welcome <KEYIN, VALUEIN> <0, "hello world welcome">
 * hello welcome <KEYIN, VALUEIN> <19, "hello welcome">
 *
 * KEYOUT: Map方法自定义实现输出的key的类型，String
 * VALUEOUT: Map方法自定义实现输出的value的类型，Integer
 *
 * eg:
 * <KEYOUT, VALUEOUT> <"hello", 1>
 *
 * Long, String, String, Integer是Java里面的数据类型，不能用
 * LongWritable, Text, Text, IntWritable是Hadoop自定义类型，具有序列化和反序列化功能，因为在分布式环境中涉及网络传输
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 把value对应的行数据按照指定的分隔符拆开
        String[] words = value.toString().split("\t");
        for (String word : words) {
            // (hello, 1) (world, 1)
            context.write(new Text(word.toLowerCase()), new IntWritable(1));
        }
    }
}