package org.lili.mq.kafka;

import org.apache.kafka.common.errors.PolicyViolationException;
import org.apache.kafka.server.policy.CreateTopicPolicy;

import java.util.Map;

/**
 * @author lili
 * @date 2021/7/4 16:52
 */
public class PolicyDemo implements CreateTopicPolicy {
    @Override
    public void configure(Map<String, ?> configs) {
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public void validate(RequestMetadata requestMetadata)
            throws PolicyViolationException {
        if (requestMetadata.numPartitions() != null || requestMetadata.replicationFactor() != null) {
            if (requestMetadata.numPartitions() < 5) {
                throw new PolicyViolationException("Topic should have at least 5 partitions, received: "
                        + requestMetadata.numPartitions());
            }
            if (requestMetadata.replicationFactor() <= 1) {
                throw new PolicyViolationException("Topic should have at least 2 replication factor, recevied: "
                        + requestMetadata.replicationFactor());
            }
        }
    }
}
