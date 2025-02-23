/*    */ package club.minnced.discord.webhook.exception;
/*    */ 
/*    */ import okhttp3.Headers;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ public class HttpException
/*    */   extends RuntimeException
/*    */ {
/*    */   private final int code;
/*    */   private final String body;
/*    */   private final Headers headers;
/*    */   
/*    */   public HttpException(int code, @NotNull String body, @NotNull Headers headers) {
/* 33 */     super("Request returned failure " + code + ": " + body);
/* 34 */     this.body = body;
/* 35 */     this.code = code;
/* 36 */     this.headers = headers;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCode() {
/* 45 */     return this.code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String getBody() {
/* 55 */     return this.body;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Headers getHeaders() {
/* 65 */     return this.headers;
/*    */   }
/*    */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\exception\HttpException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */