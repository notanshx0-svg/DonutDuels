/*     */ package org.ms.donutduels.foliascheduler.reflectionremapper;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import org.checkerframework.checker.nullness.qual.NonNull;
/*     */ import org.checkerframework.framework.qual.DefaultQualifier;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.tree.MappingTree;
/*     */ import org.ms.donutduels.foliascheduler.reflectionremapper.internal.util.StringPool;
/*     */ import org.ms.donutduels.foliascheduler.reflectionremapper.internal.util.Util;
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
/*     */ final class ReflectionRemapperImpl
/*     */   implements ReflectionRemapper
/*     */ {
/*     */   private final Map<String, ClassMapping> mappingsByObf;
/*     */   private final Map<String, ClassMapping> mappingsByDeobf;
/*     */   
/*     */   private ReflectionRemapperImpl(Set<ClassMapping> mappings) {
/*  42 */     this.mappingsByObf = Collections.unmodifiableMap((Map<? extends String, ? extends ClassMapping>)mappings
/*  43 */         .stream().collect(Collectors.toMap(ClassMapping::obfName, Function.identity())));
/*     */     
/*  45 */     this.mappingsByDeobf = Collections.unmodifiableMap((Map<? extends String, ? extends ClassMapping>)mappings
/*  46 */         .stream().collect(Collectors.toMap(ClassMapping::deobfName, Function.identity())));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String remapClassName(String className) {
/*  52 */     ClassMapping map = this.mappingsByDeobf.get(className);
/*  53 */     if (map == null) {
/*  54 */       return className;
/*     */     }
/*  56 */     return map.obfName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String remapFieldName(Class<?> holdingClass, String fieldName) {
/*  61 */     ClassMapping clsMap = this.mappingsByObf.get(holdingClass.getName());
/*  62 */     if (clsMap == null) {
/*  63 */       return fieldName;
/*     */     }
/*  65 */     return clsMap.fieldsDeobfToObf().getOrDefault(fieldName, fieldName);
/*     */   }
/*     */ 
/*     */   
/*     */   public String remapMethodName(Class<?> holdingClass, String methodName, Class<?>... paramTypes) {
/*  70 */     ClassMapping clsMap = this.mappingsByObf.get(holdingClass.getName());
/*  71 */     if (clsMap == null) {
/*  72 */       return methodName;
/*     */     }
/*  74 */     return clsMap.methods().getOrDefault(methodKey(methodName, paramTypes), methodName);
/*     */   }
/*     */   
/*     */   private static String methodKey(String deobfName, Class<?>... paramTypes) {
/*  78 */     return deobfName + paramsDescriptor(paramTypes);
/*     */   }
/*     */   
/*     */   private static String methodKey(String deobfName, String obfMethodDesc) {
/*  82 */     return deobfName + paramsDescFromMethodDesc(obfMethodDesc);
/*     */   }
/*     */   
/*     */   private static String paramsDescriptor(Class<?>... params) {
/*  86 */     StringBuilder builder = new StringBuilder();
/*  87 */     for (Class<?> param : params) {
/*  88 */       builder.append(Util.descriptorString(param));
/*     */     }
/*  90 */     return builder.toString();
/*     */   }
/*     */   
/*     */   private static String paramsDescFromMethodDesc(String methodDescriptor) {
/*  94 */     String ret = methodDescriptor.substring(1);
/*  95 */     ret = ret.substring(0, ret.indexOf(")"));
/*  96 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ReflectionRemapperImpl fromMappingTree(MappingTree tree, String fromNamespace, String toNamespace) {
/* 104 */     StringPool pool = new StringPool();
/*     */     
/* 106 */     Set<ClassMapping> mappings = new HashSet<>();
/*     */     
/* 108 */     for (MappingTree.ClassMapping cls : tree.getClasses()) {
/* 109 */       Map<String, String> fields = new HashMap<>();
/* 110 */       for (MappingTree.FieldMapping field : cls.getFields()) {
/* 111 */         fields.put(pool
/* 112 */             .string(Objects.<String>requireNonNull(field.getName(fromNamespace))), pool
/* 113 */             .string(Objects.<String>requireNonNull(field.getName(toNamespace))));
/*     */       }
/*     */ 
/*     */       
/* 117 */       Map<String, String> methods = new HashMap<>();
/* 118 */       for (MappingTree.MethodMapping method : cls.getMethods()) {
/* 119 */         methods.put(pool
/* 120 */             .string(methodKey(Objects.<String>requireNonNull(method.getName(fromNamespace)), Objects.<String>requireNonNull(method.getDesc(toNamespace)))), pool
/* 121 */             .string(Objects.<String>requireNonNull(method.getName(toNamespace))));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 129 */       ClassMapping map = new ClassMapping(((String)Objects.<String>requireNonNull(cls.getName(toNamespace))).replace('/', '.'), ((String)Objects.<String>requireNonNull(cls.getName(fromNamespace))).replace('/', '.'), Collections.unmodifiableMap(fields), Collections.unmodifiableMap(methods));
/*     */ 
/*     */       
/* 132 */       mappings.add(map);
/*     */     } 
/*     */     
/* 135 */     return new ReflectionRemapperImpl(mappings);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ClassMapping
/*     */   {
/*     */     private final String obfName;
/*     */     
/*     */     private final String deobfName;
/*     */     
/*     */     private final Map<String, String> fieldsDeobfToObf;
/*     */     
/*     */     private final Map<String, String> methods;
/*     */     
/*     */     private ClassMapping(String obfName, String deobfName, Map<String, String> fieldsDeobfToObf, Map<String, String> methods) {
/* 150 */       this.obfName = obfName;
/* 151 */       this.deobfName = deobfName;
/* 152 */       this.fieldsDeobfToObf = fieldsDeobfToObf;
/* 153 */       this.methods = methods;
/*     */     }
/*     */     
/*     */     public String obfName() {
/* 157 */       return this.obfName;
/*     */     }
/*     */     
/*     */     public String deobfName() {
/* 161 */       return this.deobfName;
/*     */     }
/*     */     
/*     */     public Map<String, String> fieldsDeobfToObf() {
/* 165 */       return this.fieldsDeobfToObf;
/*     */     }
/*     */     
/*     */     public Map<String, String> methods() {
/* 169 */       return this.methods;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 174 */       if (obj == this) {
/* 175 */         return true;
/*     */       }
/* 177 */       if (obj == null || obj.getClass() != getClass()) {
/* 178 */         return false;
/*     */       }
/* 180 */       ClassMapping that = (ClassMapping)obj;
/* 181 */       return (Objects.equals(this.obfName, that.obfName) && 
/* 182 */         Objects.equals(this.deobfName, that.deobfName) && 
/* 183 */         Objects.equals(this.fieldsDeobfToObf, that.fieldsDeobfToObf) && 
/* 184 */         Objects.equals(this.methods, that.methods));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 189 */       return Objects.hash(new Object[] { this.obfName, this.deobfName, this.fieldsDeobfToObf, this.methods });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 194 */       return "ClassMapping[obfName=" + this.obfName + ", deobfName=" + this.deobfName + ", fieldsDeobfToObf=" + this.fieldsDeobfToObf + ", methods=" + this.methods + ']';
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\reflectionremapper\ReflectionRemapperImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */