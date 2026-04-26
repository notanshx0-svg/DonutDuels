/*     */ package org.ms.donutduels.foliascheduler.mappingio.format;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.Arrays;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ @Internal
/*     */ public final class ColumnFileReader
/*     */   implements Closeable
/*     */ {
/*     */   public ColumnFileReader(Reader reader, char indentationChar, char columnSeparator) {
/*  35 */     assert indentationChar != '\r';
/*  36 */     assert indentationChar != '\n';
/*  37 */     assert columnSeparator != '\r';
/*  38 */     assert columnSeparator != '\n';
/*     */     
/*  40 */     this.reader = reader;
/*  41 */     this.indentationChar = indentationChar;
/*  42 */     this.columnSeparator = columnSeparator;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  47 */     this.reader.close();
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
/*     */   public boolean nextCol(String expected) throws IOException {
/*  59 */     return (read(false, false, true, expected) != NO_MATCH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String nextCol() throws IOException {
/*  69 */     return nextCol(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String nextCol(boolean unescape) throws IOException {
/*  79 */     return read(unescape, true, true, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String peekCol(boolean unescape) throws IOException {
/*  90 */     return read(unescape, false, true, null);
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
/*     */   @Nullable
/*     */   private String read(boolean unescape, boolean consume, boolean stopAtNextCol, @Nullable String expected) throws IOException {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield eol : Z
/*     */     //   4: ifeq -> 20
/*     */     //   7: aload #4
/*     */     //   9: ifnonnull -> 16
/*     */     //   12: aconst_null
/*     */     //   13: goto -> 19
/*     */     //   16: getstatic org/ms/donutduels/foliascheduler/mappingio/format/ColumnFileReader.NO_MATCH : Ljava/lang/String;
/*     */     //   19: areturn
/*     */     //   20: aload #4
/*     */     //   22: ifnull -> 33
/*     */     //   25: aload #4
/*     */     //   27: invokevirtual length : ()I
/*     */     //   30: goto -> 34
/*     */     //   33: iconst_m1
/*     */     //   34: istore #5
/*     */     //   36: iload #5
/*     */     //   38: ifle -> 78
/*     */     //   41: aload_0
/*     */     //   42: getfield bufferPos : I
/*     */     //   45: iload #5
/*     */     //   47: iadd
/*     */     //   48: aload_0
/*     */     //   49: getfield bufferLimit : I
/*     */     //   52: if_icmplt -> 78
/*     */     //   55: aload_0
/*     */     //   56: iload #5
/*     */     //   58: iload_2
/*     */     //   59: ifne -> 66
/*     */     //   62: iconst_1
/*     */     //   63: goto -> 67
/*     */     //   66: iconst_0
/*     */     //   67: iconst_0
/*     */     //   68: invokespecial fillBuffer : (IZZ)Z
/*     */     //   71: ifne -> 78
/*     */     //   74: getstatic org/ms/donutduels/foliascheduler/mappingio/format/ColumnFileReader.NO_MATCH : Ljava/lang/String;
/*     */     //   77: areturn
/*     */     //   78: aload_0
/*     */     //   79: getfield bufferPos : I
/*     */     //   82: istore #7
/*     */     //   84: iconst_m1
/*     */     //   85: istore #8
/*     */     //   87: iconst_0
/*     */     //   88: istore #9
/*     */     //   90: iconst_m1
/*     */     //   91: istore #10
/*     */     //   93: iconst_0
/*     */     //   94: istore #11
/*     */     //   96: iconst_1
/*     */     //   97: istore #12
/*     */     //   99: iconst_0
/*     */     //   100: istore #13
/*     */     //   102: iload #7
/*     */     //   104: aload_0
/*     */     //   105: getfield bufferLimit : I
/*     */     //   108: if_icmpge -> 260
/*     */     //   111: aload_0
/*     */     //   112: getfield buffer : [C
/*     */     //   115: iload #7
/*     */     //   117: caload
/*     */     //   118: istore #14
/*     */     //   120: iload #14
/*     */     //   122: aload_0
/*     */     //   123: getfield columnSeparator : C
/*     */     //   126: if_icmpne -> 133
/*     */     //   129: iconst_1
/*     */     //   130: goto -> 134
/*     */     //   133: iconst_0
/*     */     //   134: istore #13
/*     */     //   136: iconst_1
/*     */     //   137: istore #11
/*     */     //   139: aload #4
/*     */     //   141: ifnull -> 174
/*     */     //   144: iload #9
/*     */     //   146: iload #5
/*     */     //   148: if_icmpge -> 163
/*     */     //   151: iload #14
/*     */     //   153: aload #4
/*     */     //   155: iload #9
/*     */     //   157: invokevirtual charAt : (I)C
/*     */     //   160: if_icmpne -> 170
/*     */     //   163: iload #9
/*     */     //   165: iload #5
/*     */     //   167: if_icmple -> 174
/*     */     //   170: getstatic org/ms/donutduels/foliascheduler/mappingio/format/ColumnFileReader.NO_MATCH : Ljava/lang/String;
/*     */     //   173: areturn
/*     */     //   174: iload #14
/*     */     //   176: bipush #10
/*     */     //   178: if_icmpeq -> 197
/*     */     //   181: iload #14
/*     */     //   183: bipush #13
/*     */     //   185: if_icmpeq -> 197
/*     */     //   188: iload #13
/*     */     //   190: ifeq -> 229
/*     */     //   193: iload_3
/*     */     //   194: ifeq -> 229
/*     */     //   197: aload_0
/*     */     //   198: getfield bufferPos : I
/*     */     //   201: istore #6
/*     */     //   203: iload #7
/*     */     //   205: istore #10
/*     */     //   207: iload #13
/*     */     //   209: ifne -> 355
/*     */     //   212: iload_2
/*     */     //   213: ifne -> 221
/*     */     //   216: aload #4
/*     */     //   218: ifnull -> 355
/*     */     //   221: aload_0
/*     */     //   222: iconst_1
/*     */     //   223: putfield eol : Z
/*     */     //   226: goto -> 355
/*     */     //   229: iload_1
/*     */     //   230: ifeq -> 251
/*     */     //   233: iload #14
/*     */     //   235: bipush #92
/*     */     //   237: if_icmpne -> 251
/*     */     //   240: iload #8
/*     */     //   242: ifge -> 251
/*     */     //   245: aload_0
/*     */     //   246: getfield bufferPos : I
/*     */     //   249: istore #8
/*     */     //   251: iinc #9, 1
/*     */     //   254: iinc #7, 1
/*     */     //   257: goto -> 102
/*     */     //   260: aload_0
/*     */     //   261: getfield bufferPos : I
/*     */     //   264: istore #14
/*     */     //   266: aload_0
/*     */     //   267: iload #7
/*     */     //   269: aload_0
/*     */     //   270: getfield bufferPos : I
/*     */     //   273: isub
/*     */     //   274: iconst_1
/*     */     //   275: iadd
/*     */     //   276: iload_2
/*     */     //   277: ifne -> 284
/*     */     //   280: iconst_1
/*     */     //   281: goto -> 285
/*     */     //   284: iconst_0
/*     */     //   285: iload_2
/*     */     //   286: invokespecial fillBuffer : (IZZ)Z
/*     */     //   289: istore #12
/*     */     //   291: aload_0
/*     */     //   292: getfield bufferPos : I
/*     */     //   295: iload #14
/*     */     //   297: isub
/*     */     //   298: istore #15
/*     */     //   300: getstatic org/ms/donutduels/foliascheduler/mappingio/format/ColumnFileReader.$assertionsDisabled : Z
/*     */     //   303: ifne -> 319
/*     */     //   306: iload #15
/*     */     //   308: ifle -> 319
/*     */     //   311: new java/lang/AssertionError
/*     */     //   314: dup
/*     */     //   315: invokespecial <init> : ()V
/*     */     //   318: athrow
/*     */     //   319: iload #7
/*     */     //   321: iload #15
/*     */     //   323: iadd
/*     */     //   324: istore #7
/*     */     //   326: iload #8
/*     */     //   328: iflt -> 338
/*     */     //   331: iload #8
/*     */     //   333: iload #15
/*     */     //   335: iadd
/*     */     //   336: istore #8
/*     */     //   338: iload #12
/*     */     //   340: ifne -> 352
/*     */     //   343: aload_0
/*     */     //   344: getfield bufferPos : I
/*     */     //   347: istore #6
/*     */     //   349: goto -> 355
/*     */     //   352: goto -> 102
/*     */     //   355: aload #4
/*     */     //   357: ifnull -> 369
/*     */     //   360: iconst_1
/*     */     //   361: istore_2
/*     */     //   362: aload #4
/*     */     //   364: astore #14
/*     */     //   366: goto -> 434
/*     */     //   369: iload #7
/*     */     //   371: iload #6
/*     */     //   373: isub
/*     */     //   374: istore #15
/*     */     //   376: iload #15
/*     */     //   378: ifne -> 397
/*     */     //   381: iload #11
/*     */     //   383: ifeq -> 391
/*     */     //   386: ldc ''
/*     */     //   388: goto -> 392
/*     */     //   391: aconst_null
/*     */     //   392: astore #14
/*     */     //   394: goto -> 434
/*     */     //   397: iload #8
/*     */     //   399: iflt -> 421
/*     */     //   402: aload_0
/*     */     //   403: getfield buffer : [C
/*     */     //   406: iload #6
/*     */     //   408: iload #15
/*     */     //   410: invokestatic valueOf : ([CII)Ljava/lang/String;
/*     */     //   413: invokestatic unescape : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   416: astore #14
/*     */     //   418: goto -> 434
/*     */     //   421: aload_0
/*     */     //   422: getfield buffer : [C
/*     */     //   425: iload #6
/*     */     //   427: iload #15
/*     */     //   429: invokestatic valueOf : ([CII)Ljava/lang/String;
/*     */     //   432: astore #14
/*     */     //   434: iload_2
/*     */     //   435: ifeq -> 556
/*     */     //   438: iload #11
/*     */     //   440: ifeq -> 448
/*     */     //   443: aload_0
/*     */     //   444: iconst_0
/*     */     //   445: putfield bof : Z
/*     */     //   448: iload #10
/*     */     //   450: iconst_m1
/*     */     //   451: if_icmpeq -> 485
/*     */     //   454: aload_0
/*     */     //   455: iload #10
/*     */     //   457: putfield bufferPos : I
/*     */     //   460: iload #13
/*     */     //   462: ifeq -> 485
/*     */     //   465: aload_0
/*     */     //   466: iconst_1
/*     */     //   467: iconst_0
/*     */     //   468: iconst_0
/*     */     //   469: invokespecial fillBuffer : (IZZ)Z
/*     */     //   472: ifeq -> 485
/*     */     //   475: aload_0
/*     */     //   476: dup
/*     */     //   477: getfield bufferPos : I
/*     */     //   480: iconst_1
/*     */     //   481: iadd
/*     */     //   482: putfield bufferPos : I
/*     */     //   485: iload #12
/*     */     //   487: ifne -> 500
/*     */     //   490: aload_0
/*     */     //   491: aload_0
/*     */     //   492: iconst_1
/*     */     //   493: dup_x1
/*     */     //   494: putfield eol : Z
/*     */     //   497: putfield eof : Z
/*     */     //   500: aload_0
/*     */     //   501: getfield eol : Z
/*     */     //   504: ifeq -> 556
/*     */     //   507: aload_0
/*     */     //   508: getfield eof : Z
/*     */     //   511: ifne -> 556
/*     */     //   514: aload_0
/*     */     //   515: getfield buffer : [C
/*     */     //   518: aload_0
/*     */     //   519: getfield bufferPos : I
/*     */     //   522: caload
/*     */     //   523: bipush #13
/*     */     //   525: if_icmpne -> 532
/*     */     //   528: iconst_2
/*     */     //   529: goto -> 533
/*     */     //   532: iconst_1
/*     */     //   533: istore #15
/*     */     //   535: iload #7
/*     */     //   537: aload_0
/*     */     //   538: getfield bufferLimit : I
/*     */     //   541: iload #15
/*     */     //   543: isub
/*     */     //   544: if_icmplt -> 556
/*     */     //   547: aload_0
/*     */     //   548: iload #15
/*     */     //   550: iconst_0
/*     */     //   551: iconst_1
/*     */     //   552: invokespecial fillBuffer : (IZZ)Z
/*     */     //   555: pop
/*     */     //   556: aload #14
/*     */     //   558: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #104	-> 0
/*     */     //   #106	-> 20
/*     */     //   #109	-> 36
/*     */     //   #110	-> 55
/*     */     //   #114	-> 78
/*     */     //   #115	-> 84
/*     */     //   #116	-> 87
/*     */     //   #117	-> 90
/*     */     //   #118	-> 93
/*     */     //   #119	-> 96
/*     */     //   #120	-> 99
/*     */     //   #123	-> 102
/*     */     //   #124	-> 111
/*     */     //   #125	-> 120
/*     */     //   #126	-> 136
/*     */     //   #128	-> 139
/*     */     //   #129	-> 144
/*     */     //   #131	-> 170
/*     */     //   #135	-> 174
/*     */     //   #136	-> 197
/*     */     //   #137	-> 203
/*     */     //   #139	-> 207
/*     */     //   #140	-> 221
/*     */     //   #144	-> 229
/*     */     //   #145	-> 245
/*     */     //   #148	-> 251
/*     */     //   #149	-> 254
/*     */     //   #150	-> 257
/*     */     //   #154	-> 260
/*     */     //   #155	-> 266
/*     */     //   #156	-> 291
/*     */     //   #157	-> 300
/*     */     //   #158	-> 319
/*     */     //   #159	-> 326
/*     */     //   #161	-> 338
/*     */     //   #162	-> 343
/*     */     //   #163	-> 349
/*     */     //   #165	-> 352
/*     */     //   #169	-> 355
/*     */     //   #170	-> 360
/*     */     //   #171	-> 362
/*     */     //   #173	-> 369
/*     */     //   #175	-> 376
/*     */     //   #176	-> 381
/*     */     //   #177	-> 397
/*     */     //   #178	-> 402
/*     */     //   #180	-> 421
/*     */     //   #184	-> 434
/*     */     //   #185	-> 438
/*     */     //   #187	-> 448
/*     */     //   #188	-> 454
/*     */     //   #191	-> 460
/*     */     //   #192	-> 475
/*     */     //   #196	-> 485
/*     */     //   #198	-> 500
/*     */     //   #199	-> 514
/*     */     //   #201	-> 535
/*     */     //   #202	-> 547
/*     */     //   #207	-> 556
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   203	26	6	start	I
/*     */     //   120	137	14	c	C
/*     */     //   349	3	6	start	I
/*     */     //   266	86	14	oldStart	I
/*     */     //   300	52	15	posShift	I
/*     */     //   366	3	14	ret	Ljava/lang/String;
/*     */     //   394	3	14	ret	Ljava/lang/String;
/*     */     //   418	3	14	ret	Ljava/lang/String;
/*     */     //   376	58	15	contentLength	I
/*     */     //   535	21	15	charsToRead	I
/*     */     //   0	559	0	this	Lorg/ms/donutduels/foliascheduler/mappingio/format/ColumnFileReader;
/*     */     //   0	559	1	unescape	Z
/*     */     //   0	559	2	consume	Z
/*     */     //   0	559	3	stopAtNextCol	Z
/*     */     //   0	559	4	expected	Ljava/lang/String;
/*     */     //   36	523	5	expectedLength	I
/*     */     //   355	204	6	start	I
/*     */     //   84	475	7	end	I
/*     */     //   87	472	8	firstEscaped	I
/*     */     //   90	469	9	contentCharsRead	I
/*     */     //   93	466	10	modifiedBufferPos	I
/*     */     //   96	463	11	readAnything	Z
/*     */     //   99	460	12	filled	Z
/*     */     //   102	457	13	isColumnSeparator	Z
/*     */     //   434	125	14	ret	Ljava/lang/String;
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
/*     */   @Nullable
/*     */   public String nextCols(boolean unescape) throws IOException {
/* 217 */     return read(unescape, true, false, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String peekCols(boolean unescape) throws IOException {
/* 228 */     return read(unescape, false, false, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextIntCol() throws IOException {
/* 237 */     String str = nextCol(false);
/*     */     
/*     */     try {
/* 240 */       return (str != null) ? Integer.parseInt(str) : -1;
/* 241 */     } catch (NumberFormatException e) {
/* 242 */       throw new IOException("invalid number in line " + this.lineNumber + ": " + str);
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
/*     */   public boolean nextLine(int indent) throws IOException {
/*     */     label28: do {
/* 257 */       while (this.bufferPos < this.bufferLimit) {
/* 258 */         char c = this.buffer[this.bufferPos];
/*     */         
/* 260 */         if (c == '\n') {
/* 261 */           if (indent == 0) {
/* 262 */             if (!fillBuffer(2, false, true))
/*     */               break label28; 
/* 264 */             char next = this.buffer[this.bufferPos + 1];
/*     */             
/* 266 */             if (next == '\n' || next == '\r') {
/* 267 */               this.bufferPos++;
/* 268 */               this.lineNumber++;
/* 269 */               this.bof = false;
/*     */               
/*     */               continue;
/*     */             } 
/*     */           } 
/* 274 */           if (!fillBuffer(indent + 1, false, true)) return false;
/*     */           
/* 276 */           for (int i = 1; i <= indent; i++) {
/* 277 */             if (this.buffer[this.bufferPos + i] != this.indentationChar) return false;
/*     */           
/*     */           } 
/* 280 */           this.bufferPos += indent + 1;
/* 281 */           this.lineNumber++;
/* 282 */           this.bof = false;
/* 283 */           this.eol = false;
/*     */           
/* 285 */           return true;
/*     */         } 
/*     */         
/* 288 */         this.bufferPos++;
/* 289 */         this.bof = false;
/*     */       } 
/* 291 */     } while (fillBuffer(1, false, true));
/*     */     
/* 293 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasExtraIndents() throws IOException {
/* 297 */     return (fillBuffer(1, false, false) && this.buffer[this.bufferPos] == this.indentationChar);
/*     */   }
/*     */   
/*     */   public int getLineNumber() {
/* 301 */     return this.lineNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAtEol() {
/* 308 */     return this.eol;
/*     */   }
/*     */   
/*     */   public boolean isAtBof() {
/* 312 */     return this.bof;
/*     */   }
/*     */   
/*     */   public boolean isAtEof() {
/* 316 */     return this.eof;
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
/*     */   public int mark() {
/* 328 */     if (this.markIdx == 0 && this.bufferPos > 0) {
/* 329 */       int available = this.bufferLimit - this.bufferPos;
/* 330 */       System.arraycopy(this.buffer, this.bufferPos, this.buffer, 0, available);
/* 331 */       this.bufferPos = 0;
/* 332 */       this.bufferLimit = available;
/*     */     } 
/*     */     
/* 335 */     if (this.markIdx == this.markedBufferPositions.length) {
/* 336 */       this.markedBufferPositions = Arrays.copyOf(this.markedBufferPositions, this.markedBufferPositions.length * 2);
/* 337 */       this.markedLineNumbers = Arrays.copyOf(this.markedLineNumbers, this.markedLineNumbers.length * 2);
/* 338 */       this.markedBofs = Arrays.copyOf(this.markedBofs, this.markedBofs.length * 2);
/* 339 */       this.markedEols = Arrays.copyOf(this.markedEols, this.markedEols.length * 2);
/* 340 */       this.markedEofs = Arrays.copyOf(this.markedEofs, this.markedEofs.length * 2);
/*     */     } 
/*     */     
/* 343 */     this.markedBufferPositions[this.markIdx] = this.bufferPos;
/* 344 */     this.markedLineNumbers[this.markIdx] = this.lineNumber;
/* 345 */     this.markedBofs[this.markIdx] = this.bof;
/* 346 */     this.markedEols[this.markIdx] = this.eol;
/* 347 */     this.markedEofs[this.markIdx] = this.eof;
/*     */     
/* 349 */     return ++this.markIdx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void discardMark() {
/* 356 */     discardMark(this.markIdx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void discardMark(int index) {
/* 363 */     if (this.markIdx == 0) throw new IllegalStateException("no mark to discard"); 
/* 364 */     if (index < 1 || index > this.markIdx) throw new IllegalStateException("index out of bounds");
/*     */     
/* 366 */     for (int i = this.markIdx; i >= index; i--) {
/* 367 */       this.markedBufferPositions[i - 1] = 0;
/* 368 */       this.markedLineNumbers[i - 1] = 0;
/*     */     } 
/*     */     
/* 371 */     this.markIdx = index - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int reset() {
/* 381 */     reset(this.markIdx);
/* 382 */     return this.markIdx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(int indexToResetTo) {
/* 392 */     if (this.markIdx == 0) throw new IllegalStateException("no mark to reset to"); 
/* 393 */     if (indexToResetTo < -this.markIdx || indexToResetTo > this.markIdx) throw new IllegalStateException("index out of bounds");
/*     */     
/* 395 */     if (indexToResetTo < 0) indexToResetTo += this.markIdx; 
/* 396 */     int arrayIdx = (indexToResetTo == 0) ? indexToResetTo : (indexToResetTo - 1);
/*     */     
/* 398 */     this.bufferPos = this.markedBufferPositions[arrayIdx];
/* 399 */     this.lineNumber = this.markedLineNumbers[arrayIdx];
/* 400 */     this.bof = this.markedBofs[arrayIdx];
/* 401 */     this.eol = this.markedEols[arrayIdx];
/* 402 */     this.eof = this.markedEofs[arrayIdx];
/*     */     
/* 404 */     if (indexToResetTo == 0) discardMark(1); 
/* 405 */     this.markIdx = indexToResetTo;
/*     */   }
/*     */   
/*     */   private boolean fillBuffer(int count, boolean preventCompaction, boolean markEof) throws IOException {
/* 409 */     int available = this.bufferLimit - this.bufferPos;
/* 410 */     int req = count - available;
/* 411 */     if (req <= 0) return true;
/*     */     
/* 413 */     if (this.bufferPos + count > this.buffer.length) {
/* 414 */       if (this.markIdx > 0 || preventCompaction) {
/* 415 */         this.buffer = Arrays.copyOf(this.buffer, Math.max(this.bufferPos + count, this.buffer.length * 2));
/*     */       } else {
/* 417 */         if (count > this.buffer.length) {
/* 418 */           char[] newBuffer = new char[Math.max(count, this.buffer.length * 2)];
/* 419 */           System.arraycopy(this.buffer, this.bufferPos, newBuffer, 0, available);
/* 420 */           this.buffer = newBuffer;
/*     */         } else {
/* 422 */           System.arraycopy(this.buffer, this.bufferPos, this.buffer, 0, available);
/*     */         } 
/*     */         
/* 425 */         this.bufferPos = 0;
/* 426 */         this.bufferLimit = available;
/*     */       } 
/*     */     }
/*     */     
/* 430 */     int reqLimit = this.bufferLimit + req;
/*     */     
/*     */     do {
/* 433 */       int read = this.reader.read(this.buffer, this.bufferLimit, this.buffer.length - this.bufferLimit);
/*     */       
/* 435 */       if (read < 0) {
/* 436 */         if (markEof) this.eof = this.eol = true; 
/* 437 */         return false;
/*     */       } 
/*     */       
/* 440 */       this.bufferLimit += read;
/* 441 */     } while (this.bufferLimit < reqLimit);
/*     */     
/* 443 */     return true;
/*     */   }
/*     */   
/* 446 */   private static final String NO_MATCH = new String();
/*     */   private final Reader reader;
/*     */   private final char indentationChar;
/*     */   private final char columnSeparator;
/* 450 */   private char[] buffer = new char[16384];
/*     */   private int bufferPos;
/*     */   private int bufferLimit;
/* 453 */   private int lineNumber = 1;
/*     */   private boolean bof = true;
/*     */   private boolean eol;
/*     */   private boolean eof;
/* 457 */   private int markIdx = 0;
/* 458 */   private int[] markedBufferPositions = new int[3];
/* 459 */   private int[] markedLineNumbers = new int[3];
/* 460 */   private boolean[] markedBofs = new boolean[3];
/* 461 */   private boolean[] markedEols = new boolean[3];
/* 462 */   private boolean[] markedEofs = new boolean[3];
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\ColumnFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */