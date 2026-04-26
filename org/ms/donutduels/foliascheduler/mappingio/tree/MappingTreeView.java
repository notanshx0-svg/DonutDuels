/*     */ package org.ms.donutduels.foliascheduler.mappingio.tree;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingVisitor;
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
/*     */ public interface MappingTreeView
/*     */ {
/*     */   public static final int SRC_NAMESPACE_ID = -1;
/*     */   public static final int MIN_NAMESPACE_ID = -1;
/*     */   public static final int NULL_NAMESPACE_ID = -2;
/*     */   
/*     */   static {
/*  33 */     if (null.$assertionsDisabled);
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
/*     */   default int getMaxNamespaceId() {
/*  50 */     return getDstNamespaces().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default int getMinNamespaceId() {
/*  57 */     return -1;
/*     */   }
/*     */   
/*     */   default int getNamespaceId(String namespace) {
/*  61 */     if (namespace.equals(getSrcNamespace())) {
/*  62 */       return -1;
/*     */     }
/*     */     
/*  65 */     int ret = getDstNamespaces().indexOf(namespace);
/*     */     
/*  67 */     return (ret >= 0) ? ret : -2;
/*     */   }
/*     */   
/*     */   default String getNamespaceName(int id) {
/*  71 */     if (id < 0) return getSrcNamespace();
/*     */     
/*  73 */     return getDstNamespaces().get(id);
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
/*     */   @Nullable
/*     */   default ClassMappingView getClass(String name, int namespace) {
/*  93 */     if (namespace < 0) return getClass(name);
/*     */     
/*  95 */     for (ClassMappingView cls : getClasses()) {
/*  96 */       if (name.equals(cls.getDstName(namespace))) return cls;
/*     */     
/*     */     } 
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default FieldMappingView getField(String srcClsName, String srcName, @Nullable String srcDesc) {
/* 107 */     ClassMappingView owner = getClass(srcClsName);
/* 108 */     return (owner != null) ? owner.getField(srcName, srcDesc) : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   default FieldMappingView getField(String clsName, String name, @Nullable String desc, int namespace) {
/* 113 */     ClassMappingView owner = getClass(clsName, namespace);
/* 114 */     return (owner != null) ? owner.getField(name, desc, namespace) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default MethodMappingView getMethod(String srcClsName, String srcName, @Nullable String srcDesc) {
/* 122 */     ClassMappingView owner = getClass(srcClsName);
/* 123 */     return (owner != null) ? owner.getMethod(srcName, srcDesc) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default MethodMappingView getMethod(String clsName, String name, @Nullable String desc, int namespace) {
/* 131 */     ClassMappingView owner = getClass(clsName, namespace);
/* 132 */     return (owner != null) ? owner.getMethod(name, desc, namespace) : null;
/*     */   }
/*     */   
/*     */   default void accept(MappingVisitor visitor) throws IOException {
/* 136 */     accept(visitor, VisitOrder.createByInputOrder());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default String mapClassName(String name, int namespace) {
/* 142 */     return mapClassName(name, -1, namespace);
/*     */   }
/*     */   
/*     */   default String mapClassName(String name, int srcNamespace, int dstNamespace) {
/* 146 */     if (!null.$assertionsDisabled && name.indexOf('.') >= 0) throw new AssertionError();
/*     */     
/* 148 */     if (srcNamespace == dstNamespace) return name;
/*     */     
/* 150 */     ClassMappingView cls = getClass(name, srcNamespace);
/* 151 */     if (cls == null) return name;
/*     */     
/* 153 */     String ret = cls.getName(dstNamespace);
/*     */     
/* 155 */     return (ret != null) ? ret : name;
/*     */   }
/*     */   
/*     */   default String mapDesc(CharSequence desc, int namespace) {
/* 159 */     return mapDesc(desc, 0, desc.length(), -1, namespace);
/*     */   }
/*     */   
/*     */   default String mapDesc(CharSequence desc, int srcNamespace, int dstNamespace) {
/* 163 */     return mapDesc(desc, 0, desc.length(), srcNamespace, dstNamespace);
/*     */   }
/*     */   
/*     */   default String mapDesc(CharSequence desc, int start, int end, int namespace) {
/* 167 */     return mapDesc(desc, start, end, -1, namespace);
/*     */   } @Nullable
/*     */   String getSrcNamespace(); List<String> getDstNamespaces(); List<? extends MetadataEntryView> getMetadata(); List<? extends MetadataEntryView> getMetadata(String paramString);
/*     */   default String mapDesc(CharSequence desc, int start, int end, int srcNamespace, int dstNamespace) {
/* 171 */     if (srcNamespace == dstNamespace) return desc.subSequence(start, end).toString();
/*     */     
/* 173 */     StringBuilder ret = null;
/* 174 */     int copyOffset = start;
/* 175 */     int offset = start;
/*     */     
/* 177 */     while (offset < end) {
/* 178 */       char c = desc.charAt(offset++);
/*     */       
/* 180 */       if (c == 'L') {
/* 181 */         int idEnd = offset;
/*     */         
/* 183 */         while (idEnd < end) {
/* 184 */           c = desc.charAt(idEnd);
/* 185 */           if (c == ';')
/* 186 */             break;  idEnd++;
/*     */         } 
/*     */         
/* 189 */         if (idEnd >= end) throw new IllegalArgumentException("invalid descriptor: " + desc.subSequence(start, end));
/*     */         
/* 191 */         String cls = desc.subSequence(offset, idEnd).toString();
/* 192 */         String mappedCls = mapClassName(cls, srcNamespace, dstNamespace);
/*     */         
/* 194 */         if (mappedCls != null && !mappedCls.equals(cls)) {
/* 195 */           if (ret == null) ret = new StringBuilder(end - start);
/*     */           
/* 197 */           ret.append(desc, copyOffset, offset);
/* 198 */           ret.append(mappedCls);
/* 199 */           copyOffset = idEnd;
/*     */         } 
/*     */         
/* 202 */         offset = idEnd + 1;
/*     */       } 
/*     */     } 
/*     */     
/* 206 */     if (ret == null) return desc.subSequence(start, end).toString();
/*     */     
/* 208 */     ret.append(desc, copyOffset, end);
/*     */     
/* 210 */     return ret.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   Collection<? extends ClassMappingView> getClasses();
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   ClassMappingView getClass(String paramString);
/*     */   
/*     */   void accept(MappingVisitor paramMappingVisitor, VisitOrder paramVisitOrder) throws IOException;
/*     */   
/*     */   public static interface ElementMappingView
/*     */   {
/*     */     MappingTreeView getTree();
/*     */     
/*     */     @Nullable
/*     */     default String getName(int namespace) {
/* 228 */       if (namespace < 0) {
/* 229 */         return getSrcName();
/*     */       }
/* 231 */       return getDstName(namespace);
/*     */     } String getSrcName();
/*     */     @Nullable
/*     */     String getDstName(int param1Int);
/*     */     @Nullable
/*     */     default String getName(String namespace) {
/* 237 */       int nsId = getTree().getNamespaceId(namespace);
/*     */       
/* 239 */       if (nsId == -2) {
/* 240 */         return null;
/*     */       }
/* 242 */       return getName(nsId);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     String getComment();
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface ClassMappingView
/*     */     extends ElementMappingView
/*     */   {
/*     */     Collection<? extends MappingTreeView.FieldMappingView> getFields();
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     MappingTreeView.FieldMappingView getField(String param1String1, @Nullable String param1String2);
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     default MappingTreeView.FieldMappingView getField(String name, @Nullable String desc, int namespace) {
/* 264 */       if (namespace < 0) return getField(name, desc);
/*     */       
/* 266 */       for (MappingTreeView.FieldMappingView field : getFields()) {
/* 267 */         if (!name.equals(field.getDstName(namespace)))
/*     */           continue;  String mDesc;
/* 269 */         if (desc != null && (mDesc = field.getDesc(namespace)) != null && !desc.equals(mDesc))
/*     */           continue; 
/* 271 */         return field;
/*     */       } 
/*     */       
/* 274 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Collection<? extends MappingTreeView.MethodMappingView> getMethods();
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     MappingTreeView.MethodMappingView getMethod(String param1String1, @Nullable String param1String2);
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     default MappingTreeView.MethodMappingView getMethod(String name, @Nullable String desc, int namespace) {
/* 290 */       if (namespace < 0) return getMethod(name, desc);
/*     */       
/* 292 */       for (MappingTreeView.MethodMappingView method : getMethods()) {
/* 293 */         if (!name.equals(method.getDstName(namespace)))
/*     */           continue; 
/*     */         String mDesc;
/* 296 */         if (desc != null && (mDesc = method.getDesc(namespace)) != null && !desc.equals(mDesc) && (!desc.endsWith(")") || !mDesc.startsWith(desc)))
/*     */           continue; 
/* 298 */         return method;
/*     */       } 
/*     */       
/* 301 */       return null;
/*     */     } }
/*     */   
/*     */   public static interface MemberMappingView extends ElementMappingView {
/*     */     MappingTreeView.ClassMappingView getOwner();
/*     */     
/*     */     @Nullable
/*     */     String getSrcDesc();
/*     */     
/*     */     @Nullable
/*     */     default String getDstDesc(int namespace) {
/* 312 */       String srcDesc = getSrcDesc();
/*     */       
/* 314 */       return (srcDesc != null) ? getTree().mapDesc(srcDesc, namespace) : null;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     default String getDesc(int namespace) {
/* 319 */       String srcDesc = getSrcDesc();
/*     */       
/* 321 */       if (namespace < 0 || srcDesc == null) {
/* 322 */         return srcDesc;
/*     */       }
/* 324 */       return getTree().mapDesc(srcDesc, namespace);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     default String getDesc(String namespace) {
/* 330 */       int nsId = getTree().getNamespaceId(namespace);
/*     */       
/* 332 */       if (nsId == -2) {
/* 333 */         return null;
/*     */       }
/* 335 */       return getDesc(nsId);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface FieldMappingView extends MemberMappingView {}
/*     */   
/*     */   public static interface MethodMappingView extends MemberMappingView {
/*     */     Collection<? extends MappingTreeView.MethodArgMappingView> getArgs();
/*     */     
/*     */     @Nullable
/*     */     MappingTreeView.MethodArgMappingView getArg(int param1Int1, int param1Int2, @Nullable String param1String);
/*     */     
/*     */     Collection<? extends MappingTreeView.MethodVarMappingView> getVars();
/*     */     
/*     */     @Nullable
/*     */     MappingTreeView.MethodVarMappingView getVar(int param1Int1, int param1Int2, int param1Int3, int param1Int4, @Nullable String param1String);
/*     */   }
/*     */   
/*     */   public static interface MethodVarMappingView extends ElementMappingView {
/*     */     MappingTreeView.MethodMappingView getMethod();
/*     */     
/*     */     int getLvtRowIndex();
/*     */     
/*     */     int getLvIndex();
/*     */     
/*     */     int getStartOpIdx();
/*     */     
/*     */     int getEndOpIdx();
/*     */   }
/*     */   
/*     */   public static interface MethodArgMappingView extends ElementMappingView {
/*     */     MappingTreeView.MethodMappingView getMethod();
/*     */     
/*     */     int getArgPosition();
/*     */     
/*     */     int getLvIndex();
/*     */   }
/*     */   
/*     */   public static interface MetadataEntryView {
/*     */     String getKey();
/*     */     
/*     */     @Nullable
/*     */     String getValue();
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\tree\MappingTreeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */