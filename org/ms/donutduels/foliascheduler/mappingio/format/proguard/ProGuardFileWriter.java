/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.proguard;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappedElementKind;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingWriter;
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
/*     */ public final class ProGuardFileWriter
/*     */   implements MappingWriter
/*     */ {
/*     */   private final Writer writer;
/*     */   private final String dstNamespaceString;
/*  37 */   private int dstNamespace = -1;
/*     */   
/*     */   private String clsSrcName;
/*     */   
/*     */   private String memberSrcName;
/*     */   
/*     */   private String memberSrcDesc;
/*     */   
/*     */   private String dstName;
/*     */   
/*     */   private boolean classContentVisitPending;
/*     */ 
/*     */   
/*     */   public ProGuardFileWriter(Writer writer) {
/*  51 */     this(writer, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProGuardFileWriter(Writer writer, int dstNamespace) {
/*  61 */     this.writer = Objects.<Writer>requireNonNull(writer, "writer cannot be null");
/*  62 */     this.dstNamespace = dstNamespace;
/*  63 */     this.dstNamespaceString = null;
/*     */     
/*  65 */     if (dstNamespace < 0) {
/*  66 */       throw new IllegalArgumentException("Namespace must be non-negative, found " + dstNamespace);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProGuardFileWriter(Writer writer, String dstNamespace) {
/*  77 */     this.writer = Objects.<Writer>requireNonNull(writer, "writer cannot be null");
/*  78 */     this.dstNamespaceString = Objects.<String>requireNonNull(dstNamespace, "namespace cannot be null");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  86 */     this.writer.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {
/*  91 */     if (this.dstNamespaceString != null) {
/*  92 */       this.dstNamespace = dstNamespaces.indexOf(this.dstNamespaceString);
/*     */       
/*  94 */       if (this.dstNamespace == -1) {
/*  95 */         throw new RuntimeException("Invalid destination namespace '" + this.dstNamespaceString + "' not in [" + String.join(", ", dstNamespaces) + ']');
/*     */       }
/*     */     } 
/*     */     
/*  99 */     if (this.dstNamespace >= dstNamespaces.size()) {
/* 100 */       throw new IndexOutOfBoundsException("Namespace " + this.dstNamespace + " doesn't exist in [" + String.join(", ", dstNamespaces) + ']');
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/* 106 */     this.clsSrcName = srcName;
/*     */     
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/* 113 */     this.memberSrcName = srcName;
/* 114 */     this.memberSrcDesc = srcDesc;
/*     */     
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/* 121 */     this.memberSrcName = srcName;
/* 122 */     this.memberSrcDesc = srcDesc;
/*     */     
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) throws IOException {
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) throws IOException {
/* 139 */     if (this.dstNamespace != namespace) {
/*     */       return;
/*     */     }
/*     */     
/* 143 */     this.dstName = name;
/*     */   }
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/*     */     List<String> argTypes;
/*     */     int i;
/* 148 */     if (targetKind == MappedElementKind.CLASS) {
/* 149 */       if (this.dstName == null) {
/* 150 */         this.classContentVisitPending = true;
/* 151 */         return true;
/*     */       } 
/*     */     } else {
/* 154 */       if (this.dstName == null)
/* 155 */         return false; 
/* 156 */       if (this.classContentVisitPending) {
/* 157 */         String memberDstName = this.dstName;
/* 158 */         this.dstName = this.clsSrcName;
/* 159 */         visitElementContent(MappedElementKind.CLASS);
/* 160 */         this.classContentVisitPending = false;
/* 161 */         this.dstName = memberDstName;
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     switch (targetKind) {
/*     */       case CLASS:
/* 167 */         this.writer.write(toJavaClassName(this.clsSrcName));
/* 168 */         this.dstName = toJavaClassName(this.dstName) + ":";
/*     */         break;
/*     */       case FIELD:
/* 171 */         writeIndent();
/* 172 */         this.writer.write(toJavaType(this.memberSrcDesc));
/* 173 */         this.writer.write(32);
/* 174 */         this.writer.write(this.memberSrcName);
/*     */         break;
/*     */       case METHOD:
/* 177 */         writeIndent();
/* 178 */         this.writer.write(toJavaType(this.memberSrcDesc.substring(this.memberSrcDesc.indexOf(')', 1) + 1)));
/* 179 */         this.writer.write(32);
/* 180 */         this.writer.write(this.memberSrcName);
/* 181 */         this.writer.write(40);
/* 182 */         argTypes = extractArgumentTypes(this.memberSrcDesc);
/*     */         
/* 184 */         for (i = 0; i < argTypes.size(); i++) {
/* 185 */           if (i > 0) {
/* 186 */             this.writer.write(44);
/*     */           }
/*     */           
/* 189 */           this.writer.write(argTypes.get(i));
/*     */         } 
/*     */         
/* 192 */         this.writer.write(41);
/*     */         break;
/*     */       default:
/* 195 */         throw new IllegalStateException("unexpected invocation for " + targetKind);
/*     */     } 
/*     */     
/* 198 */     writeArrow();
/* 199 */     this.writer.write(this.dstName);
/* 200 */     this.writer.write(10);
/*     */     
/* 202 */     this.dstName = null;
/* 203 */     return (targetKind == MappedElementKind.CLASS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {}
/*     */ 
/*     */   
/*     */   private void writeArrow() throws IOException {
/* 212 */     this.writer.write(" -> ");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeIndent() throws IOException {
/* 217 */     this.writer.write("    ");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toJavaClassName(String name) {
/* 225 */     return name.replace('/', '.');
/*     */   }
/*     */   
/*     */   private static String toJavaType(String descriptor) {
/* 229 */     StringBuilder result = new StringBuilder();
/* 230 */     int arrayLevel = 0;
/*     */     
/* 232 */     for (int i = 0; i < descriptor.length(); i++) {
/* 233 */       switch (descriptor.charAt(i)) { case '[':
/* 234 */           arrayLevel++; break;
/* 235 */         case 'B': result.append("byte"); break;
/* 236 */         case 'S': result.append("short"); break;
/* 237 */         case 'I': result.append("int"); break;
/* 238 */         case 'J': result.append("long"); break;
/* 239 */         case 'F': result.append("float"); break;
/* 240 */         case 'D': result.append("double"); break;
/* 241 */         case 'C': result.append("char"); break;
/* 242 */         case 'Z': result.append("boolean"); break;
/* 243 */         case 'V': result.append("void"); break;
/*     */         case 'L':
/* 245 */           while (i + 1 < descriptor.length()) {
/* 246 */             char c = descriptor.charAt(++i);
/*     */             
/* 248 */             if (c == '/') {
/* 249 */               result.append('.'); continue;
/* 250 */             }  if (c == ';') {
/*     */               break;
/*     */             }
/* 253 */             result.append(c);
/*     */           } 
/*     */           break;
/*     */         
/*     */         default:
/* 258 */           throw new IllegalArgumentException("Unknown character in descriptor: " + descriptor.charAt(i)); }
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 263 */     while (arrayLevel > 0) {
/* 264 */       result.append("[]");
/* 265 */       arrayLevel--;
/*     */     } 
/*     */     
/* 268 */     return result.toString();
/*     */   }
/*     */   
/*     */   private List<String> extractArgumentTypes(String desc) {
/* 272 */     List<String> argTypes = new ArrayList<>();
/* 273 */     int index = 1;
/*     */     
/* 275 */     while (desc.charAt(index) != ')') {
/* 276 */       int start = index;
/*     */       
/* 278 */       while (desc.charAt(index) == '[') {
/* 279 */         index++;
/*     */       }
/*     */       
/* 282 */       if (desc.charAt(index) == 'L') {
/* 283 */         index = desc.indexOf(';', index) + 1;
/*     */       } else {
/* 285 */         index++;
/*     */       } 
/*     */       
/* 288 */       argTypes.add(toJavaType(desc.substring(start, index)));
/*     */     } 
/*     */     
/* 291 */     return argTypes;
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\proguard\ProGuardFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */