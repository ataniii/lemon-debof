/*     */ package club.minnced.discord.webhook;
/*     */ 
/*     */ import club.minnced.discord.webhook.exception.HttpException;
/*     */ import club.minnced.discord.webhook.receive.EntityFactory;
/*     */ import club.minnced.discord.webhook.receive.ReadonlyMessage;
/*     */ import club.minnced.discord.webhook.send.AllowedMentions;
/*     */ import club.minnced.discord.webhook.send.WebhookEmbed;
/*     */ import club.minnced.discord.webhook.send.WebhookMessage;
/*     */ import club.minnced.discord.webhook.send.WebhookMessageBuilder;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.regex.Matcher;
/*     */ import javax.annotation.Nonnegative;
/*     */ import okhttp3.OkHttpClient;
/*     */ import okhttp3.RequestBody;
/*     */ import okhttp3.Response;
/*     */ import org.jetbrains.annotations.Async.Execute;
/*     */ import org.jetbrains.annotations.Async.Schedule;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ public class WebhookClient
/*     */   implements AutoCloseable
/*     */ {
/*     */   public static final String WEBHOOK_URL = "https://discord.com/api/v8/webhooks/%s/%s";
/*     */   public static final String USER_AGENT = "Webhook(https://github.com/MinnDevelopment/discord-webhooks, 0.5.6)";
/*  57 */   private static final Logger LOG = LoggerFactory.getLogger(WebhookClient.class);
/*     */   
/*     */   protected final String url;
/*     */   
/*     */   protected final long id;
/*     */   
/*     */   protected final OkHttpClient client;
/*     */   protected final ScheduledExecutorService pool;
/*     */   protected final Bucket bucket;
/*     */   protected final BlockingQueue<Request> queue;
/*     */   protected final boolean parseMessage;
/*     */   protected final AllowedMentions allowedMentions;
/*     */   protected long defaultTimeout;
/*     */   protected volatile boolean isQueued;
/*     */   protected boolean isShutdown;
/*     */   
/*     */   protected WebhookClient(long id, String token, boolean parseMessage, OkHttpClient client, ScheduledExecutorService pool, AllowedMentions mentions) {
/*  74 */     this.client = client;
/*  75 */     this.id = id;
/*  76 */     this.parseMessage = parseMessage;
/*  77 */     this.url = String.format("https://discord.com/api/v8/webhooks/%s/%s", new Object[] { Long.toUnsignedString(id), token });
/*  78 */     this.pool = pool;
/*  79 */     this.bucket = new Bucket();
/*  80 */     this.queue = new LinkedBlockingQueue<>();
/*  81 */     this.allowedMentions = mentions;
/*  82 */     this.isQueued = false;
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
/*     */   public static WebhookClient withId(long id, @NotNull String token) {
/*  99 */     Objects.requireNonNull(token, "Token");
/* 100 */     ScheduledExecutorService pool = WebhookClientBuilder.getDefaultPool(id, null, false);
/* 101 */     return new WebhookClient(id, token, true, new OkHttpClient(), pool, AllowedMentions.all());
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
/*     */   public static WebhookClient withUrl(@NotNull String url) {
/* 118 */     Objects.requireNonNull(url, "URL");
/* 119 */     Matcher matcher = WebhookClientBuilder.WEBHOOK_PATTERN.matcher(url);
/* 120 */     if (!matcher.matches()) {
/* 121 */       throw new IllegalArgumentException("Failed to parse webhook URL");
/*     */     }
/* 123 */     return withId(Long.parseUnsignedLong(matcher.group(1)), matcher.group(2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 132 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getUrl() {
/* 143 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWait() {
/* 153 */     return this.parseMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 162 */     return this.isShutdown;
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
/*     */   @NotNull
/*     */   public WebhookClient setTimeout(@Nonnegative long millis) {
/* 183 */     if (millis < 0L)
/* 184 */       throw new IllegalArgumentException("Cannot set a negative timeout"); 
/* 185 */     this.defaultTimeout = millis;
/* 186 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimeout() {
/* 196 */     return this.defaultTimeout;
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> send(@NotNull WebhookMessage message) {
/* 216 */     Objects.requireNonNull(message, "WebhookMessage");
/* 217 */     return execute(message.getBody());
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> send(@NotNull File file) {
/* 235 */     Objects.requireNonNull(file, "File");
/* 236 */     return send(file, file.getName());
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> send(@NotNull File file, @NotNull String fileName) {
/* 256 */     return send((new WebhookMessageBuilder())
/* 257 */         .setAllowedMentions(this.allowedMentions)
/* 258 */         .addFile(fileName, file)
/* 259 */         .build());
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> send(@NotNull byte[] data, @NotNull String fileName) {
/* 279 */     return send((new WebhookMessageBuilder())
/* 280 */         .setAllowedMentions(this.allowedMentions)
/* 281 */         .addFile(fileName, data)
/* 282 */         .build());
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> send(@NotNull InputStream data, @NotNull String fileName) {
/* 302 */     return send((new WebhookMessageBuilder())
/* 303 */         .setAllowedMentions(this.allowedMentions)
/* 304 */         .addFile(fileName, data)
/* 305 */         .build());
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> send(@NotNull WebhookEmbed first, @NotNull WebhookEmbed... embeds) {
/* 325 */     return send(WebhookMessage.embeds(first, embeds));
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> send(@NotNull Collection<WebhookEmbed> embeds) {
/* 343 */     return send(WebhookMessage.embeds(embeds));
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> send(@NotNull String content) {
/* 361 */     Objects.requireNonNull(content, "Content");
/* 362 */     content = content.trim();
/* 363 */     if (content.isEmpty())
/* 364 */       throw new IllegalArgumentException("Cannot send an empty message"); 
/* 365 */     if (content.length() > 2000)
/* 366 */       throw new IllegalArgumentException("Content may not exceed 2000 characters"); 
/* 367 */     return execute(newBody(newJson().put("content", content).toString()));
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull WebhookMessage message) {
/* 389 */     Objects.requireNonNull(message, "WebhookMessage");
/* 390 */     return execute(message.getBody(), Long.toUnsignedString(messageId), RequestType.EDIT);
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull WebhookEmbed first, @NotNull WebhookEmbed... embeds) {
/* 412 */     return edit(messageId, WebhookMessage.embeds(first, embeds));
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull Collection<WebhookEmbed> embeds) {
/* 432 */     return edit(messageId, WebhookMessage.embeds(embeds));
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull String content) {
/* 452 */     Objects.requireNonNull(content, "Content");
/* 453 */     content = content.trim();
/* 454 */     if (content.isEmpty())
/* 455 */       throw new IllegalArgumentException("Cannot send an empty message"); 
/* 456 */     if (content.length() > 2000)
/* 457 */       throw new IllegalArgumentException("Content may not exceed 2000 characters"); 
/* 458 */     return edit(messageId, (new WebhookMessageBuilder()).setContent(content).build());
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
/*     */   @NotNull
/*     */   public CompletableFuture<Void> delete(long messageId) {
/* 471 */     return execute(null, Long.toUnsignedString(messageId), RequestType.DELETE).thenApply(v -> null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JSONObject newJson() {
/* 477 */     JSONObject json = new JSONObject();
/* 478 */     json.put("allowed_mentions", this.allowedMentions);
/* 479 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 487 */     this.isShutdown = true;
/* 488 */     if (this.queue.isEmpty())
/* 489 */       this.pool.shutdown(); 
/*     */   }
/*     */   
/*     */   protected void checkShutdown() {
/* 493 */     if (this.isShutdown)
/* 494 */       throw new RejectedExecutionException("Cannot send to closed client!"); 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected static RequestBody newBody(String object) {
/* 499 */     return RequestBody.create(IOUtil.JSON, object);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected CompletableFuture<ReadonlyMessage> execute(RequestBody body, @Nullable String messageId, @NotNull RequestType type) {
/* 504 */     checkShutdown();
/* 505 */     String endpoint = this.url;
/* 506 */     if (type != RequestType.SEND)
/* 507 */       endpoint = endpoint + "/messages/" + messageId; 
/* 508 */     if (this.parseMessage)
/* 509 */       endpoint = endpoint + "?wait=true"; 
/* 510 */     return queueRequest(endpoint, type.method, body);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected CompletableFuture<ReadonlyMessage> execute(RequestBody body) {
/* 515 */     return execute(body, null, RequestType.SEND);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected static HttpException failure(Response response) throws IOException {
/* 520 */     InputStream stream = IOUtil.getBody(response);
/* 521 */     String responseBody = (stream == null) ? "" : new String(IOUtil.readAllBytes(stream));
/*     */     
/* 523 */     return new HttpException(response.code(), responseBody, response.headers());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected CompletableFuture<ReadonlyMessage> queueRequest(String url, String method, RequestBody body) {
/* 528 */     boolean wasQueued = this.isQueued;
/* 529 */     this.isQueued = true;
/* 530 */     CompletableFuture<ReadonlyMessage> callback = new CompletableFuture<>();
/* 531 */     Request req = new Request(callback, body, method, url);
/* 532 */     if (this.defaultTimeout > 0L)
/* 533 */       req.deadline = System.currentTimeMillis() + this.defaultTimeout; 
/* 534 */     enqueuePair(req);
/* 535 */     if (!wasQueued)
/* 536 */       backoffQueue(); 
/* 537 */     return callback;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected okhttp3.Request newRequest(Request request) {
/* 542 */     return (new okhttp3.Request.Builder())
/* 543 */       .url(request.url)
/* 544 */       .method(request.method, request.body)
/* 545 */       .header("accept-encoding", "gzip")
/* 546 */       .header("user-agent", "Webhook(https://github.com/MinnDevelopment/discord-webhooks, 0.5.6)")
/* 547 */       .build();
/*     */   }
/*     */   
/*     */   protected void backoffQueue() {
/* 551 */     long delay = this.bucket.retryAfter();
/* 552 */     if (delay > 0L)
/* 553 */       LOG.debug("Backing off queue for {}", Long.valueOf(delay)); 
/* 554 */     this.pool.schedule(this::drainQueue, delay, TimeUnit.MILLISECONDS);
/*     */   }
/*     */   
/*     */   protected synchronized void drainQueue() {
/* 558 */     boolean graceful = true;
/* 559 */     while (!this.queue.isEmpty()) {
/* 560 */       Request pair = this.queue.peek();
/* 561 */       graceful = executePair(pair);
/* 562 */       if (!graceful)
/*     */         break; 
/*     */     } 
/* 565 */     this.isQueued = !graceful;
/* 566 */     if (this.isShutdown && graceful)
/* 567 */       this.pool.shutdown(); 
/*     */   }
/*     */   
/*     */   private boolean enqueuePair(@Schedule Request pair) {
/* 571 */     return this.queue.add(pair);
/*     */   }
/*     */   
/*     */   private boolean executePair(@Execute Request req) {
/* 575 */     if (req.future.isDone()) {
/* 576 */       this.queue.poll();
/* 577 */       return true;
/* 578 */     }  if (req.deadline > 0L && req.deadline < System.currentTimeMillis()) {
/* 579 */       req.future.completeExceptionally(new TimeoutException());
/* 580 */       this.queue.poll();
/* 581 */       return true;
/*     */     } 
/*     */     
/* 584 */     okhttp3.Request request = newRequest(req);
/* 585 */     try (Response response = this.client.newCall(request).execute()) {
/* 586 */       this.bucket.update(response);
/* 587 */       if (response.code() == 429) {
/* 588 */         backoffQueue();
/* 589 */         return false;
/*     */       } 
/* 591 */       if (!response.isSuccessful()) {
/* 592 */         HttpException exception = failure(response);
/* 593 */         LOG.error("Sending a webhook message failed with non-OK http response", (Throwable)exception);
/* 594 */         (this.queue.poll()).future.completeExceptionally((Throwable)exception);
/* 595 */         return true;
/*     */       } 
/* 597 */       ReadonlyMessage message = null;
/* 598 */       if (this.parseMessage && !"DELETE".equals(req.method)) {
/* 599 */         InputStream body = IOUtil.getBody(response);
/* 600 */         JSONObject json = IOUtil.toJSON(body);
/* 601 */         message = EntityFactory.makeMessage(json);
/*     */       } 
/* 603 */       (this.queue.poll()).future.complete(message);
/* 604 */       if (this.bucket.isRateLimit()) {
/* 605 */         backoffQueue();
/* 606 */         return false;
/*     */       }
/*     */     
/* 609 */     } catch (JSONException|IOException e) {
/* 610 */       LOG.error("There was some error while sending a webhook message", e);
/* 611 */       (this.queue.poll()).future.completeExceptionally(e);
/*     */     } 
/* 613 */     return true;
/*     */   }
/*     */   
/*     */   enum RequestType {
/* 617 */     SEND("POST"), EDIT("PATCH"), DELETE("DELETE");
/*     */     
/*     */     private final String method;
/*     */     
/*     */     RequestType(String method) {
/* 622 */       this.method = method;
/*     */     }
/*     */     
/*     */     public String getMethod() {
/* 626 */       return this.method;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static final class Bucket {
/*     */     public static final int RATE_LIMIT_CODE = 429;
/*     */     public long resetTime;
/*     */     public int remainingUses;
/* 634 */     public int limit = Integer.MAX_VALUE;
/*     */     
/*     */     public synchronized boolean isRateLimit() {
/* 637 */       if (retryAfter() <= 0L)
/* 638 */         this.remainingUses = this.limit; 
/* 639 */       return (this.remainingUses <= 0);
/*     */     }
/*     */     
/*     */     public synchronized long retryAfter() {
/* 643 */       return this.resetTime - System.currentTimeMillis();
/*     */     }
/*     */     private synchronized void handleRatelimit(Response response, long current) throws IOException {
/*     */       long delay;
/* 647 */       String retryAfter = response.header("Retry-After");
/*     */       
/* 649 */       if (retryAfter == null) {
/* 650 */         InputStream stream = IOUtil.getBody(response);
/* 651 */         JSONObject body = IOUtil.toJSON(stream);
/* 652 */         delay = (long)Math.ceil(body.getDouble("retry_after")) * 1000L;
/*     */       } else {
/*     */         
/* 655 */         delay = Long.parseLong(retryAfter) * 1000L;
/*     */       } 
/* 657 */       WebhookClient.LOG.error("Encountered 429, retrying after {}", Long.valueOf(delay));
/* 658 */       this.resetTime = current + delay;
/*     */     }
/*     */     
/*     */     private synchronized void update0(Response response) throws IOException {
/* 662 */       long current = System.currentTimeMillis();
/* 663 */       boolean is429 = (response.code() == 429);
/* 664 */       if (is429) {
/* 665 */         handleRatelimit(response, current);
/*     */       }
/* 667 */       else if (!response.isSuccessful()) {
/* 668 */         WebhookClient.LOG.debug("Failed to update buckets due to unsuccessful response with code: {} and body: \n{}", 
/* 669 */             Integer.valueOf(response.code()), new IOUtil.Lazy(() -> new String(IOUtil.readAllBytes(IOUtil.getBody(response)))));
/*     */         return;
/*     */       } 
/* 672 */       this.remainingUses = Integer.parseInt(response.header("X-RateLimit-Remaining"));
/* 673 */       this.limit = Integer.parseInt(response.header("X-RateLimit-Limit"));
/*     */       
/* 675 */       if (!is429) {
/* 676 */         long reset = (long)Math.ceil(Double.parseDouble(response.header("X-RateLimit-Reset-After")));
/* 677 */         long delay = reset * 1000L;
/* 678 */         this.resetTime = current + delay;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void update(Response response) {
/*     */       try {
/* 684 */         update0(response);
/*     */       }
/* 686 */       catch (Exception ex) {
/* 687 */         WebhookClient.LOG.error("Could not read http response", ex);
/*     */       } 
/*     */     } }
/*     */   
/*     */   private static final class Request {
/*     */     private final CompletableFuture<ReadonlyMessage> future;
/*     */     private final RequestBody body;
/*     */     private final String method;
/*     */     private final String url;
/*     */     private long deadline;
/*     */     
/*     */     public Request(CompletableFuture<ReadonlyMessage> future, RequestBody body, String method, String url) {
/* 699 */       this.future = future;
/* 700 */       this.body = body;
/* 701 */       this.method = method;
/* 702 */       this.url = url;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\WebhookClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */