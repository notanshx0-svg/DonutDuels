/*     */ package org.ms.donutduels.foliascheduler.reflectionremapper.proxy;
/*     */ 
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Parameter;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.UnaryOperator;
/*     */ import org.ms.donutduels.foliascheduler.reflectionremapper.ReflectionRemapper;
/*     */ import org.ms.donutduels.foliascheduler.reflectionremapper.internal.util.Util;
/*     */ import org.ms.donutduels.foliascheduler.reflectionremapper.proxy.annotation.ConstructorInvoker;
/*     */ import org.ms.donutduels.foliascheduler.reflectionremapper.proxy.annotation.FieldGetter;
/*     */ import org.ms.donutduels.foliascheduler.reflectionremapper.proxy.annotation.FieldSetter;
/*     */ import org.ms.donutduels.foliascheduler.reflectionremapper.proxy.annotation.MethodName;
/*     */ import org.ms.donutduels.foliascheduler.reflectionremapper.proxy.annotation.Static;
/*     */ import org.ms.donutduels.foliascheduler.reflectionremapper.proxy.annotation.Type;
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
/*     */ final class ReflectionProxyInvocationHandler<I>
/*     */   implements InvocationHandler
/*     */ {
/*  47 */   private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
/*  48 */   private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
/*     */   private final Class<I> interfaceClass;
/*  50 */   private final Map<Method, MethodHandle> methods = new HashMap<>();
/*  51 */   private final Map<Method, MethodHandle> getters = new HashMap<>();
/*  52 */   private final Map<Method, MethodHandle> setters = new HashMap<>();
/*  53 */   private final Map<Method, MethodHandle> staticGetters = new HashMap<>();
/*  54 */   private final Map<Method, MethodHandle> staticSetters = new HashMap<>();
/*  55 */   private final Map<Method, MethodHandle> defaultMethods = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ReflectionProxyInvocationHandler(Class<I> interfaceClass, ReflectionRemapper reflectionRemapper) {
/*  61 */     this.interfaceClass = interfaceClass;
/*  62 */     scanInterface(reflectionRemapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/*  71 */     if (isEqualsMethod(method))
/*  72 */       return Boolean.valueOf((proxy == args[0])); 
/*  73 */     if (isHashCodeMethod(method))
/*  74 */       return Integer.valueOf(0); 
/*  75 */     if (isToStringMethod(method)) {
/*  76 */       return String.format("ReflectionProxy[interface=%s, implementation=%s]", new Object[] { this.interfaceClass.getTypeName(), proxy.getClass().getTypeName() });
/*     */     }
/*     */     
/*  79 */     if (args == null) {
/*  80 */       args = EMPTY_OBJECT_ARRAY;
/*     */     }
/*     */     
/*  83 */     if (method.isDefault()) {
/*  84 */       return handleDefaultMethod(proxy, method, args);
/*     */     }
/*     */ 
/*     */     
/*  88 */     MethodHandle methodHandle = this.methods.get(method);
/*  89 */     if (methodHandle != null) {
/*  90 */       if (args.length == 0) {
/*  91 */         return methodHandle.invokeExact();
/*     */       }
/*  93 */       return methodHandle.invokeExact(args);
/*     */     } 
/*     */ 
/*     */     
/*  97 */     MethodHandle getter = this.getters.get(method);
/*  98 */     if (getter != null) {
/*  99 */       return getter.invokeExact(args[0]);
/*     */     }
/*     */ 
/*     */     
/* 103 */     MethodHandle setter = this.setters.get(method);
/* 104 */     if (setter != null) {
/* 105 */       return setter.invokeExact(args[0], args[1]);
/*     */     }
/*     */ 
/*     */     
/* 109 */     MethodHandle staticGetter = this.staticGetters.get(method);
/* 110 */     if (staticGetter != null) {
/* 111 */       return staticGetter.invokeExact();
/*     */     }
/*     */ 
/*     */     
/* 115 */     MethodHandle staticSetter = this.staticSetters.get(method);
/* 116 */     if (staticSetter != null) {
/* 117 */       return staticSetter.invokeExact(args[0]);
/*     */     }
/*     */ 
/*     */     
/* 121 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object handleDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
/* 129 */     MethodHandle handle = this.defaultMethods.computeIfAbsent(method, m -> adapt(((MethodHandle)Util.sneakyThrows(())).bindTo(proxy)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     if (args.length == 0) {
/* 135 */       return handle.invokeExact();
/*     */     }
/* 137 */     return handle.invokeExact(args);
/*     */   }
/*     */ 
/*     */   
/*     */   private void scanInterface(ReflectionRemapper reflectionRemapper) {
/* 142 */     Class<?> prevProxy = null;
/* 143 */     Class<?> prevProxied = null;
/*     */     
/* 145 */     for (Class<?> cls : (Iterable<Class<?>>)Util.topDownInterfaceHierarchy(this.interfaceClass)) {
/* 146 */       Objects.requireNonNull(reflectionRemapper); Class<?> proxied = Util.findProxiedClass(cls, reflectionRemapper::remapClassName);
/*     */       
/* 148 */       if (prevProxied != null && !prevProxied.isAssignableFrom(proxied)) {
/* 149 */         throw new IllegalArgumentException("Reflection proxy interface " + cls
/* 150 */             .getName() + " proxies " + proxied.getName() + ", and extends from reflection proxy interface " + prevProxy
/* 151 */             .getName() + " which proxies " + prevProxied.getName() + ", but the proxied types are not compatible.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 158 */       Objects.requireNonNull(reflectionRemapper); scanInterface(cls, proxied, reflectionRemapper::remapClassOrArrayName, fieldName -> reflectionRemapper.remapFieldName(proxied, fieldName), (methodName, parameters) -> reflectionRemapper.remapMethodName(proxied, methodName, parameters));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 163 */       prevProxied = proxied;
/* 164 */       prevProxy = cls;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void scanInterface(Class<?> interfaceClass, Class<?> proxiedClass, UnaryOperator<String> classMapper, UnaryOperator<String> fieldMapper, BiFunction<String, Class<?>[], String> methodMapper) {
/* 175 */     for (Method method : interfaceClass.getDeclaredMethods()) {
/* 176 */       if (!isEqualsMethod(method) && !isHashCodeMethod(method) && !isToStringMethod(method) && !Util.isSynthetic(method.getModifiers()))
/*     */       {
/* 178 */         if (!method.isDefault()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 184 */           boolean constructorInvoker = (method.getDeclaredAnnotation(ConstructorInvoker.class) != null);
/* 185 */           if (constructorInvoker) {
/* 186 */             this.methods.put(method, 
/*     */                 
/* 188 */                 adapt((MethodHandle)Util.sneakyThrows(() -> LOOKUP.unreflectConstructor(findProxiedConstructor(proxiedClass, method, classMapper)))));
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 193 */             FieldGetter getterAnnotation = method.<FieldGetter>getDeclaredAnnotation(FieldGetter.class);
/* 194 */             FieldSetter setterAnnotation = method.<FieldSetter>getDeclaredAnnotation(FieldSetter.class);
/* 195 */             if (getterAnnotation != null && setterAnnotation != null) {
/* 196 */               throw new IllegalArgumentException("Method " + method.getName() + " in " + interfaceClass.getTypeName() + " is annotated with @FieldGetter and @FieldSetter, don't know which to use.");
/*     */             }
/*     */             
/* 199 */             boolean hasStaticAnnotation = (method.getDeclaredAnnotation(Static.class) != null);
/*     */             
/* 201 */             if (getterAnnotation != null) {
/* 202 */               MethodHandle handle = (MethodHandle)Util.sneakyThrows(() -> LOOKUP.unreflectGetter(findProxiedField(proxiedClass, getterAnnotation.value(), fieldMapper)));
/* 203 */               if (hasStaticAnnotation) {
/* 204 */                 checkParameterCount(method, interfaceClass, 0, "Static @FieldGetters should have no parameters.");
/* 205 */                 this.staticGetters.put(method, handle.asType(MethodType.methodType(Object.class)));
/*     */               } else {
/* 207 */                 checkParameterCount(method, interfaceClass, 1, "Non-static @FieldGetters should have one parameter.");
/* 208 */                 this.getters.put(method, handle.asType(MethodType.methodType(Object.class, Object.class)));
/*     */               
/*     */               }
/*     */             
/*     */             }
/* 213 */             else if (setterAnnotation != null) {
/* 214 */               MethodHandle handle = (MethodHandle)Util.sneakyThrows(() -> LOOKUP.unreflectSetter(findProxiedField(proxiedClass, setterAnnotation.value(), fieldMapper)));
/* 215 */               if (hasStaticAnnotation) {
/* 216 */                 checkParameterCount(method, interfaceClass, 1, "Static @FieldSetters should have one parameter.");
/* 217 */                 this.staticSetters.put(method, handle.asType(MethodType.methodType(Object.class, Object.class)));
/*     */               } else {
/* 219 */                 checkParameterCount(method, interfaceClass, 2, "Non-static @FieldSetters should have two parameters.");
/* 220 */                 this.setters.put(method, handle.asType(MethodType.methodType(Object.class, Object.class, new Class[] { Object.class })));
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 225 */               if (!hasStaticAnnotation && method.getParameterCount() < 1) {
/* 226 */                 throw new IllegalArgumentException("Non-static method invokers should have at least one parameter. Method " + method.getName() + " in " + interfaceClass.getTypeName() + " has " + method.getParameterCount());
/*     */               }
/*     */               
/* 229 */               this.methods.put(method, 
/*     */                   
/* 231 */                   adapt((MethodHandle)Util.sneakyThrows(() -> LOOKUP.unreflect(findProxiedMethod(proxiedClass, method, classMapper, methodMapper)))));
/*     */             } 
/*     */           } 
/*     */         }  } 
/*     */     } 
/*     */   } private static MethodHandle adapt(MethodHandle handle) {
/* 237 */     if (handle.type().parameterCount() == 0) {
/* 238 */       return handle.asType(MethodType.methodType(Object.class));
/*     */     }
/* 240 */     return handle.asSpreader(Object[].class, handle.type().parameterCount())
/* 241 */       .asType(MethodType.methodType(Object.class, Object[].class));
/*     */   }
/*     */   
/*     */   private static void checkParameterCount(Method method, Class<?> holder, int expected, String message) {
/* 245 */     if (method.getParameterCount() != expected) {
/* 246 */       throw new IllegalArgumentException(
/* 247 */           String.format("Unexpected amount of parameters for method %s in %s, got %d while expecting %d. %s", new Object[] { method.getName(), holder.getTypeName(), Integer.valueOf(method.getParameterCount()), Integer.valueOf(expected), message }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isToStringMethod(Method method) {
/* 253 */     return (method.getName().equals("toString") && method
/* 254 */       .getParameterCount() == 0 && method
/* 255 */       .getReturnType() == String.class);
/*     */   }
/*     */   
/*     */   private static boolean isHashCodeMethod(Method method) {
/* 259 */     return (method.getName().equals("hashCode") && method
/* 260 */       .getParameterCount() == 0 && method
/* 261 */       .getReturnType() == int.class);
/*     */   }
/*     */   
/*     */   private static boolean isEqualsMethod(Method method) {
/* 265 */     return (method.getName().equals("equals") && method
/* 266 */       .getParameterCount() == 1 && method
/* 267 */       .getReturnType() == boolean.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Field findProxiedField(Class<?> proxiedClass, String fieldName, UnaryOperator<String> fieldMapper) {
/*     */     Field field;
/*     */     try {
/* 277 */       field = proxiedClass.getDeclaredField(fieldMapper.apply(fieldName));
/* 278 */     } catch (NoSuchFieldException e) {
/* 279 */       throw new IllegalArgumentException("Could not find field '" + fieldName + "' in " + proxiedClass.getTypeName(), e);
/*     */     } 
/*     */     try {
/* 282 */       field.setAccessible(true);
/* 283 */     } catch (Exception ex) {
/* 284 */       throw new IllegalStateException("Could not set access for field '" + fieldName + "' in " + proxiedClass.getTypeName(), ex);
/*     */     } 
/* 286 */     return field;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Constructor<?> findProxiedConstructor(Class<?> proxiedClass, Method method, UnaryOperator<String> classMapper) {
/*     */     Constructor<?> constructor;
/* 296 */     Class<?>[] actualParams = (Class[])Arrays.<Parameter>stream(method.getParameters()).map(p -> resolveParameterTypeClass(p, classMapper)).toArray(x$0 -> new Class[x$0]);
/*     */ 
/*     */     
/*     */     try {
/* 300 */       constructor = proxiedClass.getDeclaredConstructor(actualParams);
/* 301 */     } catch (NoSuchMethodException ex) {
/* 302 */       throw new IllegalArgumentException("Could not find constructor of " + proxiedClass.getTypeName() + " with parameter types " + Arrays.toString(method.getParameterTypes()), ex);
/*     */     } 
/*     */     try {
/* 305 */       constructor.setAccessible(true);
/* 306 */     } catch (Exception ex) {
/* 307 */       throw new IllegalStateException("Could not set access for proxy method target constructor of " + proxiedClass.getTypeName() + " with parameter types " + Arrays.toString(method.getParameterTypes()), ex);
/*     */     } 
/* 309 */     return constructor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Method findProxiedMethod(Class<?> proxiedClass, Method method, UnaryOperator<String> classMapper, BiFunction<String, Class<?>[], String> methodMapper) {
/*     */     Class<?>[] actualParams;
/*     */     Method proxiedMethod;
/* 318 */     boolean hasStaticAnnotation = (method.getDeclaredAnnotation(Static.class) != null);
/*     */ 
/*     */     
/* 321 */     if (hasStaticAnnotation) {
/*     */ 
/*     */       
/* 324 */       actualParams = (Class[])Arrays.<Parameter>stream(method.getParameters()).map(p -> resolveParameterTypeClass(p, classMapper)).toArray(x$0 -> new Class[x$0]);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 329 */       actualParams = (Class[])Arrays.<Parameter>stream(method.getParameters()).skip(1L).map(p -> resolveParameterTypeClass(p, classMapper)).toArray(x$0 -> new Class[x$0]);
/*     */     } 
/*     */     
/* 332 */     MethodName methodAnnotation = method.<MethodName>getDeclaredAnnotation(MethodName.class);
/* 333 */     String methodName = (methodAnnotation == null) ? method.getName() : methodAnnotation.value();
/*     */     
/*     */     try {
/* 336 */       proxiedMethod = proxiedClass.getDeclaredMethod(methodMapper.apply(methodName, actualParams), actualParams);
/* 337 */     } catch (NoSuchMethodException e) {
/* 338 */       throw new IllegalArgumentException("Could not find proxy method target method: " + proxiedClass.getTypeName() + " " + methodName);
/*     */     } 
/*     */     try {
/* 341 */       proxiedMethod.setAccessible(true);
/* 342 */     } catch (Exception ex) {
/* 343 */       throw new IllegalStateException("Could not set access for proxy method target method: " + proxiedClass.getTypeName() + " " + methodName, ex);
/*     */     } 
/*     */     
/* 346 */     return proxiedMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> resolveParameterTypeClass(Parameter parameter, UnaryOperator<String> classMapper) {
/*     */     Class<?> namedClass;
/* 353 */     Type typeAnnotation = parameter.<Type>getDeclaredAnnotation(Type.class);
/* 354 */     if (typeAnnotation == null) {
/* 355 */       return parameter.getType();
/*     */     }
/*     */     
/* 358 */     if (typeAnnotation.value() == Object.class && typeAnnotation.className().isEmpty()) {
/* 359 */       throw new IllegalArgumentException("@Type annotation must either have value() or className() set.");
/*     */     }
/*     */     
/* 362 */     if (typeAnnotation.value() != Object.class) {
/* 363 */       return Util.findProxiedClass(typeAnnotation.value(), classMapper);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 368 */       namedClass = Class.forName(classMapper.apply(typeAnnotation.className()));
/* 369 */     } catch (ClassNotFoundException e) {
/* 370 */       throw new IllegalArgumentException("Class " + typeAnnotation.className() + " specified in @Type annotation not found.", e);
/*     */     } 
/*     */     
/* 373 */     return namedClass;
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\reflectionremapper\proxy\ReflectionProxyInvocationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */