sh /opt/mq/rocketmq/rocketmq-node1/bin/mqnamesrv -c /opt/mq/rocketmq/rocketmq-node1/conf/namesrv.properties > /opt/mq/rocketmq/rocketmq-node1/logs/namesrv.log 2>&1 &

sh /opt/mq/rocketmq/rocketmq-node2/bin/mqnamesrv -c /opt/mq/rocketmq/rocketmq-node2/conf/namesrv.properties > /opt/mq/rocketmq/rocketmq-node2/logs/namesrv.log 2>&1 &

sh /opt/mq/rocketmq/rocketmq-node1/bin/mqbroker autoCreateTopicEnable=true  -c /opt/mq/rocketmq/rocketmq-node1/conf/2m-noslave/broker-a.properties > /opt/mq/rocketmq/rocketmq-node1/logs/broker.log 2>&1 &

sh /opt/mq/rocketmq/rocketmq-node2/bin/mqbroker autoCreateTopicEnable=true  -c /opt/mq/rocketmq/rocketmq-node2/conf/2m-noslave/broker-b.properties > /opt/mq/rocketmq/rocketmq-node2/logs/broker.log 2>&1 &
