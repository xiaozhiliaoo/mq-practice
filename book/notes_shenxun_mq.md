### 沈询
http://i.youku.com/i/UMTcwMTg3NDc1Mg==?spm=a2hzp.8253869.0.0

taobao最重要的三大产品：ons(Open Notification Service-metaq)，drds(Distributed Relational Database Service)，tddl(Taobao Distributed Data Layer)
hsf(high speed framework)

ONS(Open Notification Service)

以故事的方式讲。

最终一致性，全局来看，最后系统都会成功，某些系统宕机了，但是恢复为正常，就可以了，
因为消息没有被消费。

后端挂系统，前端不会受到影响。

异步和解耦区别？

消息系统，原理都很简单，难点在于权衡与取舍。

消息一定会堆积，海量数据堆积问题。

发送处理逻辑简单，

避免任何地方不能扩展，任何一点必须能够扩展，要不然瓶颈一定在哪里。

任意一个点必须是集群。

按需扩缩，无需关注架构上无法扩展。

面向扩展而设计的。

异步多队列。

消息堆积，宕机不能接受，写入消息很慢，交易过程很慢，无论堆积多少，系统不能挂，不能慢。

QPS，TPS高 因为使用内存，考虑消息堆积了吗？只要一堆积，就挂了。

kafka日志堆积，日志吞吐量，不注重延时，拉模型。

拉关注吞吐量。推关注延时。

推很快，订阅者不用处理业务逻辑，收消息就好，拉需要业务端自定义逻辑。

降低消息消费延迟，推拉结合。

订阅者拉mq消息，mq此时没有消息，hold住，等有消息了在推过去。减低消息消费延时。
拉很及时。

ONS，注重分布式系统能力。
小型企业mq系统：设计思路变化。

消息处理能力不行，消息会堆积，堆积之后系统依然很好。

mqms单机，扩展很难。

为什么要分组？随机选择一台机器消费，不会发给别的机器集群。

消息乱序产生：要吞吐量+容错还是方便理解？
容易写的代码，一般性能不好，

消息发出去什么顺序，消息接收什么顺序。但是几个poducer发，几个broker接，几个consunme消费？
发给同一个server，发两个也行，但是要通知。

最优模型，尽可能有序，乱序了在处理

付款消息先到，创建后到，也无所谓的。恰好不需要解决。


消息重复时候，针对消费者，因为网络问题。

去重成本代价很高，期望用户恰好不需要去重，方案不是从mq，而是用户自己，

走了网络一定会有重复，并非mq本身带来的问题，用户不需要，mq做了很麻烦，如果需要，需要考虑代价。

去重表。本地事务。去重表有了消息被处理， 就不需要处理，写记录，一次写变成了两次写。

恰好不需要是最好的。去重表定期删除，去重表事务的，机器资源处理去重表。

去重表 VS 好的设计？ 为什么交给用户了？

接的机器多，系统一定会堆积的。

延时很低，用户不会感受到最终一致性。

Topic和Tag，消息组：动态机器归属某个组内，消息发送者，接受者无限扩容。

rocketmq 部署了5台broker，数据分布问题，文件系统怎么保证数据一样呢？

系统追求扩展，本身就是云化系统，云化系统本身追求扩展性。

只和中间件打交道，交易创建时间，只要中间件稳定可靠就好了，异步解耦，无需等待后端请求，
直冲挂了也无所谓，重要消息还在，最终一致，中间可能会因为宕机而不可用，但是重启就可以继续。
通过消息中间件，对系统状态进行全局统一

1  后端挂，没事，解耦
2  无需等待后端完成功能
3  一个系统挂了，中间状态可能会乱，但是恢复了，消息会推送给他，保持了系统状态的最终一致性。
4 消息并发发送到中间件，消息中间件并行接受。

多队列多冗余多复制

无论堆了多少消息，系统不能慢，系统不能挂。外面的系统看起来tpsqps很好，前提使用内存大，只要消息一堆积tpsqps下降快，突然一下子就奔溃了。

kafka 日志分析消息与统计，主要关注日志拉取吞吐量，不是特别关注拉取延迟。

保证顺序的消息时候，一定只能一个发布者，一个订阅者之间的关系，三个都是一个，系统吞吐量都很低，完全做不到，必然代价。

只要经过网络，必然会出现重复消费

事务本质就是相互等待的过程。

消息吞吐量与延迟平衡，延迟低，用户体验好，体会不到系统是最终一致性的系统。

秘书=消息系统。解放自己，事情交给秘书了。专业分工。这事解耦。

快的核心，用户等待时间减少。直观并行操作。

单次完成最慢的时间，并行提交最慢的时间。如果最慢的系统慢了怎么办？接口并行处理，随便一台机器挂了，
交易流程阻塞。

不影响交易创建，就不会影响用户体验。直冲挂了，起来后，会重新发给直冲系统。全局来看，
交易创建和所有系统一致，最终一致：中间不正常，正常了就可以回复了，通过mq进行所有全局系统状态统一。

消息系统原理不复杂，难在权衡与取舍。不理解取舍，选型无从下手，设计假定是什么？

淘宝最坏情况都会发生，因为数据量大，需要的tps，qps。

降低投递延迟。

外面mq并不是面向大规模分布式系统的，消息一定会堆积。

发送逻辑简单，

任何地方必须自由扩展，整个系统才是可扩展，不能有一个点不能扩展，否则就是单点。
任意一个点必须是集群。
三者都可以按需扩缩。发布者不够了，可以加，mq，订阅者可以加。
系统设计面向扩展而生，和其他mq最大不同，按需扩缩能力。

单机raid，磁盘挂了，数据不丢失。
跨机异步复制。
多组server，一组宕机，另一组恢复。
多队列，多冗余，多复制版本保证数据安全和高可用。

后端消费者挂了，消息就会堆积。
前端写入很快，生产快，消费慢，导致消息堆积。几十亿数据堆积。

保证交易创建高可用，高性能。
高可用：交易创建不能失败，写入延时不能慢。
无论堆积多少消息，系统不能挂，系统不能慢。
外面mq看起来tps，qps，为什么达到？激进使用内存，数据很漂亮，光使用内存，消息堆积立马挂了。突然挂了。

大量堆积稳定性和系统延时。

推：延时低，消费者逻辑简单。
拉：频次快，mq压力大，拉消息关注吞吐量。
推拉结合：有消息发送，然后在拉。
ONS：先拉然后推送。推拉平衡。

ONS强调分布式系统能力，不同带来设计思路变化。

真正面向分布式系统。面向失败设计系统。

Topic一级消息类型，Tag也叫MessageType
基于Tag做消息检索，消息类型好检索，归类和方便管理，

发送订阅组，外面消息服务没有集群概念。mqms扩展很难。

处理能力不够机器立马加，处理能力够，下线。

组含义：标记属于哪一个集群，归组，加入知道做什么了，组名。

随机选择一台，只发送到一台。只发给c1。

所有mq面临问题：乱序和重复。

消息乱序产生：要吞吐量还是容易理解？

乱序复杂。

消息发出去什么顺序，发给一台，还是两台，两台设计系统吞吐量低，发给同一台Server，
发两台也能。

只能死等着，顺序问题。一定订阅者挂了，整个队列就停止了。

保证顺序一定是一对一。系统吞吐量上不去，受到了单机限制，吞吐量怎么最大化，做不到，系统化
必然代价。

是否需要顺序？代价很高。只要有顺序，并行度上不去，越全局，系统并行上不去。一次乱序，之前努力白费。

最优模型：尽可能有序。

优先级队列，一个错了就会全错，异常处理，让用户多想下。只能保证99.999%有序，系统设计，每一次考虑到
乱序的可能，用分布式系统消息必须的代价。

世界上解决一个计算机问题最简单的方法：“恰好”不需要解决它！—— 沈询

期望不在这种问题付出代价，期望用户切好不需要他。订单状态，订单和订单间先后顺序没意义，
只需要订阅付款消息，即使创建消息后到。

恰好不需要：
1 付款
2 转账操作，多人同时转账。
3 交易编号，后端恢复顺序。

分布式系统Happen Before。
真正关心交易顺序很少，只关注一个状态，最终做到一致性很简单的，完全不关心顺序、
如果被迫需要关注顺序，消息队列无序不意味着消息无序。TCP协议。(包顺序无序，但是包里面有消息id号码)
核心思路：消息发送加编号，接受恢复编号。
无序队列让消息发送接收保持有序性？
全局有序，0,1,2,3,4,5 发布者，订阅者必须只有一个。中间吞吐量可以很大，集群。尽量减少队列锁的颗粒度。
吞吐量大，性能好，维持局部有序。并发度订单个数。几千万订单，并行度高。
ID取模，分6个子队列。
全局有序，
尽可能乱序，最低限度顺序，系统性能一定能上来。

mq分布式事务有所取舍，为了写出高效代码。

消息重复直接叫鲫鱼哥两次。

幂等消息不用去重（DB层），非幂等消息需要去重。

为什么MQ不去处理去重？成本代价很高，成本低价太高了。
去重表有消息了，就不用处理了。

“恰好不需要去重”。去重表：额外tps，定期清除，需要资源处理去重表。
设计优势：1 恰好不需要 2 去重表（为什么交给用户）

最低成本完成事务，消息队列。事务消息。

smith账号销号了，只能人工处理，10万行代码bug比三年出现问题，直到成功为止。努力送达模型：不需要处理回滚，应用处理成本很低。
努力送达模型：完成系统事务操作，转账类操作，交易适用了这种模式，交易本身非常容易扩展，支付宝扩展性，处理大交易量。
mq的分布式事务模型支持支付宝交易量，系统没有瓶颈没有单点。

消息就是MQ。








