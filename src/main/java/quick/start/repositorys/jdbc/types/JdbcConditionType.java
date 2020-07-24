package quick.start.repositorys.jdbc.types;

import quick.start.constant.CommonConstant;
import quick.start.repositorys.types.ConditionType;

public enum JdbcConditionType {

     EQUAL(ConditionType.EQUAL, "="),
     NOT_EQUAL(ConditionType.NOT_EQUAL, "!="),
     LESS_THEN(ConditionType.LESS_THEN, "<"),
     LESS_THEN_OR_EQUAL(ConditionType.LESS_THEN_OR_EQUAL, "<="),
     GREATER_THEN(ConditionType.GREATER_THEN, ">"),
     GREATER_THEN_OR_EQUAL(ConditionType.GREATER_THEN_OR_EQUAL, ">="),
     IN(ConditionType.IN, "in"),
     LIKE(ConditionType.LIKE, "like");

     private String value;
     private ConditionType type;

     private JdbcConditionType(ConditionType type, String value) {
          this.type = type;
          this.value = value;
     }

     public static String getValue(ConditionType type) {
          for (JdbcConditionType jdbcConditionType : JdbcConditionType.values()) {
               if (jdbcConditionType.type.equals(type)) {
                    return jdbcConditionType.value;
               }
          }
          return CommonConstant.NULL;
     }

}
