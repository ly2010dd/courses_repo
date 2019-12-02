package com.imooc.bigdata.hadoop.mr.eshopproject.mr;

import com.imooc.bigdata.hadoop.mr.eshopproject.utils.IPParser;
import com.imooc.bigdata.hadoop.mr.eshopproject.utils.LogParser;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Map;

/**
 * 省份流量统计
 */
public class ProvinceStatApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration configuration = new Configuration();

        FileSystem fileSystem = FileSystem.get(configuration);
        //本地测试
        //Path outputPath = new Path("output/eshopproject/v1/provincestat");
        //上服务器
        Path outputPath = new Path(args[1]);
        if (fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath, true);
        }

        Job job = Job.getInstance(configuration);
        job.setJarByClass(ProvinceStatApp.class);

        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //本地测试
        //FileInputFormat.setInputPaths(job, new Path("input/trackinfo/raw/trackinfo_20130721.data"));
        //上服务器
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, outputPath);

        job.waitForCompletion(true);
    }

    static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
        private LongWritable ONE = new LongWritable(1);
        private LogParser logParser;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            logParser = new LogParser();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String log = value.toString();

            Map<String, String> info = logParser.parse(log);
            String ip = info.get("ip");

            if (StringUtils.isNotBlank(ip)) {
                IPParser.RegionInfo regionInfo = IPParser.getInstance().analyseIp(ip);
                if (regionInfo != null) {
                    String province = regionInfo.getProvince();
                    if (StringUtils.isNotBlank(province)) {
                        context.write(new Text(province), ONE);
                    } else {
                        context.write(new Text("-"), ONE);
                    }
                } else {
                    context.write(new Text("-"), ONE);

                }
            } else {
                context.write(new Text("-"), ONE);

            }
        }
    }

    static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            long count = 0;
            for (LongWritable value : values) {
                count ++;
            }
            context.write(key, new LongWritable(count));
        }
    }

}
