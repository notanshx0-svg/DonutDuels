/*     */ package org.ms.donutduels.foliascheduler.util;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.function.Predicate;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.ms.donutduels.foliascheduler.reflectionremapper.ReflectionRemapper;
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
/*     */ public final class ReflectionUtil
/*     */ {
/*     */   @NotNull
/*     */   public static final Predicate<Member> IS_PUBLIC;
/*     */   
/*     */   static {
/*  31 */     IS_PUBLIC = (member -> Modifier.isPublic(member.getModifiers())); } @NotNull
/*  32 */   public static final Predicate<Member> IS_NOT_PUBLIC = IS_PUBLIC.negate();
/*  33 */   static { IS_PRIVATE = (member -> Modifier.isPrivate(member.getModifiers())); } @NotNull public static final Predicate<Member> IS_PRIVATE; @NotNull
/*  34 */   public static final Predicate<Member> IS_NOT_PRIVATE = IS_PRIVATE.negate(); @NotNull
/*  35 */   public static final Predicate<Member> IS_STATIC; static { IS_STATIC = (member -> Modifier.isStatic(member.getModifiers())); } @NotNull
/*  36 */   public static final Predicate<Member> IS_NOT_STATIC = IS_STATIC.negate(); @NotNull
/*  37 */   public static final Predicate<Member> IS_FINAL; static { IS_FINAL = (member -> Modifier.isFinal(member.getModifiers())); } @NotNull
/*  38 */   public static final Predicate<Member> IS_NOT_FINAL = IS_FINAL.negate();
/*     */   
/*     */   private ReflectionUtil() {
/*  41 */     throw new UnsupportedOperationException("This class cannot be instantiated");
/*     */   }
/*     */   @NotNull
/*     */   private static Field makeFieldAccessible(@NotNull Field field) {
/*  45 */     if (!field.isAccessible())
/*  46 */       field.setAccessible(true); 
/*  47 */     return field;
/*     */   }
/*     */   @NotNull
/*     */   private static Method makeMethodAccessible(@NotNull Method method) {
/*  51 */     if (!method.isAccessible())
/*  52 */       method.setAccessible(true); 
/*  53 */     return method;
/*     */   }
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
/*     */   @NotNull
/*     */   public static <T> Class<T> getClass(@NotNull String className) {
/*  72 */     if (ServerVersions.isPaper() && MinecraftVersions.TRAILS_AND_TAILS.get(5).isAtLeast()) {
/*  73 */       ReflectionRemapper remapper = ReflectionRemapper.forReobfMappingsInPaperJar();
/*  74 */       className = remapper.remapClassOrArrayName(className);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  79 */       return (Class)Class.forName(className);
/*  80 */     } catch (ClassNotFoundException e) {
/*  81 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   @NotNull
/*     */   public static <T> Class<T> getMinecraftClass(@NotNull String packageName, @NotNull String className) {
/* 103 */     if (MinecraftVersions.CAVES_AND_CLIFFS_1.isAtLeast()) {
/* 104 */       return getClass("net.minecraft." + packageName + "." + className);
/*     */     }
/*     */ 
/*     */     
/* 108 */     return getClass("net.minecraft.server." + MinecraftVersions.getCurrent() + "." + className);
/*     */   }
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
/*     */   @NotNull
/*     */   public static <T> Class<T> getCraftBukkitClass(@NotNull String classPath) {
/* 124 */     if (ServerVersions.isPaper() && MinecraftVersions.TRAILS_AND_TAILS.get(5).isAtLeast()) {
/* 125 */       return getClass("org.bukkit.craftbukkit." + classPath);
/*     */     }
/*     */     
/* 128 */     return getClass("org.bukkit.craftbukkit." + MinecraftVersions.getCurrent() + "." + classPath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static FieldAccessor getField(@NotNull Class<?> clazz, @NotNull String fieldName) {
/*     */     try {
/* 141 */       if (ServerVersions.isPaper() && MinecraftVersions.TRAILS_AND_TAILS.get(5).isAtLeast()) {
/* 142 */         ReflectionRemapper remapper = ReflectionRemapper.forReobfMappingsInPaperJar();
/* 143 */         fieldName = remapper.remapFieldName(clazz, fieldName);
/*     */       } 
/* 145 */       return new FieldAccessor(makeFieldAccessible(clazz.getDeclaredField(fieldName)));
/* 146 */     } catch (ReflectiveOperationException e) {
/* 147 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static FieldAccessor getField(@NotNull Class<?> clazz, @NotNull Class<?> fieldType) {
/* 161 */     return getField(clazz, fieldType, 0, null);
/*     */   }
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
/*     */   @NotNull
/*     */   public static FieldAccessor getField(@NotNull Class<?> clazz, @NotNull Class<?> fieldType, int index) {
/* 175 */     return getField(clazz, fieldType, index, null);
/*     */   }
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
/*     */   @NotNull
/*     */   public static FieldAccessor getField(@NotNull Class<?> clazz, @NotNull Class<?> fieldType, int index, @Nullable Predicate<? super Field> predicate) {
/* 193 */     for (Field field : clazz.getDeclaredFields()) {
/* 194 */       if (fieldType.isAssignableFrom(field.getType()))
/*     */       {
/* 196 */         if (predicate == null || predicate.test(field))
/*     */         {
/* 198 */           if (index-- <= 0)
/*     */           {
/*     */             
/* 201 */             return new FieldAccessor(makeFieldAccessible(field)); } 
/*     */         }
/*     */       }
/*     */     } 
/*     */     try {
/* 206 */       if (clazz.getSuperclass() != null)
/* 207 */         return getField(clazz.getSuperclass(), fieldType, index, predicate); 
/* 208 */     } catch (IllegalArgumentException ex) {
/*     */       
/* 210 */       if (!ex.getMessage().startsWith("No field of type ")) {
/* 211 */         throw ex;
/*     */       }
/*     */     } 
/* 214 */     throw new IllegalArgumentException("No field of type " + fieldType.getName() + " found in class " + clazz.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static MethodInvoker getMethod(@NotNull Class<?> clazz, @NotNull String methodName, Class<?>... parameterTypes) {
/*     */     try {
/* 228 */       if (ServerVersions.isPaper() && MinecraftVersions.TRAILS_AND_TAILS.get(5).isAtLeast()) {
/* 229 */         ReflectionRemapper remapper = ReflectionRemapper.forReobfMappingsInPaperJar();
/* 230 */         methodName = remapper.remapMethodName(clazz, methodName, new Class[0]);
/*     */       } 
/* 232 */       return new MethodInvoker(makeMethodAccessible(clazz.getDeclaredMethod(methodName, parameterTypes)));
/* 233 */     } catch (ReflectiveOperationException e) {
/* 234 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
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
/*     */   @NotNull
/*     */   public static MethodInvoker getMethod(@NotNull Class<?> clazz, @NotNull Class<?> returnType, Class<?>... parameterTypes) {
/* 249 */     return getMethod(clazz, returnType, 0, null, parameterTypes);
/*     */   }
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
/*     */   @NotNull
/*     */   public static MethodInvoker getMethod(@NotNull Class<?> clazz, @NotNull Class<?> returnType, int index, Class<?>... parameterTypes) {
/* 264 */     return getMethod(clazz, returnType, index, null, parameterTypes);
/*     */   }
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
/*     */   @NotNull
/*     */   public static MethodInvoker getMethod(@NotNull Class<?> clazz, @NotNull Class<?> returnType, int index, @Nullable Predicate<? super Method> predicate, Class<?>... parameterTypes) {
/* 283 */     for (Method method : clazz.getDeclaredMethods()) {
/* 284 */       if (returnType.isAssignableFrom(method.getReturnType()))
/*     */       {
/* 286 */         if (predicate == null || predicate.test(method))
/*     */         {
/* 288 */           if (parameterTypes.length == method.getParameterCount()) {
/*     */ 
/*     */ 
/*     */             
/* 292 */             boolean match = true;
/* 293 */             for (int i = 0; i < parameterTypes.length; i++) {
/* 294 */               if (!parameterTypes[i].isAssignableFrom(method.getParameterTypes()[i])) {
/* 295 */                 match = false;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 299 */             if (match)
/*     */             {
/* 301 */               if (index-- <= 0)
/*     */               {
/*     */                 
/* 304 */                 return new MethodInvoker(makeMethodAccessible(method)); }  } 
/*     */           }  } 
/*     */       }
/*     */     } 
/*     */     try {
/* 309 */       if (clazz.getSuperclass() != null)
/* 310 */         return getMethod(clazz.getSuperclass(), returnType, index, predicate, parameterTypes); 
/* 311 */     } catch (IllegalArgumentException ex) {
/*     */       
/* 313 */       if (!ex.getMessage().startsWith("No method with return type ")) {
/* 314 */         throw ex;
/*     */       }
/*     */     } 
/* 317 */     throw new IllegalArgumentException("No method with return type " + returnType.getName() + " found in class " + clazz.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static <T> ConstructorInvoker<T> getConstructor(@NotNull Class<T> clazz, Class<?>... parameterTypes) {
/*     */     try {
/* 330 */       Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes);
/* 331 */       return new ConstructorInvoker<>(constructor);
/* 332 */     } catch (ReflectiveOperationException e) {
/* 333 */       throw new WrappedReflectiveOperationException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliaschedule\\util\ReflectionUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */