/*     */ package org.ms.donutduels.foliascheduler.reflectionremapper.internal.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.UnaryOperator;
/*     */ import org.checkerframework.checker.nullness.qual.NonNull;
/*     */ import org.checkerframework.framework.qual.DefaultQualifier;
/*     */ import org.ms.donutduels.foliascheduler.reflectionremapper.proxy.annotation.Proxies;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @DefaultQualifier(NonNull.class)
/*     */ public final class Util
/*     */ {
/*  48 */   private static final Method PRIVATE_LOOKUP_IN = findMethod(MethodHandles.class, "privateLookupIn", new Class[] { Class.class, MethodHandles.Lookup.class });
/*  49 */   private static final Method DESCRIPTOR_STRING = findMethod(Class.class, "descriptorString", new Class[0]);
/*     */   
/*     */   public static boolean mojangMapped() {
/*  52 */     return classExists("net.minecraft.server.level.ServerPlayer");
/*     */   }
/*     */   
/*     */   public static boolean classExists(String className) {
/*     */     try {
/*  57 */       Class.forName(className);
/*  58 */       return true;
/*  59 */     } catch (ClassNotFoundException ex) {
/*  60 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E extends Throwable> E sneakyThrow(Throwable ex) throws E {
/*  66 */     throw (E)ex;
/*     */   }
/*     */   
/*     */   public static <T> T sneakyThrows(ThrowingSupplier<T> supplier) {
/*     */     try {
/*  71 */       return supplier.get();
/*  72 */     } catch (Throwable ex) {
/*  73 */       throw (RuntimeException)sneakyThrow(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSynthetic(int modifiers) {
/*  83 */     return ((modifiers & 0x1000) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> findProxiedClass(Class<?> proxyInterface, UnaryOperator<String> classMapper) {
/*  90 */     if (!proxyInterface.isInterface()) {
/*  91 */       throw new IllegalArgumentException(proxyInterface.getTypeName() + " is not an interface annotated with @Proxies.");
/*     */     }
/*     */     
/*  94 */     Proxies proxies = proxyInterface.<Proxies>getDeclaredAnnotation(Proxies.class);
/*  95 */     if (proxies == null) {
/*  96 */       throw new IllegalArgumentException("interface " + proxyInterface.getTypeName() + " is not annotated with @Proxies.");
/*     */     }
/*     */     
/*  99 */     if (proxies.value() == Object.class && proxies.className().isEmpty()) {
/* 100 */       throw new IllegalArgumentException("@Proxies annotation must either have value() or className() set. Interface: " + proxyInterface.getTypeName());
/*     */     }
/*     */     
/* 103 */     if (proxies.value() != Object.class) {
/* 104 */       return proxies.value();
/*     */     }
/*     */     
/*     */     try {
/* 108 */       return Class.forName(classMapper.apply(proxies.className()));
/* 109 */     } catch (ClassNotFoundException ex) {
/* 110 */       throw new IllegalArgumentException("Could not find class for @Proxied className() " + proxies.className() + ".");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Method findMethod(Class<?> holder, String name, Class<?>... paramTypes) {
/*     */     try {
/* 116 */       return holder.getDeclaredMethod(name, paramTypes);
/* 117 */     } catch (ReflectiveOperationException ex) {
/* 118 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodHandle handleForDefaultMethod(Class<?> interfaceClass, Method method) throws Throwable {
/* 126 */     if (PRIVATE_LOOKUP_IN == null) {
/*     */       
/* 128 */       Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(new Class[] { Class.class });
/* 129 */       constructor.setAccessible(true);
/* 130 */       return ((MethodHandles.Lookup)constructor.newInstance(new Object[] { interfaceClass
/* 131 */           })).in(interfaceClass)
/* 132 */         .unreflectSpecial(method, interfaceClass);
/*     */     } 
/*     */ 
/*     */     
/* 136 */     return ((MethodHandles.Lookup)PRIVATE_LOOKUP_IN.invoke(null, new Object[] { interfaceClass, MethodHandles.lookup()
/* 137 */         })).findSpecial(interfaceClass, method
/*     */         
/* 139 */         .getName(), 
/* 140 */         MethodType.methodType(method.getReturnType(), method.getParameterTypes()), interfaceClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Class<?>> topDownInterfaceHierarchy(Class<?> cls) {
/* 146 */     if (!cls.isInterface()) {
/* 147 */       throw new IllegalStateException("Expected an interface, got " + cls);
/*     */     }
/* 149 */     Set<Class<?>> set = new LinkedHashSet<>();
/* 150 */     set.add(cls);
/* 151 */     interfaces(cls, set);
/* 152 */     List<Class<?>> list = new ArrayList<>(set);
/* 153 */     Collections.reverse(list);
/* 154 */     return Collections.unmodifiableList(list);
/*     */   }
/*     */   
/*     */   private static void interfaces(Class<?> cls, Collection<Class<?>> list) {
/* 158 */     for (Class<?> iface : cls.getInterfaces()) {
/* 159 */       list.add(iface);
/* 160 */       interfaces(iface, list);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String descriptorString(Class<?> clazz) {
/* 165 */     if (DESCRIPTOR_STRING != null) {
/*     */       
/*     */       try {
/* 168 */         return (String)DESCRIPTOR_STRING.invoke(clazz, new Object[0]);
/* 169 */       } catch (ReflectiveOperationException ex) {
/* 170 */         throw new RuntimeException("Failed to call Class#descriptorString", ex);
/*     */       } 
/*     */     }
/*     */     
/* 174 */     if (clazz == long.class)
/* 175 */       return "J"; 
/* 176 */     if (clazz == int.class)
/* 177 */       return "I"; 
/* 178 */     if (clazz == char.class)
/* 179 */       return "C"; 
/* 180 */     if (clazz == short.class)
/* 181 */       return "S"; 
/* 182 */     if (clazz == byte.class)
/* 183 */       return "B"; 
/* 184 */     if (clazz == double.class)
/* 185 */       return "D"; 
/* 186 */     if (clazz == float.class)
/* 187 */       return "F"; 
/* 188 */     if (clazz == boolean.class)
/* 189 */       return "Z"; 
/* 190 */     if (clazz == void.class) {
/* 191 */       return "V";
/*     */     }
/*     */     
/* 194 */     if (clazz.isArray()) {
/* 195 */       return "[" + descriptorString(clazz.getComponentType());
/*     */     }
/*     */     
/* 198 */     return 'L' + clazz.getName().replace('.', '/') + ';';
/*     */   }
/*     */   
/*     */   public static String firstLine(InputStream mappings) {
/*     */     try {
/* 203 */       mappings.mark(1024);
/* 204 */       BufferedReader reader = new BufferedReader(new InputStreamReader(mappings, StandardCharsets.UTF_8));
/* 205 */       String line = reader.readLine();
/* 206 */       mappings.reset();
/* 207 */       return line;
/* 208 */     } catch (IOException e) {
/* 209 */       throw new UncheckedIOException("Failed to read first line of input stream", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface ThrowingSupplier<T> {
/*     */     T get() throws Throwable;
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\reflectionremapper\interna\\util\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */