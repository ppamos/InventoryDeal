### 高性能库存组件

##### 什么是高性能库存组件？

高性能库存组件来自于之前项目上的需求，利用redis数据读写速度快的和lua脚本在redis执行原子性的特点来实现库存的扣减，查询

。从而提高接口的性能。

##### 高性能库存组件的架构

**AbstractInventoryDeal.class** 库存处理的框架类，该类上面定义了库存冻结，消耗，解冻，往redis添加库存的基本流程。

**InventorySkuRedisDeal.class**实际执行库存操作的类，继承并实现AbstractInventoryDeal.class 上面的doFreezeInventory，doConsumerInventory，doReleaseInventory，doAddInventory，doAckConsumer等模板方法。通过调用InventoryRedisExecutor上的方法来实现redis的冻结，消耗，解冻。

InventorySkuRedisDeal.class 对于订单系统，它可能关注的点是库存id为1的库存有100个，所以冻结10个库存时候的数据结构可以是库存1，数量10，但是对于秒杀系统，关注点更精细化，可能更关注于库存的每个数量，所以库存id为1的库存每个数量都需要给上对应的编号，比如x1，x2....等等，所以扣减的数据结构应该是1-x1，1-x2。这样他们在redis上面的实现和订单扣减的实现是不一样的。**ps：该类待实现**

**InventoryRedisExecutor.class**在redis操作库存的接口类，具体实现SkuDealRedisExecutor.class

**FreezeInventoryListener,ConsumerInventoryListener,ReleaseInventoryListener,AckInventoryListener,AddInventoryListener**监听接口类，负责监听库存冻结，消耗，解冻，添加等动作，可以实现对应的接口来实现对这些动作的前后，以及动作执行异常后的操作。

##### 高性能库存组件接口依赖关系

![接口依赖关系](.\static\库存组件接口层设计.jpg)

##### 库存组件状态流转图

![](.\static\库存流转的各个状态.jpg)

##### 库存组件生命周期流转图

![](.\static\库存操作生命周期数据流转图.jpg)

##### 库存组件冻结

![](.\static\库存冻结.jpg)

##### 库存组件消耗

![](.\static\库存消耗.jpg)

##### 库存组件redis上的数据模型

![](.\static\redis模型.jpg)
