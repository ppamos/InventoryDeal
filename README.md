### 高性能库存组件

##### 什么是高性能库存组件？

高性能库存组件来自于之前项目上的需求，利用redis数据读写速度快的和lua脚本在redis执行原子性的特点来实现库存的扣减，查询

。从而提高接口的性能。

##### 高性能库存组件的架构

**AbstractInventoryDeal.class** 库存处理的框架类，该类上面定义了库存冻结，消耗，解冻，往redis添加库存的基本流程。

**InventorySkuRedisDeal.class **实际执行库存操作的类，继承并实现AbstractInventoryDeal.class 上面的doFreezeInventory，doConsumerInventory，doReleaseInventory，doAddInventory，doAckConsumer等模板方法。通过调用InventoryRedisExecutor上的方法来实现redis的冻结，消耗，解冻。

InventorySkuRedisDeal.class 对于订单系统，它可能关注的点是库存id为1的库存有100个，所以冻结10个库存时候的数据结构可以是库存1，数量10，但是对于秒杀系统，关注点更精细化，可能更关注于库存的每个数量，所以库存id为1的库存每个数量都需要给上对应的编号，比如x1，x2....等等，所以扣减的数据结构应该是1-x1，1-x2。这样他们在redis上面的实现和订单扣减的实现是不一样的。**ps：该类待实现**

**InventoryRedisExecutor.class **在redis操作库存的接口类，具体实现SkuDealRedisExecutor.class

**FreezeInventoryListener,ConsumerInventoryListener,ReleaseInventoryListener,AckInventoryListener,AddInventoryListener**监听接口类，负责监听库存冻结，消耗，解冻，添加等动作，可以实现对应的接口来实现对这些动作的前后，以及动作执行异常后的操作。

##### 高性能库存组件接口依赖关系

![接口依赖关系](https://gitee.com/ppamos/inventory-deal-1/raw/main/static/%E5%BA%93%E5%AD%98%E7%BB%84%E4%BB%B6%E6%8E%A5%E5%8F%A3%E5%B1%82%E8%AE%BE%E8%AE%A1.jpg)

##### 库存组件状态流转图

![](https://gitee.com/ppamos/inventory-deal-1/raw/main/static/%E5%BA%93%E5%AD%98%E6%B5%81%E8%BD%AC%E7%9A%84%E5%90%84%E4%B8%AA%E7%8A%B6%E6%80%81.jpg)

##### 库存组件生命周期流转图

![](https://gitee.com/ppamos/inventory-deal-1/raw/main/static/%E5%BA%93%E5%AD%98%E6%93%8D%E4%BD%9C%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F%E6%95%B0%E6%8D%AE%E6%B5%81%E8%BD%AC%E5%9B%BE.jpg)

##### 库存组件冻结

![](https://gitee.com/ppamos/inventory-deal-1/raw/main/static/%E5%BA%93%E5%AD%98%E5%86%BB%E7%BB%93.jpg)

##### 库存组件消耗

![](https://gitee.com/ppamos/inventory-deal-1/raw/main/static/%E5%BA%93%E5%AD%98%E6%B6%88%E8%80%97.jpg)

##### 库存组件redis上的数据模型

![](https://gitee.com/ppamos/inventory-deal-1/raw/main/static/redis%E6%A8%A1%E5%9E%8B.jpg)
