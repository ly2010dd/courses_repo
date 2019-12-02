# 第一章 大数据概述

---

### 大数据之4V特征
- 数据量（Volume）
- 多样性、复杂性（Variety）
- 基于高度分析的新价值（Value）
- 速度（Velocity）

### 大数据带来的技术变革
- 存储：文件存储-->分布式存储
- 计算：单机-->分布式计算
- 网络：万兆
- DB：RDBMS-->NoSQL(HBase/Redis...)

### 大数据技术概念
- 数据采集：flume、sqoop
- 数据存储：hadoop
- 数据处理、分析、挖掘：hadoop、spark、flink
- 可视化

### 大数据在技术架构上带来的挑战
- 对现有数据库管理技术的挑战
- 经典数据库技术并没考虑数据的多类别
- 实时性的技术挑战
- 网络架构、数据中心、运维的挑战
- 数据隐私
- 数据源复杂多样

# 第二章 初识Hadoop

---
- Hadoop概述
- Hadoop核心组件
- Hadoop优势
- Hadoop发展史
- Haoop生态系统
- Hadoop发行版的选择

### Hadoop概述
### Hadoop核心组件之HDFS概述
### Hadoop核心组件之MapReduce概述
![The overall MapReduce word count process](imgs/map-reduce-process.png)
### Hadoop核心组件之资源调度系统YARN
### Hadoop优势
- 高可靠性
    - 数据存储：数据块多副本
    - 数据计算：重新调度作业计算
- 高扩展性
    - 存储/计算资源不足可以横向扩展机器
    - 一个集群可以有数以千计的节点
- 廉价机器成本低
- 成熟的生态圈
### Hadoop发展史
- InfoQ上有篇文章《Hadoop十年解读与发展预测》https://www.infoq.cn/article/hadoop-ten-years-interpretation-and-development-forecast
- Dung Cutting Hadoop 10th birthday https://www.cloudera.com/promos/hadoop10.html

### Haoop生态系统
### Hadoop发行版
- Apache
    - 优点：纯开源
    - 缺点：不同版本、不同框架之间整合，jar冲突到吐血
- CDH（Cloudera）
    - 网站：https://www.cloudera.com
    - 优点：cm(cloudera manager)通过页面意见安装各种框架、升级、impala
    - 缺点：cm不开源、与社区版本有些许出入
    - 国内市场占60-70%
- HDP（Hortonworks）
    - 优点：原装Hadoop、纯开源、支持tez
    - 缺点：企业级安全不开源、文档不好
- MapR（不建议）
- 优先考虑CDH和HDP，这两种安装部署方面更方便


# 第三章 分布式文件系统HDFS

### 3-1 HDFS概述
- 分布式
- commodity hardware
- highly fault-tolerant 
- high throughput
- large data sets

### 3-2 HDFS设计前提与目标
- Hardware Failure
    - 硬件错误是常见的
    - 每个机器只存储文件的部分数据 
- Streaming Data Access
    - more for batch processing rather than interactive use by users
    - high throughput of data access rather than low latency of data access
- Large Data Sets
- Moving Computation is Cheaper than Moving Data
    - 移动计算比移动数据更划算
    - HDFS提供了接口移动计算到数据附近

### 3-3 HDFS架构（五星）
- 架构图官网https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-hdfs/HdfsDesign.html
- NameNode(Master) and DataNodes(Slaves)
- master/slave架构
- NN
    - manage the file system namespace
    - regulate access to files by clients
- DN
    - manage storage
- HDFS exposes a file system namespace and allows user data to be stored in files
- a file is split into one or more blocks
    - blocksize:128M
    - 150M文件需要两个blocks
- blocks are stored in a set of DataNodes
    - 容错
- NameNode executes file system namespace operations
- NameNode determines the mapping of blocks to DataNodes

### 3-4 文件系统NameSpace详解
- Similar to most other existing file systems
- NN maintains the file system namespace
- Any change to the file system namespace or its properties is recorded by the NN

### 3-5 HDFS副本机制
- HDFS stores each file as a sequence of blocks
- The blocks of a file are replicated for fault tolerance
- The block size and replication factor are configurable
- Files in HDFS are write-once (except for appends and truncates) and have strictly one writer at any time
- The NameNode makes all decisions regarding replication of blocks
- Replica Placement
    - The purpose of a rack-aware replica placement policy is to improve data reliability, availability, and network bandwidth utilization
    - The NameNode determines the rack id each DataNode belongs to via Hadoop Rack Awareness
    - When the replication factor is three, HDFS’s placement policy is to put one replica on the local machine if the writer is on a datanode, otherwise on a random datanode in the same rack as that of the writer, another replica on a node in a different (remote) rack, and the last on a different node in the same remote rack（三个replica，一个在datanode writer的local机器，另一个在一个不同的remote机架的机器上，最后一个在那个remote机架的不同机器上）
    - If the replication factor is greater than 3, the placement of the 4th and following replicas are determined randomly while keeping the number of replicas per rack below the upper limit (which is basically (replicas - 1) / racks + 2)（第四个replica随机分机架，且保证每小于每个机架的replca的上限）
    - NameNode does not allow DataNodes to have multiple replicas of the same block
- Replica Selection
    - HDFS tries to satisfy a read request from a replica that is closest to the reader
    - 能从本机、本机架、本数据中心读就不从remote的读
- Safemode
    - On startup, the NameNode enters a special state called Safemode 启动时NN进入Safemode状态
    - Replication of data blocks does not occur 此状态下数据块不进行复制
    - The NameNode receives Heartbeat and Blockreport messages from the DataNodes Blockreport包含此DN的数据块列表
    - A block is considered safely replicated when the minimum number of replicas of that data block has checked in with the NameNode 每个block中的最小副本数被注册进NN后block被认为可以安全复制了
    - After a configurable percentage of safely replicated data blocks checks in with the NameNode (plus an additional 30 seconds), the NameNode exits the Safemode state 可安全复制的replica比例超过一定配置值时NN退出Safemode
    - 再之后NN把所有剩余的未满副本的blocks都复制

### 3-7 Hadoop部署前置介绍
- Hadoop使用版本：CDH 
- CDH下载：http://archive.cloudera.com/cdh5/cdh/5/
- hadoop版本：hadoop-2.6.0-cdh5.15.1 (hadoop-2.6.0-cdh5.15.1.tar.gz)
    - hadoop下载：wget http://archive.cloudera.com/cdh5/cdh/5/hadoop-2.6.0-cdh5.15.1.tar.gz
    - hive版本：hive-1.1.0-cdh5.15.1 注意最后的cdh版本各个组件要一致
    - cdh5.15.1官网文档 http://archive.cloudera.com/cdh5/cdh/5/hadoop-2.6.0-cdh5.15.1/
- hadoop安装前置要求：jdk1.8+、ssh
    - jdk 略
    - ssh免密登录
        - ssh-keygen -t rsa
            - id_rsa私钥
            - id_rsa.pub公钥
        - cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
        - chmod 600 ~/.ssh/authorized_keys

### 3-10 Hadoop安装配置standalone
- 下载解压
- 添加HADOOP_HOME/bin到系统环境变量
- 修改Hadoop配置文件
    - hadoop-env.sh
        - export JAVA_HOME=/home/hadoop/app/jdk1.8.0_91
        - HADOOP_PREFIX不用配用默认的就好
    - core-site.xml
        ```
        #主节点的文件的系统在hadoop000的8020上
        <property>
            <name>fs.defaultFS</name>
            <value>hdfs://hadoop000:8020</value>
        </property>
        #建议用8020端口，9000是hadoop1中的
        ```
    - hdfs-site.xml
        ```
        #副本系数，默认是3，但现在只有1个节点
        <property>
            <name>dfs.replication</name>
            <value>1</value>
        </property>
        
        #文件系统文件临时数据块存放的地方，默认是在/tmp中的，重启就清空了，所以需要改掉
        <property>
            <name>hadoop.tmp.dir</name>
            <value>/home/hadoop/app/tmp</value>
        </property>
        ```
    - slaves
        ```
        hadoop000
        ```
- 启动HDFS
    - hdfs namenode -format(第一次执行的时候一定要格式化文件系统，不要重复执行)
    - sbin/start-dfs.sh
    - jps
        ```
        48144 NameNode
        48530 Jps
        48420 SecondaryNameNode
        48231 DataNode
        ```
- 浏览器访问hadoop
    - 查看防火墙 sudo firewall-cmd --state
    - 关闭防火墙 sudo systemctl stop firewalld.service
    - 访问hadoop web界面 hadoop000:50070


### 3-15 HDFS命令行操作详解*****
http://archive.cloudera.com/cdh5/cdh/5/hadoop-2.6.0-cdh5.15.1/hadoop-project-dist/hadoop-common/FileSystemShell.html

### 3-16 编程环境搭建
- pom.xml最佳实践
    ```
    <?xml version="1.0" encoding="UTF-8"?>
    
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
    
      <groupId>com.imooc.bigdata</groupId>
      <artifactId>hadoop-train-v2</artifactId>
      <version>1.0</version>
    
      <name>hadoop-train-v2</name>
      <!-- FIXME change it to the project's website -->
      <url>http://www.example.com</url>
    
      <properties>
        <!--定义Hadoop版本-->
        <hadoop.version>2.6.0-cdh5.15.1</hadoop.version>
      </properties>
    
      <!--引入cdh仓库-->
      <repositories>
        <repository>
          <id>cloudera</id>
          <url>https://repository.cloudera.com/artifactory/cloudera-repos</url>
        </repository>
      </repositories>
    
      <dependencies>
        <!--添加Hadoop的依赖包-->
        <dependency>
          <groupId>org.apache.hadoop</groupId>
          <artifactId>hadoop-client</artifactId>
          <version>${hadoop.version}</version>
        </dependency>
    
        <!--添加junit的依赖包-->
        <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.11</version>
          <scope>test</scope>
        </dependency>
      </dependencies>
    
    </project>

    ```

### 3-17 HDFS API编程
- Permission denied exception
    ```
    # Exception
    Caused by: org.apache.hadoop.ipc.RemoteException(org.apache.hadoop.security.AccessControlException): Permission denied: user=milesyli, access=WRITE, inode="/":hadoop:supergroup:drwxr-xr-x
    
    # 原因：对其他用户没有w权限
    hadoop@hadoop000 hadoop-2.6.0-cdh5.15.1]$ hadoop fs -ls /
    Found 3 items
    -rw-r--r--   1 hadoop supergroup      85063 2019-11-01 15:01 /LICENSE.txt
    -rw-r--r--   1 hadoop supergroup       1366 2019-11-01 14:59 /README.txt
    
    # 解决：第三个参数穿个hadoop进去
    FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop000:8020"), configuration, "hadoop");
    ```

### 3-30 HDFS API实现词频统计

### 3-38 副本摆放策略
- 本地机架一个replica
- 远程机架一个replica
- 远程机架另一个replica

### 3-39 HDFS写入过程
![HDFS写入过程](imgs/hdfs-write-process.png)

### 3-40 HDFS读取过程
![HDFS读取过程](imgs/hdfs-read-process.png)

### 3-41 HDFS Checkpoint详解
- HDFS的元数据管理
    - 元数据
        - HDFS目录结构
        - 每个文件的BLOCK信息（id、副本数、在哪些DN）
    - 存在什么地方
        - 在${hadoop.tmp.dir}/dfs/name/current
- checkpoint机制
    ![HDFS Checkpoint机制流程](imgs/hdfs-checkpoint-process.png)


# 第四章 分布式计算框架MapReduce

### 4-2 MapReduce概述
- Google MapReduce论文发表于2004.12
- Hadoop MapReduce是Google MapReduce的克隆版
- MapReduce优点
    - 海量数据离线处理
    - 易开发，与自己开发相比
    - 易运行
- MapReduce缺点
    - 实时流式计算

### 4-3 MapReduce编程模型
- 作业差分成Map阶段、Reduce阶段
- Map阶段：Map Tasks
- Reduce阶段：Reduce Tasks

### 4-4 MapReduce编程模型详解
- MapReduce编程模型执行步骤
    - 准备map处理的输入数据
    - Mapper处理
    - Shuffle
    - Reduce处理
    - 输出结果
- MapReduce源码流程图
    - ![MapReduce源码流程图](imgs/mapreduce-src-process.png)
- MapReduce编程模型之核心概念(务必要掌握原理！面试常问)
    - split
    - InputFormat
    - OutputFormat
    - Combiner
    - Partitioner

### 4-10 Combiner操作
- ![Combiner操作](imgs/mapreduce-combiner-process.png)
- Combiner的优点：减少IO，抬升作业的执行性能
- Combiner的局限性：求平均数、除法等操作

### 4-11至4-17看代码Access
- mr/access下

### 4-17自定义Partitioner
- Mapper的输出数据 通过shuffle 按默认的key（也可自定义）分发到不同的reducer中去
- ![MapReduceWc全流程](imgs/mapreduce-wc-whole-process.png)


# 第五章 资源调度框架YARN

### 5-1 课程目录

### 5-2 YARN产生背景
- MapReduce1.x存在的问题
    - master/slave架构：JabTracker/TaskTracker
    - ![MapReduce1.x架构](imgs/mapreduce1.x-framework.png)
    - JobTracker: 单点、压力大
    - 仅仅只能支持mapreduce作业    
- 资源利用率低
    - 所有的计算框架都运行在一个集群中，共享一个集群的资源，按需分配

### 5-3 YARN概述
- YARN概述
    - Yet Another Resource Negotiator
    - 通用的资源管理系统
    - 为上层应用提供统一的资源管理和调度
- 官网文档
    - http://archive.cloudera.com/cdh5/cdh/5/hadoop-2.6.0-cdh5.15.1/hadoop-yarn/hadoop-yarn-site/YARN.html
    - master: resource management: a global ResourceManager (RM)
    - job scheduling/monitoring: per-application ApplicationMaster (AM)
    - slave: NodeManager (NM)

### 5-4 YARN架构详解
- 架构图
    - ![Yarn架构图](imgs/yarn-architecture.png)
- master/slave: RM/NM
- Client、ResourceManager、NodeManager、ApplicationMaster
    - Client: 
        - 向RM提交任务、杀死任务等
    - ApplicationMaster:     
        - 每个应用程序对应一个AM
        - AM向RM申请资源用于在NM上启动对应的task
        - 数据切分
        - 为每个task向RM申请资源（container）
        - 与NM通信
        - 任务的监控
    - NodeManager:    
        - 多个
        - 干活
        - 向RM发送心跳信息、任务的执行情况
        - 接受来自RM的请求来启动任务
        - 处理来自AM的命令
    - ResourceManager:         
        - 集群中同一时刻对外提供服务的只有一个
        - 负责资源相关
        - 处理来自客户端的请求：提交、杀死
        - 启动/监控AM
        - 监控NM
    - Container:
        - 任务的运行抽象
        - memory、cpu...
        - task是运行在container里面的
        - 可以运行am、也可以运行map/reduce task

### 5-5 YARN执行流程
- YARN执行流程
    - ![Yarn执行流程](imgs/yarn-execute-process.png)
    1. 客户端提交一个应用程序到RM
    2. RM在一个NM分配第一个Container
    3. Container中运行作业的AM
    4. AM启动后注册到RM上，并向RM申请资源回来
    5. AM在几个NM上启动申请的资源Container
    6. Container中运行Appication的task

### 5-6 YARN环境部署
- 官网
    - http://archive.cloudera.com/cdh5/cdh/5/hadoop-2.6.0-cdh5.15.1/hadoop-project-dist/hadoop-common/SingleCluster.html
- 流程
    - etc/hadoop/mapred-site.xml
        ```
        <configuration>
            <property>
                <name>mapreduce.framework.name</name>
                <value>yarn</value>
            </property>
        </configuration>
        ```
    - etc/hadoop/yarn-site.xml
        ```
        <configuration>
            <property>
                <name>yarn.nodemanager.aux-services</name>
                <value>mapreduce_shuffle</value>
            </property>
            <property>
                <name>hadoop.tmp.dir</name>
                <value>/home/hadoop/app/tmp</value>
            </property>
        </configuration>
        ```
    - Start ResourceManager daemon and NodeManager daemon
        ```
        $ sbin/start-yarn.sh
        ```
    - Browse the web interface for the ResourceManager
        ```
        http://localhost:8088/
        ```
    - Run a MapReduce job
    - Stop the deamons
        ```
        $ sbin/stop-yarn.sh
        ```

### 5-7 运行hadoop自带的example程序
    ```
    cd $HADOOP_HOME/share/hadoop/mapreduce/
    hadoop jar hadoop-mapreduce-examples-2.6.0-cdh5.15.1.jar pi 2 3
    ```

### 5-8 提交自己开发的MR作业到YARN上执行
    ```
    1) mvn clean package -DskipTests
    2) 把编译出来的jar(target/xxx.jar)以及测试数据上传到服务器上
    3) 执行作业
        hadoop jar xxx.jar 完整的类名（包名+类名） args...
        hadoop jar hadoop-train-v2-1.0.jar com.imooc.bigdata.hadoop.mr.access.AccesYarnApp /access/input/access.log /access/output/
    4) 到YARN UI(hadoop000:8088)去观察
    5) 观察输出目录
    ```
    
# 第六章 电商项目实战

### 6-4 日志内容介绍
- trackinfo_20130721.txt
- 日志字段说明
    - 第二个字段: url
    - 第十四字段: ip
    - 第十八字段: time
    
### 6-7 项目需求
- 统计页面的浏览量
- 统计各省份的浏览量
- 统计页面的访问量

### 6-8 数据处理流程及技术框架
- ![数据处理流程及技术框架](imgs/eshop-practice-data-process-framework.png)
- 看eshopproject代码


# 第七章 数据仓库Hive

### 7-2 Hive产生背景
- MapReduce编程的不便性
- 传统RDBMS人员的需要
    - HDFS上的文件没有schema的概念

### 7-3 Hive是什么
- Hive概念
    - 由Facebook开源
    - 用于解决海量结构化日志数据统计问题
    - 构建在hadoop上的数据仓库
    - 提供的SQL查询语言---HQL
    - 底层支持多种不同的执行引擎
        - Hive1.x只支持MR
        - Hive2.x支持MR/Tez/Spark(默认)

### 7-4 为什么使用Hive
- 为什么
    - 简单、容易上手
    - 为超大数据集设计的计算扩展能力
    - 统一的元数据管理
        - Hive数据是存放在HDFS
        - 元数据信息（记录数据的数据）是存放在MySQL中
        - SQL on Hadoop: Hive、Spark SQL、impala...

### 7-5 Hive在Hadoop生态圈的位置

### 7-6 Hive体系架构
- ![Hive体系架构图](imgs/hive-architecture.png)
- 体系架构详解
    - client: shell、thrift/jdbc(server/jdbc)、WebUI(HUE、Zeppelin)
    - metestore:
        - database: name、location、owner...
        - table: name、location、owner、column name/type...
        - 一般存在MySQL下

### 7-7 Hive部署架构
- ![Hive部署架构图](imgs/hive-deploy-architecture.png)
- 部署体系架构详解
    - hive元数据管理有两个自带的数据库：Derby（只能进行但session操作，一般不推荐使用）、MySQL（生产上使用）
    - MySQL存表库等元数据信息，生产上一定要有主备，hive连vip，解决MySQL单点问题
    - Hive提交到RM上
    - Hive是一个客户端而已，不涉及到集群的概念

### 7-8 Hive与RDBMS的区别
- 支持的
    - 都支持SQL
    - 都支持分布式模式
- 区别的
    - RDBMS实时的，结果返回延迟低
    - Hive是转换为MR执行的，结果返回延迟高，适合批处理的
    - 分布式模式差别比较大，MySQL集群小且部署在专用的昂贵的机器上（PB级数据），Hive构建在普通机器的hadoop集群上（PB数据只是毛毛雨）

### 7-9 Hive部署
- 下载
    - http://archive.cloudera.com/cdh5/cdh/5/
- 解压
- 添加HIVE_HOME到系统环境变量
- 修改配置
    - hive-env.sh
        - 配置HADOOP_HOME
    - hive-site.xml需要自己创建一个
        ```
        <?xml version="1.0"?>
        <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
        
        <configuration>
          <property>
            <name>javax.jdo.option.ConnectionURL</name>
            <value>jdbc:mysql://hadoop000:3306/hadoop_hive?createDatabaseIfNotExist=true</value>
          </property>
        
          <property>
            <name>javax.jdo.option.ConnectionDriverName</name>
            <value>com.mysql.jdbc.Driver</value>
          </property>
        
          <property>
            <name>javax.jdo.option.ConnectionUserName</name>
            <value>root</value>
          </property>
        
          <property>
            <name>javax.jdo.option.ConnectionPassword</name>
            <value>root</value>
          </property>
        </configuration>
        ```
- 拷贝MySQL驱动包到$HIVE_HOME/lib下
- 前提是要安装一个MySQL数据库，yum install去安装

### 7-10 Hive快速入门
- 部署好mysql后
- 运行hive客户端
```
bin/hive
```
- hive中创建一个db
```
create database test_db;
#之后进入mysql中看多了一个hadoop_hive数据库，存hive的元数据信息
#test_db在hadoop上存储位置为dfs://hadoop000:8020/user/hive/warehouse/test_db.db
```
- 创建数据
```
vim helloworld.txt
1       zhangsan
2       lisi
3       wangwu
```
- hive中建表
```
create table helloworld(id int, name string) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';
```
- 导入数据
```
load data local inpath '/home/hadoop/data/helloworld.txt' overwrite into table helloworld;
```
- 查询数据
```
select * from helloworld;
```
- 统计数据自动转换成MR
```
select count(*) from helloworld;
```
- 查看yarn前端
```
http://hadoop000:8088
```

### 7-11 Hive 数据库DDL
- 官网
- Hive数据抽象/结构
    database    --->HDFS一个目录
        table   --->HDFS一个目录
            partition   分区表  --->HDFS一个目录
                bucket  分桶    --->HDFS一个文件
- 举例
    ```
    CREATE (DATABASE|SCHEMA) [IF NOT EXISTS] database_name
      [COMMENT database_comment]
      [LOCATION hdfs_path]
      [WITH DBPROPERTIES (property_name=property_value, ...)];
      
    CREATE DATABASE IF NOT EXISTS hive;
    默认存储位置在hdfs://hadoop000:8020/user/hive/warehouse下，可配置修改
    
    CREATE DATABASE IF NOT EXISTS hive2 LOCATION '/test/location';
    
    CREATE DATABASE IF NOT EXISTS hive3 WITH DBPROPERTIES('creator'='liy');
    
    desc database hive;
    desc database extended hive3;
    
    显示当前命令是在哪个库
    set hive.cli.print.current.db;
    set hive.cli.print.current.db=true;
    ```

### 7-12 Hive 表DDL
- 举例
    ```
    
    CREATE [TEMPORARY] [EXTERNAL] TABLE [IF NOT EXISTS] [db_name.]table_name    -- (Note: TEMPORARY available in Hive 0.14.0 and later)
      [(col_name data_type [column_constraint_specification] [COMMENT col_comment], ... [constraint_specification])]
      [COMMENT table_comment]
      [PARTITIONED BY (col_name data_type [COMMENT col_comment], ...)]
      [CLUSTERED BY (col_name, col_name, ...) [SORTED BY (col_name [ASC|DESC], ...)] INTO num_buckets BUCKETS]
      [SKEWED BY (col_name, col_name, ...)                  -- (Note: Available in Hive 0.10.0 and later)]
         ON ((col_value, col_value, ...), (col_value, col_value, ...), ...)
         [STORED AS DIRECTORIES]
      [
       [ROW FORMAT row_format] 
       [STORED AS file_format]
         | STORED BY 'storage.handler.class.name' [WITH SERDEPROPERTIES (...)]  -- (Note: Available in Hive 0.6.0 and later)
      ]
      [LOCATION hdfs_path]
      [TBLPROPERTIES (property_name=property_value, ...)]   -- (Note: Available in Hive 0.6.0 and later)
      [AS select_statement];   -- (Note: Available in Hive 0.5.0 and later; not supported for external tables)
    
    
    CREATE TABLE emp(
    empno int,
    ename string,
    job string,
    mgr int,
    hiredate string,
    sal double,
    comm double,
    deptno int
    ) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';
    
    #查看表结构
    desc emp;
    desc exended emp;
    desc formatted emp;
    
    ```
    
### 7-12 Hive DML之加载和导出数据
```
# 导入
LOAD DATA [LOCAL] INPATH 'filepath' [OVERWRITE] INTO TABLE tablename [PARTITION (partcol1=val1, partcol2=val2 ...)]

LOCAL: 本地系统，如果没有LOCAL就是指HDFS的路径
OVERWORITE: 是否数据覆盖，如果没有那么就是数据追加

LOAD DATA LOCAL INPATH '/home/hadoop/data/emp.txt' OVERWRITE INTO TABLE emp;

create table emp1 as select * from emp;

# 导出
INSERT OVERWRITE [LOCAL] DIRECTORY directory1
[ROW FORMAT row_format] [STORED AS file_format] (Note: Only available starting with Hive 0.11.0)
SELECT ... FROM ...

INSERT OVERWRITE LOCAL DIRECTORY '/tmp/hive/'
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
SELECT empno,ename,sal,deptno from emp;

在hive中insert、update语句绝对不推荐使用，非常耗费性能，hive是基于离线批处理的

```

# 第八章 电商项目实战Hive实现

### 8-1 课程目录

### 8-2 Hive外部表
- MANAGED_TABLE: 内部表
    - 删除表: HDFS上的数据被删除，Meta信息也被删除
- EXTERNAL_TABLE: 外部表
    - 语法
        ```
        CREATE EXTERNAL TABLE emp_external(
        empno int,
        ename string,
        job string,
        mgr int,
        hiredate string,
        sal double,
        comm double,
        deptno int
        ) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
        LOCATION '/external/emp/';
        
        LOAD DATA LOCAL INPATH '/home/hadoop/data/emp.txt' OVERWRITE INTO TABLE emp_external;
        ```
    - 删除表: HDFS上的数据不被删除，Meta信息被删除
    - 因此外表更安全，有限考虑使用这种表
    
### 8-3 track_info分区表的创建
- 建分区表
    ```
    create external table track_info(
    ip string,
    country string,
    province string, 
    city string,
    usl string,
    time string,
    page string
    ) partitioned by (day string)
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' 
    LOCATION '/project/trackinfo/';
    
    LOAD DATA INPATH 'hdfs://hadoop000:8020/eshopproject/input/etl' OVERWRITE INTO TABLE track_info partition(day='2013-07-21');
    ```

### 8-4 执行etl

### 8-5 使用hive完成统计功能
- 统计sql
    ```
    # pv
    select count(*) from track_info where day='2013-07-21';
    
    # province
    select province, count(*) as cnt from track_info where day='2013-07-21' group by province;
    
    # 省份统计结果目标表
    create table track_info_province_stat(
    province string,
    cnt bigint
    ) partitioned by (day string)
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

    insert overwrite table track_info_province_stat partition(day='2013-07-21') select province, count(*) as cnt from track_info where day='2013-07-21' group by province;
    
    select * from track_info_province_stat; 
    ```

### 8-6 总结
- 只用MR写个ETL，之后所有尽量用hive，用hive做ETL需要些udf函数
- 如果一个框架不能落地到SQL层面，这个框架就不是一个非常适合的框架
