package org.ms.donutduels.foliascheduler.reflectionremapper.proxy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

@DefaultQualifier(NonNull.class)
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodName {
  String value();
}


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\reflectionremapper\proxy\annotation\MethodName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */