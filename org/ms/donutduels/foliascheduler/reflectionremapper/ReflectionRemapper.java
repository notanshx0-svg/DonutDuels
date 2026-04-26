/*     */ package org.ms.donutduels.foliascheduler.reflectionremapper;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Objects;
/*     */ import java.util.function.UnaryOperator;
/*     */ import org.checkerframework.checker.nullness.qual.NonNull;
/*     */ import org.checkerframework.framework.qual.DefaultQualifier;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingReader;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingVisitor;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.tree.MappingTree;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.tree.MemoryMappingTree;
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
/*     */ public interface ReflectionRemapper
/*     */ {
/*     */   String remapClassName(String paramString);
/*     */   
/*     */   String remapFieldName(Class<?> paramClass, String paramString);
/*     */   
/*     */   String remapMethodName(Class<?> paramClass, String paramString, Class<?>... paramVarArgs);
/*     */   
/*     */   default String remapClassOrArrayName(String name) {
/*  86 */     Objects.requireNonNull(name, "name");
/*  87 */     if (name.isEmpty()) {
/*  88 */       return name;
/*     */     }
/*     */ 
/*     */     
/*  92 */     if (name.charAt(0) == '[') {
/*  93 */       int last = name.lastIndexOf('[');
/*     */ 
/*     */       
/*     */       try {
/*  97 */         if (name.charAt(last + 1) == 'L') {
/*  98 */           String cls = name.substring(last + 2, name.length() - 1);
/*  99 */           return name.substring(0, last + 2) + remapClassName(cls) + ';';
/*     */         } 
/* 101 */       } catch (IndexOutOfBoundsException ex) {
/*     */         
/* 103 */         return name;
/*     */       } 
/*     */ 
/*     */       
/* 107 */       return name;
/*     */     } 
/*     */     
/* 110 */     return remapClassName(name);
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
/*     */   default ReflectionRemapper withClassNamePreprocessor(UnaryOperator<String> preprocessor) {
/* 123 */     return new ClassNamePreprocessingReflectionRemapper(this, preprocessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ReflectionRemapper noop() {
/* 133 */     return NoopReflectionRemapper.INSTANCE;
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
/*     */   static ReflectionRemapper forMappings(InputStream mappings, String fromNamespace, String toNamespace) {
/*     */     try {
/* 152 */       MemoryMappingTree tree = new MemoryMappingTree(true);
/* 153 */       tree.setSrcNamespace(fromNamespace);
/* 154 */       tree.setDstNamespaces(new ArrayList(Collections.singletonList(toNamespace)));
/*     */       
/* 156 */       MappingReader.read(new InputStreamReader(mappings, StandardCharsets.UTF_8), (MappingVisitor)tree);
/*     */       
/* 158 */       return ReflectionRemapperImpl.fromMappingTree((MappingTree)tree, fromNamespace, toNamespace);
/* 159 */     } catch (IOException ex) {
/* 160 */       throw new RuntimeException("Failed to read mappings.", ex);
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
/*     */   static ReflectionRemapper forMappings(Path mappings, String fromNamespace, String toNamespace) {
/*     */     
/* 177 */     try { InputStream stream = Files.newInputStream(mappings, new java.nio.file.OpenOption[0]); 
/* 178 */       try { ReflectionRemapper reflectionRemapper = forMappings(stream, fromNamespace, toNamespace);
/* 179 */         if (stream != null) stream.close();  return reflectionRemapper; } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 180 */     { throw new RuntimeException(e); }
/*     */   
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
/*     */   static ReflectionRemapper forPaperReobfMappings(Path mappings) {
/* 194 */     if (Util.mojangMapped()) {
/* 195 */       return noop();
/*     */     }
/*     */     
/* 198 */     try { InputStream inputStream = Files.newInputStream(mappings, new java.nio.file.OpenOption[0]); 
/* 199 */       try { ReflectionRemapper reflectionRemapper = forPaperReobfMappings(inputStream);
/* 200 */         if (inputStream != null) inputStream.close();  return reflectionRemapper; } catch (Throwable throwable) { if (inputStream != null) try { inputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 201 */     { throw new RuntimeException("Failed to read mappings.", e); }
/*     */   
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
/*     */   static ReflectionRemapper forPaperReobfMappings(InputStream mappings) {
/* 217 */     if (Util.mojangMapped()) {
/* 218 */       return noop();
/*     */     }
/*     */     
/* 221 */     if (Util.firstLine(mappings).contains("mojang+yarn")) {
/* 222 */       return forMappings(mappings, "mojang+yarn", "spigot");
/*     */     }
/* 224 */     return forMappings(mappings, "mojang", "spigot");
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
/*     */   static ReflectionRemapper forReobfMappingsInPaperJar() {
/*     */     Class<?> craftServerClass;
/* 237 */     if (Util.mojangMapped()) {
/* 238 */       return noop();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 245 */       Class<?> bukkitClass = Class.forName("org.bukkit.Bukkit");
/* 246 */       Method getServerMethod = bukkitClass.getDeclaredMethod("getServer", new Class[0]);
/* 247 */       craftServerClass = getServerMethod.invoke(null, new Object[0]).getClass();
/* 248 */     } catch (ReflectiveOperationException e) {
/* 249 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 252 */     try { InputStream reobfIn = craftServerClass.getClassLoader().getResourceAsStream("META-INF/mappings/reobf.tiny"); 
/* 253 */       try { if (reobfIn == null) {
/* 254 */           throw new IllegalStateException("Could not find mappings in expected location.");
/*     */         }
/* 256 */         ReflectionRemapper reflectionRemapper = forPaperReobfMappings(reobfIn);
/* 257 */         if (reobfIn != null) reobfIn.close();  return reflectionRemapper; } catch (Throwable throwable) { if (reobfIn != null) try { reobfIn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 258 */     { throw new RuntimeException(e); }
/*     */   
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\reflectionremapper\ReflectionRemapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */