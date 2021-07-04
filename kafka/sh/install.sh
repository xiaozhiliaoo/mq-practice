
mkdir -p ~/sofewate/kafka/node1
mkdir -p ~/sofewate/kafka/node2
mkdir -p ~/sofewate/kafka/node3

cd ~/sofewate/

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