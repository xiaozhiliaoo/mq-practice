# 单机 2m-noslave
mkdir -p /opt/mq/rocketmq
wget -P /opt/mq/rocketmq https://mirror.bit.edu.cn/apache/rocketmq/4.7.0/rocketmq-all-4.7.0-bin-release.zip
unzip /opt/mq/rocketmq/rocketmq-all-4.7.0-bin-release.zip
mv /opt/mq/rocketmq/rocketmq-all-4.7.0-bin-release /opt/mq/rocketmq/rocketmq-node1
cp -r /opt/mq/rocketmq/rocketmq-node1 /opt/mq/rocketmq/rocketmq-node2

mkdir -p /opt/mq/rocketmq/rocketmq-node1/store
mkdir -p /opt/mq/rocketmq/rocketmq-node1/store/commitlog
mkdir -p /opt/mq/rocketmq/rocketmq-node1/store/consumequeue
mkdir -p /opt/mq/rocketmq/rocketmq-node1/store/index
mkdir -p /opt/mq/rocketmq/rocketmq-node1/logs

mkdir -p /opt/mq/rocketmq/rocketmq-node2/store
mkdir -p /opt/mq/rocketmq/rocketmq-node2/store/commitlog
mkdir -p /opt/mq/rocketmq/rocketmq-node2/store/consumequeue
mkdir -p /opt/mq/rocketmq/rocketmq-node2/store/index
mkdir -p /opt/mq/rocketmq/rocketmq-node2/logs

# config nameserver naemserver只配置一个也可以
touch /opt/mq/rocketmq/rocketmq-node1/conf/namesrv.properties
echo "listenPort=9876" >> /opt/mq/rocketmq/rocketmq-node1/conf/namesrv.properties
sh /opt/mq/rocketmq/rocketmq-node1/bin/mqnamesrv -c /opt/mq/rocketmq/rocketmq-node1/conf/namesrv.properties > /opt/mq/rocketmq/rocketmq-node1/logs/namesrv.log 2>&1 &

touch /opt/mq/rocketmq/rocketmq-node2/conf/namesrv.properties
echo "listenPort=9877" >> /opt/mq/rocketmq/rocketmq-node2/conf/namesrv.properties
sh /opt/mq/rocketmq/rocketmq-node2/bin/mqnamesrv -c /opt/mq/rocketmq/rocketmq-node2/conf/namesrv.properties > /opt/mq/rocketmq/rocketmq-node2/logs/namesrv.log 2>&1 &

# 修改runbroker.sh  JAVA_OPT="${JAVA_OPT} -server -Xms1g -Xmx1g -Xmn512m" 默认配置8g，太大了，vm起不来
#nohup sh /opt/mq/rocketmq/rocketmq-node1/bin/mqbroker -c /opt/mq/rocketmq/rocketmq-node1/conf/2m-noslave/broker-a.properties >/dev/null 2>&1 &


# start brokera
sh /opt/mq/rocketmq/rocketmq-node1/bin/mqbroker autoCreateTopicEnable=true  -c /opt/mq/rocketmq/rocketmq-node1/conf/2m-noslave/broker-a.properties > /opt/mq/rocketmq/rocketmq-node1/logs/broker.log 2>&1 &
# start brokerb
sh /opt/mq/rocketmq/rocketmq-node2/bin/mqbroker autoCreateTopicEnable=true  -c /opt/mq/rocketmq/rocketmq-node2/conf/2m-noslave/broker-b.properties > /opt/mq/rocketmq/rocketmq-node2/logs/broker.log 2>&1 &

# rocketmq-console-ng install
java -jar /opt/mq/rocketmq/console/rocketmq-console-ng-1.0.1.jar --server.port=8090  --rocketmq.config.namesrvAddr='0.0.0.0:9876;0.0.0.0:9877' > /opt/mq/rocketmq/console/console.log 2>&1 &


