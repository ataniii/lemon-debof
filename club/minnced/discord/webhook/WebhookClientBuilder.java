/*     */ package club.minnced.discord.webhook;
/*     */ 
/*     */ import club.minnced.discord.webhook.external.D4JWebhookClient;
/*     */ import club.minnced.discord.webhook.external.JDAWebhookClient;
/*     */ import club.minnced.discord.webhook.external.JavacordWebhookClient;
/*     */ import club.minnced.discord.webhook.send.AllowedMentions;
/*     */ import discord4j.core.object.entity.Webhook;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.dv8tion.jda.api.entities.Webhook;
/*     */ import okhttp3.OkHttpClient;
/*     */ import org.javacord.api.entity.webhook.Webhook;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebhookClientBuilder
/*     */ {
/*  45 */   public static final Pattern WEBHOOK_PATTERN = Pattern.compile("(?:https?://)?(?:\\w+\\.)?discord(?:app)?\\.com/api(?:/v\\d+)?/webhooks/(\\d+)/([\\w-]+)(?:/(?:\\w+)?)?");
/*     */   
/*     */   protected final long id;
/*     */   protected final String token;
/*     */   protected ScheduledExecutorService pool;
/*     */   protected OkHttpClient client;
/*     */   protected ThreadFactory threadFactory;
/*  52 */   protected AllowedMentions allowedMentions = AllowedMentions.all();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isDaemon;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean parseMessage = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebhookClientBuilder(long id, @NotNull String token) {
/*  68 */     Objects.requireNonNull(token, "Token");
/*  69 */     this.id = id;
/*  70 */     this.token = token;
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
/*     */   public WebhookClientBuilder(@NotNull String url) {
/*  86 */     Objects.requireNonNull(url, "Url");
/*  87 */     Matcher matcher = WEBHOOK_PATTERN.matcher(url);
/*  88 */     if (!matcher.matches()) {
/*  89 */       throw new IllegalArgumentException("Failed to parse webhook URL");
/*     */     }
/*     */     
/*  92 */     this.id = Long.parseUnsignedLong(matcher.group(1));
/*  93 */     this.token = matcher.group(2);
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
/*     */   public static WebhookClientBuilder fromJDA(@NotNull Webhook webhook) {
/* 113 */     Objects.requireNonNull(webhook, "Webhook");
/* 114 */     return new WebhookClientBuilder(webhook.getIdLong(), Objects.<String>requireNonNull(webhook.getToken(), "Webhook Token"));
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
/*     */   public static WebhookClientBuilder fromD4J(@NotNull Webhook webhook) {
/* 130 */     Objects.requireNonNull(webhook, "Webhook");
/* 131 */     String token = webhook.getToken();
/* 132 */     Objects.requireNonNull(token, "Webhook Token");
/* 133 */     if (token.isEmpty())
/* 134 */       throw new NullPointerException("Webhook Token"); 
/* 135 */     return new WebhookClientBuilder(webhook.getId().asLong(), token);
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
/*     */   public static WebhookClientBuilder fromJavacord(@NotNull Webhook webhook) {
/* 151 */     Objects.requireNonNull(webhook, "Webhook");
/* 152 */     return new WebhookClientBuilder(webhook.getId(), (String)webhook.getToken().orElseThrow(NullPointerException::new));
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
/*     */   public WebhookClientBuilder setExecutorService(@Nullable ScheduledExecutorService executorService) {
/* 168 */     this.pool = executorService;
/* 169 */     return this;
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
/*     */   public WebhookClientBuilder setHttpClient(@Nullable OkHttpClient client) {
/* 184 */     this.client = client;
/* 185 */     return this;
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
/*     */   public WebhookClientBuilder setThreadFactory(@Nullable ThreadFactory factory) {
/* 200 */     this.threadFactory = factory;
/* 201 */     return this;
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
/*     */   public WebhookClientBuilder setAllowedMentions(@Nullable AllowedMentions mentions) {
/* 215 */     this.allowedMentions = (mentions == null) ? AllowedMentions.all() : mentions;
/* 216 */     return this;
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
/*     */   public WebhookClientBuilder setDaemon(boolean isDaemon) {
/* 231 */     this.isDaemon = isDaemon;
/* 232 */     return this;
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
/*     */   public WebhookClientBuilder setWait(boolean waitForMessage) {
/* 247 */     this.parseMessage = waitForMessage;
/* 248 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WebhookClient build() {
/* 259 */     OkHttpClient client = (this.client == null) ? new OkHttpClient() : this.client;
/* 260 */     ScheduledExecutorService pool = (this.pool != null) ? this.pool : getDefaultPool(this.id, this.threadFactory, this.isDaemon);
/* 261 */     return new WebhookClient(this.id, this.token, this.parseMessage, client, pool, this.allowedMentions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public JDAWebhookClient buildJDA() {
/* 272 */     OkHttpClient client = (this.client == null) ? new OkHttpClient() : this.client;
/* 273 */     ScheduledExecutorService pool = (this.pool != null) ? this.pool : getDefaultPool(this.id, this.threadFactory, this.isDaemon);
/* 274 */     return new JDAWebhookClient(this.id, this.token, this.parseMessage, client, pool, this.allowedMentions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public D4JWebhookClient buildD4J() {
/* 285 */     OkHttpClient client = (this.client == null) ? new OkHttpClient() : this.client;
/* 286 */     ScheduledExecutorService pool = (this.pool != null) ? this.pool : getDefaultPool(this.id, this.threadFactory, this.isDaemon);
/* 287 */     return new D4JWebhookClient(this.id, this.token, this.parseMessage, client, pool, this.allowedMentions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public JavacordWebhookClient buildJavacord() {
/* 298 */     OkHttpClient client = (this.client == null) ? new OkHttpClient() : this.client;
/* 299 */     ScheduledExecutorService pool = (this.pool != null) ? this.pool : getDefaultPool(this.id, this.threadFactory, this.isDaemon);
/* 300 */     return new JavacordWebhookClient(this.id, this.token, this.parseMessage, client, pool, this.allowedMentions);
/*     */   }
/*     */   
/*     */   protected static ScheduledExecutorService getDefaultPool(long id, ThreadFactory factory, boolean isDaemon) {
/* 304 */     return Executors.newSingleThreadScheduledExecutor((factory == null) ? new DefaultWebhookThreadFactory(id, isDaemon) : factory);
/*     */   }
/*     */   
/*     */   private static final class DefaultWebhookThreadFactory implements ThreadFactory {
/*     */     private final long id;
/*     */     private final boolean isDaemon;
/*     */     
/*     */     public DefaultWebhookThreadFactory(long id, boolean isDaemon) {
/* 312 */       this.id = id;
/* 313 */       this.isDaemon = isDaemon;
/*     */     }
/*     */ 
/*     */     
/*     */     public Thread newThread(Runnable r) {
/* 318 */       Thread thread = new Thread(r, "Webhook-RateLimit Thread WebhookID: " + this.id);
/* 319 */       thread.setDaemon(this.isDaemon);
/* 320 */       return thread;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\WebhookClientBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */