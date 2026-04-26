/*    */ package org.ms.donutduels.foliascheduler.mappingio.format.srg;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.MappingVisitor;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.ColumnFileReader;
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
/*    */ public final class JamFileReader
/*    */ {
/*    */   public static void read(Reader reader, MappingVisitor visitor) throws IOException {
/* 44 */     read(reader, "source", "target", visitor);
/*    */   }
/*    */   
/*    */   public static void read(Reader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException {
/* 48 */     read(new ColumnFileReader(reader, '\t', ' '), sourceNs, targetNs, visitor);
/*    */   }
/*    */   
/*    */   private static void read(ColumnFileReader reader, String sourceNs, String targetNs, MappingVisitor visitor) throws IOException {
/*    */     // Byte code:
/*    */     //   0: aload_3
/*    */     //   1: invokeinterface getFlags : ()Ljava/util/Set;
/*    */     //   6: astore #4
/*    */     //   8: aconst_null
/*    */     //   9: astore #5
/*    */     //   11: iconst_0
/*    */     //   12: istore #6
/*    */     //   14: aload #4
/*    */     //   16: getstatic org/ms/donutduels/foliascheduler/mappingio/MappingFlag.NEEDS_ELEMENT_UNIQUENESS : Lorg/ms/donutduels/foliascheduler/mappingio/MappingFlag;
/*    */     //   19: invokeinterface contains : (Ljava/lang/Object;)Z
/*    */     //   24: ifeq -> 41
/*    */     //   27: aload_3
/*    */     //   28: astore #5
/*    */     //   30: new org/ms/donutduels/foliascheduler/mappingio/tree/MemoryMappingTree
/*    */     //   33: dup
/*    */     //   34: invokespecial <init> : ()V
/*    */     //   37: astore_3
/*    */     //   38: goto -> 62
/*    */     //   41: aload #4
/*    */     //   43: getstatic org/ms/donutduels/foliascheduler/mappingio/MappingFlag.NEEDS_MULTIPLE_PASSES : Lorg/ms/donutduels/foliascheduler/mappingio/MappingFlag;
/*    */     //   46: invokeinterface contains : (Ljava/lang/Object;)Z
/*    */     //   51: ifeq -> 62
/*    */     //   54: aload_0
/*    */     //   55: invokevirtual mark : ()I
/*    */     //   58: pop
/*    */     //   59: iconst_1
/*    */     //   60: istore #6
/*    */     //   62: aload_3
/*    */     //   63: invokeinterface visitHeader : ()Z
/*    */     //   68: ifeq -> 82
/*    */     //   71: aload_3
/*    */     //   72: aload_1
/*    */     //   73: aload_2
/*    */     //   74: invokestatic singletonList : (Ljava/lang/Object;)Ljava/util/List;
/*    */     //   77: invokeinterface visitNamespaces : (Ljava/lang/String;Ljava/util/List;)V
/*    */     //   82: aload_3
/*    */     //   83: invokeinterface visitContent : ()Z
/*    */     //   88: ifeq -> 876
/*    */     //   91: aconst_null
/*    */     //   92: astore #7
/*    */     //   94: iconst_0
/*    */     //   95: istore #8
/*    */     //   97: aconst_null
/*    */     //   98: astore #9
/*    */     //   100: aconst_null
/*    */     //   101: astore #10
/*    */     //   103: iconst_0
/*    */     //   104: istore #11
/*    */     //   106: iconst_0
/*    */     //   107: istore #12
/*    */     //   109: iconst_0
/*    */     //   110: istore #14
/*    */     //   112: aload_0
/*    */     //   113: ldc 'CL'
/*    */     //   115: invokevirtual nextCol : (Ljava/lang/String;)Z
/*    */     //   118: ifeq -> 264
/*    */     //   121: aload_0
/*    */     //   122: invokevirtual nextCol : ()Ljava/lang/String;
/*    */     //   125: astore #15
/*    */     //   127: aload #15
/*    */     //   129: ifnull -> 140
/*    */     //   132: aload #15
/*    */     //   134: invokevirtual isEmpty : ()Z
/*    */     //   137: ifeq -> 170
/*    */     //   140: new java/io/IOException
/*    */     //   143: dup
/*    */     //   144: new java/lang/StringBuilder
/*    */     //   147: dup
/*    */     //   148: invokespecial <init> : ()V
/*    */     //   151: ldc 'missing class-name-a in line '
/*    */     //   153: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*    */     //   156: aload_0
/*    */     //   157: invokevirtual getLineNumber : ()I
/*    */     //   160: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*    */     //   163: invokevirtual toString : ()Ljava/lang/String;
/*    */     //   166: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   169: athrow
/*    */     //   170: aload #15
/*    */     //   172: astore #7
/*    */     //   174: aload_3
/*    */     //   175: aload #15
/*    */     //   177: invokeinterface visitClass : (Ljava/lang/String;)Z
/*    */     //   182: istore #8
/*    */     //   184: iload #8
/*    */     //   186: ifeq -> 261
/*    */     //   189: aload_0
/*    */     //   190: invokevirtual nextCol : ()Ljava/lang/String;
/*    */     //   193: astore #16
/*    */     //   195: aload #16
/*    */     //   197: ifnull -> 208
/*    */     //   200: aload #16
/*    */     //   202: invokevirtual isEmpty : ()Z
/*    */     //   205: ifeq -> 238
/*    */     //   208: new java/io/IOException
/*    */     //   211: dup
/*    */     //   212: new java/lang/StringBuilder
/*    */     //   215: dup
/*    */     //   216: invokespecial <init> : ()V
/*    */     //   219: ldc 'missing class-name-b in line '
/*    */     //   221: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*    */     //   224: aload_0
/*    */     //   225: invokevirtual getLineNumber : ()I
/*    */     //   228: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*    */     //   231: invokevirtual toString : ()Ljava/lang/String;
/*    */     //   234: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   237: athrow
/*    */     //   238: aload_3
/*    */     //   239: getstatic org/ms/donutduels/foliascheduler/mappingio/MappedElementKind.CLASS : Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;
/*    */     //   242: iconst_0
/*    */     //   243: aload #16
/*    */     //   245: invokeinterface visitDstName : (Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;ILjava/lang/String;)V
/*    */     //   250: aload_3
/*    */     //   251: getstatic org/ms/donutduels/foliascheduler/mappingio/MappedElementKind.CLASS : Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;
/*    */     //   254: invokeinterface visitElementContent : (Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;)Z
/*    */     //   259: istore #8
/*    */     //   261: goto -> 868
/*    */     //   264: aload_0
/*    */     //   265: ldc 'MD'
/*    */     //   267: invokevirtual nextCol : (Ljava/lang/String;)Z
/*    */     //   270: dup
/*    */     //   271: istore #13
/*    */     //   273: ifne -> 297
/*    */     //   276: aload_0
/*    */     //   277: ldc 'FD'
/*    */     //   279: invokevirtual nextCol : (Ljava/lang/String;)Z
/*    */     //   282: ifne -> 297
/*    */     //   285: aload_0
/*    */     //   286: ldc 'MP'
/*    */     //   288: invokevirtual nextCol : (Ljava/lang/String;)Z
/*    */     //   291: dup
/*    */     //   292: istore #14
/*    */     //   294: ifeq -> 868
/*    */     //   297: aload_0
/*    */     //   298: invokevirtual nextCol : ()Ljava/lang/String;
/*    */     //   301: astore #15
/*    */     //   303: aload #15
/*    */     //   305: ifnonnull -> 338
/*    */     //   308: new java/io/IOException
/*    */     //   311: dup
/*    */     //   312: new java/lang/StringBuilder
/*    */     //   315: dup
/*    */     //   316: invokespecial <init> : ()V
/*    */     //   319: ldc 'missing class-name-a in line '
/*    */     //   321: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*    */     //   324: aload_0
/*    */     //   325: invokevirtual getLineNumber : ()I
/*    */     //   328: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*    */     //   331: invokevirtual toString : ()Ljava/lang/String;
/*    */     //   334: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   337: athrow
/*    */     //   338: aload_0
/*    */     //   339: invokevirtual nextCol : ()Ljava/lang/String;
/*    */     //   342: astore #16
/*    */     //   344: aload #16
/*    */     //   346: ifnull -> 357
/*    */     //   349: aload #16
/*    */     //   351: invokevirtual isEmpty : ()Z
/*    */     //   354: ifeq -> 387
/*    */     //   357: new java/io/IOException
/*    */     //   360: dup
/*    */     //   361: new java/lang/StringBuilder
/*    */     //   364: dup
/*    */     //   365: invokespecial <init> : ()V
/*    */     //   368: ldc 'missing member-name-a in line '
/*    */     //   370: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*    */     //   373: aload_0
/*    */     //   374: invokevirtual getLineNumber : ()I
/*    */     //   377: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*    */     //   380: invokevirtual toString : ()Ljava/lang/String;
/*    */     //   383: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   386: athrow
/*    */     //   387: aload_0
/*    */     //   388: invokevirtual nextCol : ()Ljava/lang/String;
/*    */     //   391: astore #17
/*    */     //   393: aload #17
/*    */     //   395: ifnull -> 406
/*    */     //   398: aload #17
/*    */     //   400: invokevirtual isEmpty : ()Z
/*    */     //   403: ifeq -> 436
/*    */     //   406: new java/io/IOException
/*    */     //   409: dup
/*    */     //   410: new java/lang/StringBuilder
/*    */     //   413: dup
/*    */     //   414: invokespecial <init> : ()V
/*    */     //   417: ldc 'missing member-desc-a in line '
/*    */     //   419: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*    */     //   422: aload_0
/*    */     //   423: invokevirtual getLineNumber : ()I
/*    */     //   426: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*    */     //   429: invokevirtual toString : ()Ljava/lang/String;
/*    */     //   432: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   435: athrow
/*    */     //   436: aload_0
/*    */     //   437: invokevirtual nextCol : ()Ljava/lang/String;
/*    */     //   440: astore #18
/*    */     //   442: aload_0
/*    */     //   443: invokevirtual nextCol : ()Ljava/lang/String;
/*    */     //   446: astore #19
/*    */     //   448: aload_0
/*    */     //   449: invokevirtual nextCol : ()Ljava/lang/String;
/*    */     //   452: astore #20
/*    */     //   454: iconst_m1
/*    */     //   455: istore #21
/*    */     //   457: iload #14
/*    */     //   459: ifne -> 469
/*    */     //   462: aload #18
/*    */     //   464: astore #22
/*    */     //   466: goto -> 547
/*    */     //   469: aload #18
/*    */     //   471: invokestatic parseInt : (Ljava/lang/String;)I
/*    */     //   474: istore #21
/*    */     //   476: aload #20
/*    */     //   478: ifnull -> 489
/*    */     //   481: aload #20
/*    */     //   483: invokevirtual isEmpty : ()Z
/*    */     //   486: ifeq -> 496
/*    */     //   489: aload #19
/*    */     //   491: astore #22
/*    */     //   493: goto -> 547
/*    */     //   496: aload #19
/*    */     //   498: astore #23
/*    */     //   500: aload #23
/*    */     //   502: ifnull -> 513
/*    */     //   505: aload #23
/*    */     //   507: invokevirtual isEmpty : ()Z
/*    */     //   510: ifeq -> 543
/*    */     //   513: new java/io/IOException
/*    */     //   516: dup
/*    */     //   517: new java/lang/StringBuilder
/*    */     //   520: dup
/*    */     //   521: invokespecial <init> : ()V
/*    */     //   524: ldc 'missing parameter-desc-a in line '
/*    */     //   526: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*    */     //   529: aload_0
/*    */     //   530: invokevirtual getLineNumber : ()I
/*    */     //   533: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*    */     //   536: invokevirtual toString : ()Ljava/lang/String;
/*    */     //   539: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   542: athrow
/*    */     //   543: aload #20
/*    */     //   545: astore #22
/*    */     //   547: aload #22
/*    */     //   549: ifnull -> 560
/*    */     //   552: aload #22
/*    */     //   554: invokevirtual isEmpty : ()Z
/*    */     //   557: ifeq -> 590
/*    */     //   560: new java/io/IOException
/*    */     //   563: dup
/*    */     //   564: new java/lang/StringBuilder
/*    */     //   567: dup
/*    */     //   568: invokespecial <init> : ()V
/*    */     //   571: ldc 'missing name-b in line '
/*    */     //   573: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*    */     //   576: aload_0
/*    */     //   577: invokevirtual getLineNumber : ()I
/*    */     //   580: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*    */     //   583: invokevirtual toString : ()Ljava/lang/String;
/*    */     //   586: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   589: athrow
/*    */     //   590: aload #15
/*    */     //   592: aload #7
/*    */     //   594: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   597: ifne -> 640
/*    */     //   600: aload #15
/*    */     //   602: astore #7
/*    */     //   604: aconst_null
/*    */     //   605: astore #9
/*    */     //   607: aconst_null
/*    */     //   608: astore #10
/*    */     //   610: aload_3
/*    */     //   611: aload #15
/*    */     //   613: invokeinterface visitClass : (Ljava/lang/String;)Z
/*    */     //   618: ifeq -> 637
/*    */     //   621: aload_3
/*    */     //   622: getstatic org/ms/donutduels/foliascheduler/mappingio/MappedElementKind.CLASS : Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;
/*    */     //   625: invokeinterface visitElementContent : (Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;)Z
/*    */     //   630: ifeq -> 637
/*    */     //   633: iconst_1
/*    */     //   634: goto -> 638
/*    */     //   637: iconst_0
/*    */     //   638: istore #8
/*    */     //   640: iload #8
/*    */     //   642: ifne -> 648
/*    */     //   645: goto -> 868
/*    */     //   648: iconst_0
/*    */     //   649: istore #24
/*    */     //   651: iload #13
/*    */     //   653: ifne -> 665
/*    */     //   656: iload #14
/*    */     //   658: ifne -> 665
/*    */     //   661: iconst_1
/*    */     //   662: goto -> 666
/*    */     //   665: iconst_0
/*    */     //   666: istore #25
/*    */     //   668: iload #25
/*    */     //   670: ifeq -> 688
/*    */     //   673: aload_3
/*    */     //   674: aload #16
/*    */     //   676: aload #17
/*    */     //   678: invokeinterface visitField : (Ljava/lang/String;Ljava/lang/String;)Z
/*    */     //   683: istore #11
/*    */     //   685: goto -> 747
/*    */     //   688: iload #14
/*    */     //   690: ifeq -> 724
/*    */     //   693: aload #16
/*    */     //   695: aload #9
/*    */     //   697: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   700: ifeq -> 713
/*    */     //   703: aload #17
/*    */     //   705: aload #10
/*    */     //   707: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   710: ifne -> 717
/*    */     //   713: iconst_1
/*    */     //   714: goto -> 718
/*    */     //   717: iconst_0
/*    */     //   718: dup
/*    */     //   719: istore #24
/*    */     //   721: ifeq -> 747
/*    */     //   724: aload #16
/*    */     //   726: astore #9
/*    */     //   728: aload #17
/*    */     //   730: astore #10
/*    */     //   732: aload_3
/*    */     //   733: aload #16
/*    */     //   735: aload #17
/*    */     //   737: invokeinterface visitMethod : (Ljava/lang/String;Ljava/lang/String;)Z
/*    */     //   742: istore #11
/*    */     //   744: iconst_0
/*    */     //   745: istore #12
/*    */     //   747: iload #11
/*    */     //   749: ifne -> 755
/*    */     //   752: goto -> 868
/*    */     //   755: iload #25
/*    */     //   757: ifeq -> 785
/*    */     //   760: aload_3
/*    */     //   761: getstatic org/ms/donutduels/foliascheduler/mappingio/MappedElementKind.FIELD : Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;
/*    */     //   764: iconst_0
/*    */     //   765: aload #22
/*    */     //   767: invokeinterface visitDstName : (Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;ILjava/lang/String;)V
/*    */     //   772: aload_3
/*    */     //   773: getstatic org/ms/donutduels/foliascheduler/mappingio/MappedElementKind.FIELD : Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;
/*    */     //   776: invokeinterface visitElementContent : (Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;)Z
/*    */     //   781: pop
/*    */     //   782: goto -> 868
/*    */     //   785: iload #13
/*    */     //   787: ifeq -> 802
/*    */     //   790: aload_3
/*    */     //   791: getstatic org/ms/donutduels/foliascheduler/mappingio/MappedElementKind.METHOD : Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;
/*    */     //   794: iconst_0
/*    */     //   795: aload #22
/*    */     //   797: invokeinterface visitDstName : (Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;ILjava/lang/String;)V
/*    */     //   802: iload #13
/*    */     //   804: ifne -> 812
/*    */     //   807: iload #24
/*    */     //   809: ifeq -> 823
/*    */     //   812: aload_3
/*    */     //   813: getstatic org/ms/donutduels/foliascheduler/mappingio/MappedElementKind.METHOD : Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;
/*    */     //   816: invokeinterface visitElementContent : (Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;)Z
/*    */     //   821: istore #12
/*    */     //   823: iload #14
/*    */     //   825: ifeq -> 868
/*    */     //   828: iload #12
/*    */     //   830: ifeq -> 868
/*    */     //   833: aload_3
/*    */     //   834: iload #21
/*    */     //   836: iconst_m1
/*    */     //   837: aconst_null
/*    */     //   838: invokeinterface visitMethodArg : (IILjava/lang/String;)Z
/*    */     //   843: ifeq -> 868
/*    */     //   846: aload_3
/*    */     //   847: getstatic org/ms/donutduels/foliascheduler/mappingio/MappedElementKind.METHOD_ARG : Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;
/*    */     //   850: iconst_0
/*    */     //   851: aload #22
/*    */     //   853: invokeinterface visitDstName : (Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;ILjava/lang/String;)V
/*    */     //   858: aload_3
/*    */     //   859: getstatic org/ms/donutduels/foliascheduler/mappingio/MappedElementKind.METHOD_ARG : Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;
/*    */     //   862: invokeinterface visitElementContent : (Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;)Z
/*    */     //   867: pop
/*    */     //   868: aload_0
/*    */     //   869: iconst_0
/*    */     //   870: invokevirtual nextLine : (I)Z
/*    */     //   873: ifne -> 109
/*    */     //   876: aload_3
/*    */     //   877: invokeinterface visitEnd : ()Z
/*    */     //   882: ifeq -> 888
/*    */     //   885: goto -> 932
/*    */     //   888: iload #6
/*    */     //   890: ifne -> 903
/*    */     //   893: new java/lang/IllegalStateException
/*    */     //   896: dup
/*    */     //   897: ldc 'repeated visitation requested without NEEDS_MULTIPLE_PASSES'
/*    */     //   899: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   902: athrow
/*    */     //   903: aload_0
/*    */     //   904: invokevirtual reset : ()I
/*    */     //   907: istore #7
/*    */     //   909: getstatic org/ms/donutduels/foliascheduler/mappingio/format/srg/JamFileReader.$assertionsDisabled : Z
/*    */     //   912: ifne -> 929
/*    */     //   915: iload #7
/*    */     //   917: iconst_1
/*    */     //   918: if_icmpeq -> 929
/*    */     //   921: new java/lang/AssertionError
/*    */     //   924: dup
/*    */     //   925: invokespecial <init> : ()V
/*    */     //   928: athrow
/*    */     //   929: goto -> 62
/*    */     //   932: aload #5
/*    */     //   934: ifnull -> 948
/*    */     //   937: aload_3
/*    */     //   938: checkcast org/ms/donutduels/foliascheduler/mappingio/tree/MappingTree
/*    */     //   941: aload #5
/*    */     //   943: invokeinterface accept : (Lorg/ms/donutduels/foliascheduler/mappingio/MappingVisitor;)V
/*    */     //   948: return
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #52	-> 0
/*    */     //   #53	-> 8
/*    */     //   #54	-> 11
/*    */     //   #56	-> 14
/*    */     //   #57	-> 27
/*    */     //   #58	-> 30
/*    */     //   #59	-> 41
/*    */     //   #60	-> 54
/*    */     //   #61	-> 59
/*    */     //   #65	-> 62
/*    */     //   #66	-> 71
/*    */     //   #69	-> 82
/*    */     //   #70	-> 91
/*    */     //   #71	-> 94
/*    */     //   #72	-> 97
/*    */     //   #73	-> 100
/*    */     //   #74	-> 103
/*    */     //   #75	-> 106
/*    */     //   #79	-> 109
/*    */     //   #81	-> 112
/*    */     //   #82	-> 121
/*    */     //   #83	-> 127
/*    */     //   #85	-> 170
/*    */     //   #86	-> 174
/*    */     //   #88	-> 184
/*    */     //   #89	-> 189
/*    */     //   #90	-> 195
/*    */     //   #92	-> 238
/*    */     //   #93	-> 250
/*    */     //   #95	-> 261
/*    */     //   #96	-> 288
/*    */     //   #97	-> 297
/*    */     //   #98	-> 303
/*    */     //   #100	-> 338
/*    */     //   #101	-> 344
/*    */     //   #103	-> 387
/*    */     //   #104	-> 393
/*    */     //   #106	-> 436
/*    */     //   #107	-> 442
/*    */     //   #108	-> 448
/*    */     //   #110	-> 454
/*    */     //   #114	-> 457
/*    */     //   #115	-> 462
/*    */     //   #117	-> 469
/*    */     //   #119	-> 476
/*    */     //   #120	-> 489
/*    */     //   #122	-> 496
/*    */     //   #123	-> 500
/*    */     //   #125	-> 543
/*    */     //   #129	-> 547
/*    */     //   #131	-> 590
/*    */     //   #132	-> 600
/*    */     //   #133	-> 604
/*    */     //   #134	-> 607
/*    */     //   #135	-> 610
/*    */     //   #138	-> 640
/*    */     //   #139	-> 648
/*    */     //   #140	-> 651
/*    */     //   #142	-> 668
/*    */     //   #143	-> 673
/*    */     //   #144	-> 688
/*    */     //   #145	-> 724
/*    */     //   #146	-> 728
/*    */     //   #147	-> 732
/*    */     //   #148	-> 744
/*    */     //   #151	-> 747
/*    */     //   #153	-> 755
/*    */     //   #154	-> 760
/*    */     //   #155	-> 772
/*    */     //   #156	-> 782
/*    */     //   #157	-> 785
/*    */     //   #158	-> 790
/*    */     //   #161	-> 802
/*    */     //   #162	-> 812
/*    */     //   #165	-> 823
/*    */     //   #166	-> 846
/*    */     //   #167	-> 858
/*    */     //   #170	-> 868
/*    */     //   #173	-> 876
/*    */     //   #175	-> 888
/*    */     //   #176	-> 893
/*    */     //   #179	-> 903
/*    */     //   #180	-> 909
/*    */     //   #181	-> 929
/*    */     //   #183	-> 932
/*    */     //   #184	-> 937
/*    */     //   #186	-> 948
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   195	66	16	dstName	Ljava/lang/String;
/*    */     //   127	134	15	srcName	Ljava/lang/String;
/*    */     //   466	3	22	dstName	Ljava/lang/String;
/*    */     //   493	3	22	dstName	Ljava/lang/String;
/*    */     //   500	47	23	argSrcDesc	Ljava/lang/String;
/*    */     //   303	565	15	clsSrcName	Ljava/lang/String;
/*    */     //   344	524	16	memberSrcName	Ljava/lang/String;
/*    */     //   393	475	17	memberSrcDesc	Ljava/lang/String;
/*    */     //   442	426	18	col5	Ljava/lang/String;
/*    */     //   448	420	19	col6	Ljava/lang/String;
/*    */     //   454	414	20	col7	Ljava/lang/String;
/*    */     //   457	411	21	argSrcPos	I
/*    */     //   547	321	22	dstName	Ljava/lang/String;
/*    */     //   651	217	24	newMethod	Z
/*    */     //   668	200	25	isField	Z
/*    */     //   273	595	13	isMethod	Z
/*    */     //   112	756	14	isArg	Z
/*    */     //   94	782	7	lastClassName	Ljava/lang/String;
/*    */     //   97	779	8	visitClass	Z
/*    */     //   100	776	9	lastMethodName	Ljava/lang/String;
/*    */     //   103	773	10	lastMethodDesc	Ljava/lang/String;
/*    */     //   106	770	11	visitMember	Z
/*    */     //   109	767	12	visitMethodContent	Z
/*    */     //   909	20	7	markIdx	I
/*    */     //   0	949	0	reader	Lorg/ms/donutduels/foliascheduler/mappingio/format/ColumnFileReader;
/*    */     //   0	949	1	sourceNs	Ljava/lang/String;
/*    */     //   0	949	2	targetNs	Ljava/lang/String;
/*    */     //   0	949	3	visitor	Lorg/ms/donutduels/foliascheduler/mappingio/MappingVisitor;
/*    */     //   8	941	4	flags	Ljava/util/Set;
/*    */     //   11	938	5	parentVisitor	Lorg/ms/donutduels/foliascheduler/mappingio/MappingVisitor;
/*    */     //   14	935	6	readerMarked	Z
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   8	941	4	flags	Ljava/util/Set<Lorg/ms/donutduels/foliascheduler/mappingio/MappingFlag;>;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\srg\JamFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */