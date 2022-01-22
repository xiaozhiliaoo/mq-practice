jps -l | grep -E 'NamesrvStartup|BrokerStartup' | grep -v grep | awk '{print $1}' | xargs kill -9
