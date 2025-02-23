/*     */ package club.minnced.discord.webhook;
/*     */ 
/*     */ import club.minnced.discord.webhook.receive.ReadonlyMessage;
/*     */ import club.minnced.discord.webhook.send.AllowedMentions;
/*     */ import club.minnced.discord.webhook.send.WebhookEmbed;
/*     */ import club.minnced.discord.webhook.send.WebhookMessage;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collectors;
/*     */ import okhttp3.OkHttpClient;
/*     */ import okhttp3.RequestBody;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ public class WebhookCluster
/*     */   implements AutoCloseable
/*     */ {
/*     */   protected final List<WebhookClient> webhooks;
/*     */   protected OkHttpClient defaultHttpClient;
/*     */   protected ScheduledExecutorService defaultPool;
/*     */   protected ThreadFactory threadFactory;
/*  54 */   protected AllowedMentions allowedMentions = AllowedMentions.all();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isDaemon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebhookCluster(@NotNull Collection<? extends WebhookClient> initialClients) {
/*  67 */     Objects.requireNonNull(initialClients, "List");
/*  68 */     this.webhooks = new ArrayList<>(initialClients.size());
/*  69 */     for (WebhookClient client : initialClients) {
/*  70 */       addWebhooks(new WebhookClient[] { client });
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
/*     */ 
/*     */   
/*     */   public WebhookCluster(int initialCapacity) {
/*  88 */     this.webhooks = new ArrayList<>(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebhookCluster() {
/*  96 */     this.webhooks = new ArrayList<>();
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
/*     */   public WebhookCluster setDefaultHttpClient(@Nullable OkHttpClient defaultHttpClient) {
/* 114 */     this.defaultHttpClient = defaultHttpClient;
/* 115 */     return this;
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
/*     */   public WebhookCluster setDefaultExecutorService(@Nullable ScheduledExecutorService executorService) {
/* 131 */     this.defaultPool = executorService;
/* 132 */     return this;
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
/*     */   public WebhookCluster setDefaultThreadFactory(@Nullable ThreadFactory factory) {
/* 148 */     this.threadFactory = factory;
/* 149 */     return this;
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
/*     */   @NotNull
/*     */   public WebhookCluster setAllowedMentions(@Nullable AllowedMentions allowedMentions) {
/* 163 */     this.allowedMentions = (allowedMentions == null) ? AllowedMentions.all() : allowedMentions;
/* 164 */     return this;
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
/*     */   public WebhookCluster setDefaultDaemon(boolean isDaemon) {
/* 179 */     this.isDaemon = isDaemon;
/* 180 */     return this;
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
/*     */   public WebhookCluster buildWebhook(long id, @NotNull String token) {
/* 201 */     this.webhooks.add(newBuilder(id, token).build());
/* 202 */     return this;
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
/*     */   public WebhookClientBuilder newBuilder(long id, @NotNull String token) {
/* 222 */     WebhookClientBuilder builder = new WebhookClientBuilder(id, token);
/* 223 */     builder.setExecutorService(this.defaultPool)
/* 224 */       .setHttpClient(this.defaultHttpClient)
/* 225 */       .setThreadFactory(this.threadFactory)
/* 226 */       .setAllowedMentions(this.allowedMentions)
/* 227 */       .setDaemon(this.isDaemon);
/* 228 */     return builder;
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
/*     */   public WebhookCluster addWebhooks(@NotNull WebhookClient... clients) {
/* 246 */     Objects.requireNonNull(clients, "Clients");
/* 247 */     for (WebhookClient client : clients) {
/* 248 */       Objects.requireNonNull(client, "Client");
/* 249 */       if (client.isShutdown)
/* 250 */         throw new IllegalArgumentException("One of the provided WebhookClients has been closed already!"); 
/* 251 */       this.webhooks.add(client);
/*     */     } 
/* 253 */     return this;
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
/*     */   public WebhookCluster addWebhooks(@NotNull Collection<WebhookClient> clients) {
/* 271 */     Objects.requireNonNull(clients, "Clients");
/* 272 */     for (WebhookClient client : clients) {
/* 273 */       Objects.requireNonNull(client, "Client");
/* 274 */       if (client.isShutdown)
/* 275 */         throw new IllegalArgumentException("One of the provided WebhookClients has been closed already!"); 
/* 276 */       this.webhooks.add(client);
/*     */     } 
/* 278 */     return this;
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
/*     */   public WebhookCluster removeWebhooks(@NotNull WebhookClient... clients) {
/* 294 */     Objects.requireNonNull(clients, "Clients");
/* 295 */     this.webhooks.removeAll(Arrays.asList((Object[])clients));
/* 296 */     return this;
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
/*     */   public WebhookCluster removeWebhooks(@NotNull Collection<WebhookClient> clients) {
/* 312 */     Objects.requireNonNull(clients, "Clients");
/* 313 */     this.webhooks.removeAll(clients);
/* 314 */     return this;
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
/*     */   public List<WebhookClient> removeIf(@NotNull Predicate<WebhookClient> predicate) {
/* 330 */     Objects.requireNonNull(predicate, "Predicate");
/* 331 */     List<WebhookClient> clients = new ArrayList<>();
/* 332 */     for (WebhookClient client : this.webhooks) {
/* 333 */       if (predicate.test(client))
/* 334 */         clients.add(client); 
/*     */     } 
/* 336 */     removeWebhooks(clients);
/* 337 */     return clients;
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
/*     */   public List<WebhookClient> closeIf(@NotNull Predicate<WebhookClient> predicate) {
/* 353 */     Objects.requireNonNull(predicate, "Filter");
/* 354 */     List<WebhookClient> clients = new ArrayList<>();
/* 355 */     for (WebhookClient client : this.webhooks) {
/* 356 */       if (predicate.test(client))
/* 357 */         clients.add(client); 
/*     */     } 
/* 359 */     removeWebhooks(clients);
/* 360 */     clients.forEach(WebhookClient::close);
/* 361 */     return clients;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<WebhookClient> getWebhooks() {
/* 371 */     return Collections.unmodifiableList(new ArrayList<>(this.webhooks));
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
/*     */   public List<CompletableFuture<ReadonlyMessage>> multicast(@NotNull Predicate<WebhookClient> filter, @NotNull WebhookMessage message) {
/* 393 */     Objects.requireNonNull(filter, "Filter");
/* 394 */     Objects.requireNonNull(message, "Message");
/* 395 */     RequestBody body = message.getBody();
/* 396 */     List<CompletableFuture<ReadonlyMessage>> callbacks = new ArrayList<>();
/* 397 */     for (WebhookClient client : this.webhooks) {
/* 398 */       if (filter.test(client))
/* 399 */         callbacks.add(client.execute(body)); 
/*     */     } 
/* 401 */     return callbacks;
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
/*     */   public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull WebhookMessage message) {
/* 419 */     Objects.requireNonNull(message, "Message");
/* 420 */     RequestBody body = message.getBody();
/* 421 */     List<CompletableFuture<ReadonlyMessage>> callbacks = new ArrayList<>(this.webhooks.size());
/* 422 */     for (WebhookClient webhook : this.webhooks) {
/* 423 */       callbacks.add(webhook.execute(body));
/* 424 */       if (message.isFile())
/* 425 */         body = message.getBody(); 
/*     */     } 
/* 427 */     return callbacks;
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
/*     */   public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull WebhookEmbed first, @NotNull WebhookEmbed... embeds) {
/* 445 */     List<WebhookEmbed> list = new ArrayList<>(embeds.length + 2);
/* 446 */     list.add(first);
/* 447 */     Collections.addAll(list, embeds);
/* 448 */     return broadcast(list);
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
/*     */   public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull Collection<WebhookEmbed> embeds) {
/* 464 */     return (List<CompletableFuture<ReadonlyMessage>>)this.webhooks.stream()
/* 465 */       .map(w -> w.send(embeds))
/* 466 */       .collect(Collectors.toList());
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
/*     */   public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull String content) {
/* 482 */     Objects.requireNonNull(content, "Content");
/* 483 */     if (content.length() > 2000)
/* 484 */       throw new IllegalArgumentException("Content may not exceed 2000 characters!"); 
/* 485 */     return (List<CompletableFuture<ReadonlyMessage>>)this.webhooks.stream()
/* 486 */       .map(w -> w.send(content))
/* 487 */       .collect(Collectors.toList());
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
/*     */   public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull File file) {
/* 505 */     Objects.requireNonNull(file, "File");
/* 506 */     return broadcast(file.getName(), file);
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
/*     */   public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull String fileName, @NotNull File file) {
/* 526 */     Objects.requireNonNull(file, "File");
/* 527 */     if (file.length() > 10L)
/* 528 */       throw new IllegalArgumentException("Provided File exceeds the maximum size of 8MB!"); 
/*     */     try {
/* 530 */       return broadcast(fileName, new FileInputStream(file));
/* 531 */     } catch (FileNotFoundException e) {
/* 532 */       throw new UncheckedIOException(e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull String fileName, @NotNull InputStream data) {
/*     */     try {
/* 554 */       return broadcast(fileName, IOUtil.readAllBytes(data));
/* 555 */     } catch (IOException e) {
/* 556 */       throw new UncheckedIOException(e);
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
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull String fileName, @NotNull byte[] data) {
/* 575 */     Objects.requireNonNull(data, "Data");
/* 576 */     if (data.length > 10)
/* 577 */       throw new IllegalArgumentException("Provided data exceeds the maximum size of 8MB!"); 
/* 578 */     return (List<CompletableFuture<ReadonlyMessage>>)this.webhooks.stream()
/* 579 */       .map(w -> w.send(data, fileName))
/* 580 */       .collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 590 */     this.webhooks.forEach(WebhookClient::close);
/* 591 */     this.webhooks.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\WebhookCluster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */