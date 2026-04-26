/*    */ package org.ms.donutduels.foliascheduler.reflectionremapper.proxy;
/*    */ 
/*    */ import java.lang.reflect.Proxy;
/*    */ import org.checkerframework.checker.nullness.qual.NonNull;
/*    */ import org.checkerframework.framework.qual.DefaultQualifier;
/*    */ import org.ms.donutduels.foliascheduler.reflectionremapper.ReflectionRemapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @DefaultQualifier(NonNull.class)
/*    */ public final class ReflectionProxyFactory
/*    */ {
/*    */   private final ReflectionRemapper reflectionRemapper;
/*    */   private final ClassLoader classLoader;
/*    */   
/*    */   private ReflectionProxyFactory(ReflectionRemapper reflectionRemapper, ClassLoader classLoader) {
/* 35 */     this.reflectionRemapper = reflectionRemapper;
/* 36 */     this.classLoader = classLoader;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <I> I reflectionProxy(Class<I> proxyInterface) {
/* 50 */     return (I)Proxy.newProxyInstance(this.classLoader, new Class[] { proxyInterface }, new ReflectionProxyInvocationHandler<>(proxyInterface, this.reflectionRemapper));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ReflectionProxyFactory create(ReflectionRemapper reflectionRemapper, ClassLoader classLoader) {
/* 73 */     return new ReflectionProxyFactory(reflectionRemapper, classLoader);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\reflectionremapper\proxy\ReflectionProxyFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */