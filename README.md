# quick start
> 一个Java快速开发框架，支持mysql、xml、json等的基本操作。

### Mysql功能

##### 默认支持功能列表

| 方法                                                         | 说明                                       |
| ------------------------------------------------------------ | ------------------------------------------ |
| boolean has(Serializable id)                                 | 根据主键检查记录是否存在                   |
| E findById(Serializable id)                                  | 根据主键查找对象                           |
| List<E> findByIds(Collection<? extends Serializable> ids)    | 根据主键批量查找对象                       |
| List<E> find()                                               | 查询所有的记录                             |
| List<E> findByColumn(String column, Serializable value)      | 根据指定字段查询记录                       |
| List<E> findByColumn(String column, Collection<? extends Serializable> values) | 根据指定字段查询记录                       |
| List<E> find(Conditions conditions)                          | 根据条件查询，条件的具体用法请看下面的案例 |
| Paginator<E> findByPage(Conditions conditions, Integer pageSize, Integer pageNumber) | 分页查询                                   |
| Integer delete(Serializable id)                              | 根据主键删除                               |
| Integer delete(List<? extends Serializable> ids)             | 根据主键删除                               |
| Integer delete(String column, Collection<? extends Serializable> values) | 根据字段删除                               |
| Integer insert(E entity)                                     | 保存对象                                   |
| Integer insert(List<E> entitys)                              | 批量保存对象                               |
| Integer update(E entity)                                     | 修改对象（根据主键修改）                   |
| Integer update(String id, String key, Object value)          | 修改                                       |
| Integer update(List<? extends Serializable> ids, Map<String, Object> data) | 批量修改                                   |
| Integer update(String id, Map<String, Object> data)          | 修改                                       |
| Integer update(List<? extends Serializable> ids, String key, Object value) | 修改                                       |

##### 第一步：注入jdbcTemplate

```java
@Bean
public JdbcTemplate jdbcTemplate() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
    return jdbcTemplate;
}
```

##### 第二步：定义对象

```java
@Data
@Table("Orders")//定义表名
public class Order implements Entity {
     @Generated//设置自增属性
     private Integer orderId;
     @PrimaryKey//定义主键
     private String orderCode;
     private String remark;
}
```

##### 第三步：定义Repository

```java
@Repository
public class OrderRepository extends JdbcRepository<Order> {
		//nothing to do
}
```

##### 第四步：增删改查

```java
//新增
Order order = new Order();
order.setOrderCode(String.valueOf(System.currentTimeMillis()));
order.setRemark(LocalDateTime.now().toString());
orderRepository.insert(order);

//修改
order.setRemard("remark");
orderRepository.update(order);
  
//查询
orderRepository.findById("1593238076676"));
  
//删除
orderRepository.delete("1593238076676");
```

### Conditions条件使用

```java
Conditions conditions = new Conditions()
  .equal("orderCode", "1593238616437")//等于
  .lessThenOrEqual("orderId", 13)//小于
  .greaterThen("orderId", 10)//大于
  .desc("orderId")//降序
  .asc("orderCode")//升序
  .limit(10);//limit-分页
orderRepository.find(conditions).forEach(x -> {
  logger.info("{}", x.toString());
});
```

