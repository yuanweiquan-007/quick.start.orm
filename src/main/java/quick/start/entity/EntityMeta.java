package quick.start.entity;

import quick.start.annotation.PrimaryKey;
import quick.start.annotation.Table;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Data
public class EntityMeta<E extends Entity> {

     private Class<E> eClass;
     private String tableName;
     private String primaryKey;

     private EntityMeta() {
     }

     public static <E extends Entity> EntityMeta<E> of(Class<E> eClass) {
          EntityMeta<E> meta = new EntityMeta<>();
          meta.setEClass(eClass);
          if (eClass.isAnnotationPresent(Table.class)) {
               meta.setTableName(eClass.getAnnotation(Table.class).value());
          }
          if (eClass.isAnnotationPresent(PrimaryKey.class)) {
               meta.setPrimaryKey(eClass.getAnnotation(PrimaryKey.class).value());
          }
          if (StringUtils.isEmpty(meta.primaryKey)) {
               parserPrimaryFromField(eClass, meta);
          }
          return meta;
     }

     public E newInstance() {
          try {
               return eClass.newInstance();
          } catch (Exception ex) {
               ex.printStackTrace();
               return null;
          }
     }

     private static <E extends Entity> void parserPrimaryFromField(Class<E> eClass, EntityMeta<E> meta) {
          Field[] declaredFields = eClass.getDeclaredFields();
          for (Field field : declaredFields) {
               if (field.isAnnotationPresent(PrimaryKey.class)) {
                    PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
                    if (StringUtils.isEmpty(primaryKey.value())) {
                         meta.setPrimaryKey(field.getName());
                         break;
                    }
               }
          }
     }

}
