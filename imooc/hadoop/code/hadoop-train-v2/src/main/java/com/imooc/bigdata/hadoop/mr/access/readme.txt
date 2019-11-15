 自定义复杂类型

 access.log
    第二个字段：手机号
    倒数第三字段：上行流量
    倒数第二字段：下行流量

 需求：统计每个手机号上行流量和、下行流量和、总的流量和（上行流量和+下行流量和）

 Access.java
    手机号、上行流量、下行流量、总流量

 求和：根据手机号进行分组，然后把该手机号对应的上下行流量加起来

 Mapper：把手机号、上行流量、下行流量 拆开
    把手机号作为key，把Access作为value写出去

 Reducer：（手机号，<Access, Access>）

 Partitioner：
 public class HashPartitioner<K, V> extends Partitioner<K, V> {

   /** Use {@link Object#hashCode()} to partition. */
   public int getPartition(K key, V value,
                           int numReduceTasks) {
     return (key.hashCode() & Integer.MAX_VALUE) % numReduceTasks;
   }

 }
 - Partitioner决定maptask输出的数据交由哪个reducetask处理
 - numReduceTasks 作业所指定的reducer的个数，决定了reduce作业输出文件的个数
 - HashPartitioner 是mapreduce默认的分区规则，分发的key的hash值与reduce task个数取模

 需求：将统计结果按手机号前缀进行分区，并输出到不同的输出文件中去
    - 13* ==> ...
    - 15* ==> ...
    - other ==> ...
    如上所示最终输出有3类，所以reduce数应该就是有3个，所以numReduceTasks==3