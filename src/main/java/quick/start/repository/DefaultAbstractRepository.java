package quick.start.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import quick.start.entity.Entity;
import quick.start.parser.CommandParser;
import quick.start.repository.command.CommandFactory;
import quick.start.repository.command.CommandForEntity;
import quick.start.repository.command.Select;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class DefaultAbstractRepository<E extends Entity> implements Repository<E> {

     protected Logger logger = LoggerFactory.getLogger(getClass());
     protected CommandFactory<E> commandFactory = new CommandFactory<>();

     protected abstract CommandParser commandParser();

     protected abstract List<E> select(Select<E> select);

     @Override
     public E findById(Serializable id) {
          Assert.notNull(id, "查询主键值为空");
          Select<E> select = commandFactory.select();
          checkPrimaryKeyAndTableName(select);
          select.equal(select.getPrimaryKey(), id);
          List<E> entitys = select(select);
          //如果有多个，就返回第一个。也可以自行修改抛出异常。
          return CollectionUtils.isEmpty(entitys) ? null : entitys.get(0);
     }

     @Override
     public List<E> findByIds(Collection<? extends Serializable> ids) {
          Assert.notEmpty(ids, "查询主键值为空");
          Select<E> select = commandFactory.select();
          checkPrimaryKeyAndTableName(select);
          select.whereIn(select.getPrimaryKey(), ids);
          return select(select);
     }

     @Override
     public List<E> findByColumn(String column, Serializable value) {
          Select<E> select = commandFactory.select();
          checkTableName(select);
          select.equal(column, value);
          return select(select);
     }

     @Override
     public List<E> findByColumn(String column, Collection<? extends Serializable> values) {
          return null;
     }

     protected void checkTableName(CommandForEntity<E> commandForEntity) {
          Assert.notNull(commandForEntity.getMeta().getTableName(), "未设置表名，可通过注解@Table来定义表名");
     }

     protected void checkPrimaryKey(CommandForEntity<E> commandForEntity) {
          Assert.notNull(commandForEntity.getMeta().getPrimaryKey(), "未设置主键，可通过注解@PrimaryKey来定义主键");
     }

     protected void checkPrimaryKeyAndTableName(CommandForEntity<E> commandForEntity) {
          checkPrimaryKey(commandForEntity);
          checkTableName(commandForEntity);
     }

}
