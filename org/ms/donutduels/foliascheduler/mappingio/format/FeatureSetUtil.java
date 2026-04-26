/*    */ package org.ms.donutduels.foliascheduler.mappingio.format;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ final class FeatureSetUtil
/*    */ {
/*    */   static boolean isSupported(FeatureSet.MemberSupport members) {
/* 29 */     return (members.srcNames() != FeatureSet.FeaturePresence.ABSENT || members
/* 30 */       .dstNames() != FeatureSet.FeaturePresence.ABSENT || members
/* 31 */       .srcDescs() != FeatureSet.FeaturePresence.ABSENT || members
/* 32 */       .dstDescs() != FeatureSet.FeaturePresence.ABSENT);
/*    */   }
/*    */   
/*    */   static boolean isSupported(FeatureSet.LocalSupport locals) {
/* 36 */     return (locals.positions() != FeatureSet.FeaturePresence.ABSENT || locals
/* 37 */       .lvIndices() != FeatureSet.FeaturePresence.ABSENT || locals
/* 38 */       .lvtRowIndices() != FeatureSet.FeaturePresence.ABSENT || locals
/* 39 */       .startOpIndices() != FeatureSet.FeaturePresence.ABSENT || locals
/* 40 */       .endOpIndices() != FeatureSet.FeaturePresence.ABSENT || locals
/* 41 */       .srcNames() != FeatureSet.FeaturePresence.ABSENT || locals
/* 42 */       .dstNames() != FeatureSet.FeaturePresence.ABSENT || locals
/* 43 */       .srcDescs() != FeatureSet.FeaturePresence.ABSENT || locals
/* 44 */       .dstDescs() != FeatureSet.FeaturePresence.ABSENT);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\FeatureSetUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */