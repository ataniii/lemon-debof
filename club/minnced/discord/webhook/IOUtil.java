/*     */ package club.minnced.discord.webhook;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import okhttp3.MediaType;
/*     */ import okhttp3.RequestBody;
/*     */ import okhttp3.Response;
/*     */ import okhttp3.ResponseBody;
/*     */ import okio.BufferedSink;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.json.JSONObject;
/*     */ import org.json.JSONTokener;
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
/*     */ public class IOUtil
/*     */ {
/*  42 */   public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
/*     */   
/*  44 */   public static final MediaType OCTET = MediaType.parse("application/octet-stream; charset=utf-8");
/*     */   
/*  46 */   public static final byte[] EMPTY_BYTES = new byte[0];
/*     */   
/*  48 */   private static final CompletableFuture[] EMPTY_FUTURES = new CompletableFuture[0];
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
/*     */   @NotNull
/*     */   public static byte[] readAllBytes(@NotNull InputStream stream) throws IOException {
/*  63 */     int count = 0, pos = 0;
/*  64 */     byte[] output = EMPTY_BYTES;
/*  65 */     byte[] buf = new byte[1024];
/*  66 */     while ((count = stream.read(buf)) > 0) {
/*  67 */       if (pos + count >= output.length) {
/*  68 */         byte[] tmp = output;
/*  69 */         output = new byte[pos + count];
/*  70 */         System.arraycopy(tmp, 0, output, 0, tmp.length);
/*     */       } 
/*     */       
/*  73 */       for (int i = 0; i < count; i++) {
/*  74 */         output[pos++] = buf[i];
/*     */       }
/*     */     } 
/*  77 */     return output;
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
/*     */   @Nullable
/*     */   public static InputStream getBody(@NotNull Response req) throws IOException {
/*  93 */     List<String> encoding = req.headers("content-encoding");
/*  94 */     ResponseBody body = req.body();
/*  95 */     if (!encoding.isEmpty() && body != null) {
/*  96 */       return new GZIPInputStream(body.byteStream());
/*     */     }
/*  98 */     return (body != null) ? body.byteStream() : null;
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
/*     */   @NotNull
/*     */   public static JSONObject toJSON(@NotNull InputStream input) {
/* 114 */     return new JSONObject(new JSONTokener(input));
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
/*     */   @NotNull
/*     */   public static <T> CompletableFuture<List<T>> flipFuture(@NotNull List<CompletableFuture<T>> list) {
/* 129 */     List<T> result = new ArrayList<>(list.size());
/* 130 */     List<CompletableFuture<Void>> updatedStages = new ArrayList<>(list.size());
/*     */     
/* 132 */     list.stream()
/* 133 */       .map(it -> it.thenAccept(result::add))
/* 134 */       .forEach(updatedStages::add);
/*     */     
/* 136 */     CompletableFuture<Void> tracker = CompletableFuture.allOf((CompletableFuture<?>[])updatedStages.<CompletableFuture>toArray(EMPTY_FUTURES));
/* 137 */     CompletableFuture<List<T>> future = new CompletableFuture<>();
/*     */     
/* 139 */     tracker.thenRun(() -> future.complete(result)).exceptionally(e -> {
/*     */           future.completeExceptionally(e);
/*     */           
/*     */           return null;
/*     */         });
/* 144 */     return future;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface SilentSupplier<T>
/*     */   {
/*     */     @Nullable
/*     */     T get() throws Exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Lazy
/*     */   {
/*     */     private final IOUtil.SilentSupplier<?> supply;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Lazy(IOUtil.SilentSupplier<?> supply) {
/* 168 */       this.supply = supply;
/*     */     }
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public String toString() {
/*     */       try {
/* 175 */         return String.valueOf(this.supply.get());
/*     */       }
/* 177 */       catch (Exception e) {
/* 178 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class OctetBody
/*     */     extends RequestBody
/*     */   {
/*     */     private final byte[] data;
/*     */     
/*     */     public OctetBody(@NotNull byte[] data) {
/* 190 */       this.data = data;
/*     */     }
/*     */ 
/*     */     
/*     */     public MediaType contentType() {
/* 195 */       return IOUtil.OCTET;
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeTo(BufferedSink sink) throws IOException {
/* 200 */       sink.write(this.data);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\IOUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */