/*     */ package org.ms.donutduels.foliascheduler.mappingio.format;
/*     */ 
/*     */ 
/*     */ 
/*     */ class FeatureSetImpl
/*     */   implements FeatureSet
/*     */ {
/*     */   private final boolean hasNamespaces;
/*     */   private final FeatureSet.MetadataSupport fileMetadata;
/*     */   private final FeatureSet.MetadataSupport elementMetadata;
/*     */   private final FeatureSet.NameSupport packages;
/*     */   private final FeatureSet.ClassSupport classes;
/*     */   private final FeatureSet.MemberSupport fields;
/*     */   private final FeatureSet.MemberSupport methods;
/*     */   private final FeatureSet.LocalSupport args;
/*     */   private final FeatureSet.LocalSupport vars;
/*     */   private final FeatureSet.ElementCommentSupport elementComments;
/*     */   private final boolean hasFileComments;
/*     */   
/*     */   FeatureSetImpl(boolean hasNamespaces, FeatureSet.MetadataSupport fileMetadata, FeatureSet.MetadataSupport elementMetadata, FeatureSet.NameSupport packages, FeatureSet.ClassSupport classes, FeatureSet.MemberSupport fields, FeatureSet.MemberSupport methods, FeatureSet.LocalSupport args, FeatureSet.LocalSupport vars, FeatureSet.ElementCommentSupport elementComments, boolean hasFileComments) {
/*  21 */     this.hasNamespaces = hasNamespaces;
/*  22 */     this.fileMetadata = fileMetadata;
/*  23 */     this.elementMetadata = elementMetadata;
/*  24 */     this.packages = packages;
/*  25 */     this.classes = classes;
/*  26 */     this.fields = fields;
/*  27 */     this.methods = methods;
/*  28 */     this.args = args;
/*  29 */     this.vars = vars;
/*  30 */     this.elementComments = elementComments;
/*  31 */     this.hasFileComments = hasFileComments;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNamespaces() {
/*  36 */     return this.hasNamespaces;
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureSet.MetadataSupport fileMetadata() {
/*  41 */     return this.fileMetadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureSet.MetadataSupport elementMetadata() {
/*  46 */     return this.elementMetadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureSet.NameSupport packages() {
/*  51 */     return this.packages;
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureSet.ClassSupport classes() {
/*  56 */     return this.classes;
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureSet.MemberSupport fields() {
/*  61 */     return this.fields;
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureSet.MemberSupport methods() {
/*  66 */     return this.methods;
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureSet.LocalSupport args() {
/*  71 */     return this.args;
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureSet.LocalSupport vars() {
/*  76 */     return this.vars;
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureSet.ElementCommentSupport elementComments() {
/*  81 */     return this.elementComments;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasFileComments() {
/*  86 */     return this.hasFileComments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ClassSupportImpl
/*     */     extends NameSupportImpl
/*     */     implements FeatureSet.ClassSupport
/*     */   {
/*     */     private final boolean hasRepackaging;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ClassSupportImpl(FeatureSet.NameSupport names, boolean hasRepackaging) {
/* 103 */       super(names.srcNames(), names.dstNames());
/* 104 */       this.hasRepackaging = hasRepackaging;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasRepackaging() {
/* 109 */       return this.hasRepackaging;
/*     */     }
/*     */   }
/*     */   
/*     */   static class MemberSupportImpl extends NameSupportImpl implements FeatureSet.MemberSupport {
/*     */     private final FeatureSet.DescSupport descriptors;
/*     */     
/*     */     MemberSupportImpl(FeatureSet.NameSupport names, FeatureSet.DescSupport descriptors) {
/* 117 */       super(names.srcNames(), names.dstNames());
/* 118 */       this.descriptors = descriptors;
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureSet.FeaturePresence srcDescs() {
/* 123 */       return this.descriptors.srcDescs();
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureSet.FeaturePresence dstDescs() {
/* 128 */       return this.descriptors.dstDescs();
/*     */     } }
/*     */   
/*     */   static class LocalSupportImpl extends NameSupportImpl implements FeatureSet.LocalSupport { private final FeatureSet.FeaturePresence positions;
/*     */     private final FeatureSet.FeaturePresence lvIndices;
/*     */     private final FeatureSet.FeaturePresence lvtRowIndices;
/*     */     
/*     */     LocalSupportImpl(FeatureSet.FeaturePresence positions, FeatureSet.FeaturePresence lvIndices, FeatureSet.FeaturePresence lvtRowIndices, FeatureSet.FeaturePresence startOpIndices, FeatureSet.FeaturePresence endOpIndices, FeatureSet.NameSupport names, FeatureSet.DescSupport descriptors) {
/* 136 */       super(names.srcNames(), names.dstNames());
/* 137 */       this.positions = positions;
/* 138 */       this.lvIndices = lvIndices;
/* 139 */       this.lvtRowIndices = lvtRowIndices;
/* 140 */       this.startOpIndices = startOpIndices;
/* 141 */       this.endOpIndices = endOpIndices;
/* 142 */       this.descriptors = descriptors;
/*     */     }
/*     */     private final FeatureSet.FeaturePresence startOpIndices; private final FeatureSet.FeaturePresence endOpIndices; private final FeatureSet.DescSupport descriptors;
/*     */     
/*     */     public FeatureSet.FeaturePresence positions() {
/* 147 */       return this.positions;
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureSet.FeaturePresence lvIndices() {
/* 152 */       return this.lvIndices;
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureSet.FeaturePresence lvtRowIndices() {
/* 157 */       return this.lvtRowIndices;
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureSet.FeaturePresence startOpIndices() {
/* 162 */       return this.startOpIndices;
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureSet.FeaturePresence endOpIndices() {
/* 167 */       return this.endOpIndices;
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureSet.FeaturePresence srcDescs() {
/* 172 */       return this.descriptors.srcDescs();
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureSet.FeaturePresence dstDescs() {
/* 177 */       return this.descriptors.dstDescs();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   static class NameSupportImpl
/*     */     implements FeatureSet.NameSupport
/*     */   {
/*     */     private final FeatureSet.FeaturePresence srcNames;
/*     */     
/*     */     private final FeatureSet.FeaturePresence dstNames;
/*     */     
/*     */     NameSupportImpl(FeatureSet.FeaturePresence srcNames, FeatureSet.FeaturePresence dstNames) {
/* 190 */       this.srcNames = srcNames;
/* 191 */       this.dstNames = dstNames;
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureSet.FeaturePresence srcNames() {
/* 196 */       return this.srcNames;
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureSet.FeaturePresence dstNames() {
/* 201 */       return this.dstNames;
/*     */     }
/*     */   }
/*     */   
/*     */   static class DescSupportImpl implements FeatureSet.DescSupport {
/*     */     private final FeatureSet.FeaturePresence srcDescriptors;
/*     */     private final FeatureSet.FeaturePresence dstDescriptors;
/*     */     
/*     */     DescSupportImpl(FeatureSet.FeaturePresence srcDescriptors, FeatureSet.FeaturePresence dstDescriptors) {
/* 210 */       this.srcDescriptors = srcDescriptors;
/* 211 */       this.dstDescriptors = dstDescriptors;
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureSet.FeaturePresence srcDescs() {
/* 216 */       return this.srcDescriptors;
/*     */     }
/*     */ 
/*     */     
/*     */     public FeatureSet.FeaturePresence dstDescs() {
/* 221 */       return this.dstDescriptors;
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\FeatureSetImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */