package com.imooc.bigdata.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用HDFS API完成wordcount统计
 * 需求：统计HDFS上的文件的wc，然后将统计结果输出到HDFS
 *
 * 任务拆解：
 * 1）读取HDFS上的文件 ==> HDFS API
 * 2）业务处理（词频统计）==> Mapper
 * 3）将处理结果缓存起来 ==> Context
 * 4）将结果输出到HDFS ==> HDFS API
 */
public class HDFSWCApp01 {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        // 1）读取HDFS上的文件 ==> HDFS API
        Path input = new Path("/hdfsapi/test/hello.txt");

        // 获取HDFS文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop000:8020"), new Configuration(), "hadoop");
        ImoocMapper mapper = new WordCountMapper();
        ImoocContext context = new ImoocContext();

        RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(input, false);
        while (iterator.hasNext()) {
            LocatedFileStatus file = iterator.next();
            FSDataInputStream in = fs.open(file.getPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line = "";
            while ((line = reader.readLine()) != null) {
                // 2）业务处理（词频统计）(helo, 3)
                // 业务逻辑处理完将结果写到Cache中
                mapper.map(line, context);
            }

            reader.close();
            in.close();
        }

        // 3）将处理结果缓存起来 Map
        Map<Object, Object> contextMap = context.getCacheMap();

        // 4）将结果输出到HDFS ==> HDFS API
        Path output = new Path("/hdfsapi/output");
        FSDataOutputStream out = fs.create(new Path(output, new Path("wc.out")));

        // 将3）中缓存的内容输出到out中去
        for (Map.Entry<Object, Object> entry : contextMap.entrySet()) {
            out.write((entry.getKey().toString() + "\t" + entry.getValue() + "\n").getBytes());
        }

        out.close();
        fs.close();

        System.out.println("词频统计运行成功...");
    }
}
