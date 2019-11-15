package com.imooc.bigdata.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * JAVA API操作HDFS文件系统
 *
 * 关键点：
 * 1）创建Configuration
 * 2) 获取FileSystem
 * 3) HDFS API具体操作
 */
public class HDFSApp {
    public static final String HDFS_PATH = "hdfs://hadoop000:8020";
    FileSystem fileSystem = null;
    Configuration configuration = null;

    @Before
    public void setUp() throws Exception {
        System.out.println("------setUp------");
        configuration = new Configuration();
        configuration.set("dfs.replication", "1");
        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, "hadoop");
    }

    /**
     * 创建HDFS文件夹
     */
    @Test
    public void mkdir() throws Exception {
        fileSystem.mkdirs(new Path("/hdfsapi/test"));
    }

    /**
     * 查看HDFS内容
     */
    @Test
    public void text() throws Exception {
        FSDataInputStream in = fileSystem.open(new Path("/README.txt"));
        IOUtils.copyBytes(in, System.out, 1024);
    }

    /**
     * 创建文件
     */
    @Test
    public void create() throws Exception {
       FSDataOutputStream out = fileSystem.create(new Path("/hdfsapi/test/a.txt"));
       out.writeUTF("hello liy");
       out.flush();
       out.close();
    }

    /**
     * 修改配置文件
     */
    @Test
    public void testReplication() {
        // configuration为null时
        // dfs.replication默认读的org.apache.hadoop:hadoop-hdfs:2.60.-cdh5.15.1下的hdfs-default.xml文件中的
        System.out.println(configuration.get("dfs.replication"));
        configuration.set("dfs.replication", "1");
        System.out.println(configuration.get("dfs.replication"));
    }

    /**
     *  测试更改文件名
     */
    @Test
    public void rename() throws IOException {
        Path oldPath = new Path("/hdfsapi/test/a.txt");
        Path newPath = new Path("/hdfsapi/test/b.txt");
        boolean result = fileSystem.rename(oldPath, newPath);
        System.out.println(result);
    }

    /**
     * 拷贝本地文件到HDFS
     */
    @Test
    public void copyFromLocalFile() throws IOException {
        Path src = new Path("/Users/milesyli/hello.txt");
        Path dst = new Path("/hdfsapi/test/");
        fileSystem.copyFromLocalFile(src, dst);
    }

    /**
     * 拷贝大文件到HDFS：带进度
     */
    @Test
    public void copyFromLocalBigFile() throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(new File("/Users/milesyli/tools/apache-maven-3.6.2-bin.tar.gz")));

        FSDataOutputStream out = fileSystem.create(new Path("/hdfsapi/test/maven.tgz"), new Progressable() {
            public void progress() {
                System.out.print(".");
            }
        });
        IOUtils.copyBytes(in, out, 4096);
    }

    /**
     * 拷贝HDFS文件到本地：下载
     */
    @Test
    public void copyToLocalFile() throws IOException {
        Path src = new Path("/hdfsapi/test/hello.txt");
        Path dst = new Path("/Users/milesyli/tmp");
        fileSystem.copyToLocalFile(src, dst);
    }

    /**
     * 列出文件夹下所有文件
     */
    @Test
    public void listFiles() throws IOException {
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/hdfsapi/test"));
        for (FileStatus fileStatus : fileStatuses) {
            String isDir = fileStatus.isDirectory() ? "file dir" : "file";
            String permission = fileStatus.getPermission().toString();
            short replica = fileStatus.getReplication();
            long length = fileStatus.getLen();
            String path = fileStatus.getPath().toString();

            System.out.println(isDir + "\t" + permission + "\t" + replica + "\t" + length + "\t" + path);
        }
    }

    /**
     * 递归列出文件夹下所有文件
     */
    @Test
    public void listFilesRecursive() throws IOException {
        RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(new Path("/hdfsapi/test"), true);
        while (files.hasNext()) {
            LocatedFileStatus fileStatus = files.next();
            String isDir = fileStatus.isDirectory() ? "file dir" : "file";
            String permission = fileStatus.getPermission().toString();
            short replica = fileStatus.getReplication();
            long length = fileStatus.getLen();
            String path = fileStatus.getPath().toString();

            System.out.println(isDir + "\t" + permission + "\t" + replica + "\t" + length + "\t" + path);
        }
    }

    /**
     * 查看文件块信息
     */
    @Test
    public void getFileBlockLocations() throws IOException {
        FileStatus fileStatus = fileSystem.getFileStatus(new Path("/hdfsapi/test/maven.tgz"));
        BlockLocation[] blocks = fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getReplication());

        for (BlockLocation block : blocks) {
            for (String name : block.getNames()) {
                System.out.println(name + " : " + block.getOffset() + " : " + block.getLength());
            }
        }
    }

    /**
     * 删除文件
     */
    @Test
    public void delete() throws IOException {
        boolean result = fileSystem.delete(new Path("/hdfsapi/test/maven.tgz"), true);
        System.out.println(result);
    }

    @After
    public void tearDown() {
        configuration = null;
        fileSystem = null;
        System.out.println("------tearDown------");
    }

//    public static void main(String[] args) throws Exception {
//        Configuration configuration = new Configuration();
//        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop000:8020"), configuration, "hadoop");
//
//        Path path = new Path("/hdfsapi/test");
//        boolean result = fileSystem.mkdirs(path);
//        System.out.println(result);
//     }

}
