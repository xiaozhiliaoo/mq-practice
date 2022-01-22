mkdir -p ~/sofewate/kafka/node1
mkdir -p ~/sofewate/kafka/node2
mkdir -p ~/sofewate/kafka/node3

cd ~/sofeware/

wget https://mirror-hk.koddos.net/apache/kafka/2.7.0/kafka_2.13-2.7.0.tgz

tar -zxvf kafka_2.13-2.7.0.tgz

cp -r kafka_2.13-2.7.0/  node1/
cp -r kafka_2.13-2.7.0/  node2/
cp -r kafka_2.13-2.7.0/  node3/

export JAVA_HOME=/usr/java/jdk1.8.0_131
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib:$CLASSPATH
export JAVA_PATH=${JAVA_HOME}/bin:${JRE_HOME}/bin
export PATH=$PATH:${JAVA_PATH}


-- 启动过程
/home/lili/software/kafka/node1/bin/zookeeper-server-start.sh -daemon /home/lili/software/kafka/node1/config/zookeeper.properties
/home/lili/software/kafka/node1/bin/kafka-server-start.sh -daemon /home/lili/software/kafka/node1/config/server.properties

/home/lili/software/kafka/node2/bin/kafka-server-start.sh -daemon /home/lili/software/kafka/node2/config/server.properties


/home/lili/software/kafka/node3/bin/kafka-server-start.sh -daemon /home/lili/software/kafka/node3/config/server.properties


-- 创建topic

bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic my-topic --partitions 3 --replication-factor 3

-- 查找所有topic
bin/kafka-topics.sh --zookeeper localhost:2181 -list

bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic  my-topic


bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092
bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092

bin/kafka-console-consumer.sh --topic __consumer_offset --bootstrap-server localhost:9092 --formatter 'kafka.coordinator.group.GroupMetadataManager$OffsetsMessageFormatter'



bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9093 --list
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9094 --list

bin/kafka-topics.sh --create --zookeeper localhost:2181 --partitions 6 --replication-factor 3 --topic tests-topic --config delete.retention.ms=259200000

bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic  tests-topic

bin/kafka-topics.sh --create --zookeeper localhost:2181 --replica-assignment 0:1,1:2,0:2,1:2 --topic tests-topic2

bin/kafka-producer-perf-test.sh --topic tests-topic2 --throughput 1 --num-records 500000 --record-size 100 --producer-props bootstrap.servers=localhost:9092 acks=-1

bin/kafka-console-consumer.sh --bootstrap-server=localhost:9092 --topic tests-topics2 --from-beginning --consumer-property group.id=test-group1

bin/kafka-console-consumer.sh --bootstrap-server=localhost:9092 --topic tests-topics2 --from-beginning --consumer-property group.id=test-group2

bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list

bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group test-group1



bin/kafka-consumer-groups.sh --bootstrap-server th011.corp.yodao.com:9092 --describe --group logistics --members --verbose

bin/kafka-topics.sh --bootstrap-server th011.corp.yodao.com:9092 --topic courseop-event-bus --describe