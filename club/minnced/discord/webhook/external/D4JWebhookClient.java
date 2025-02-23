/*    */ package club.minnced.discord.webhook.external;
/*    */ import club.minnced.discord.webhook.WebhookClient;
/*    */ import club.minnced.discord.webhook.WebhookClientBuilder;
/*    */ import club.minnced.discord.webhook.receive.ReadonlyMessage;
/*    */ import club.minnced.discord.webhook.send.AllowedMentions;
/*    */ import club.minnced.discord.webhook.send.WebhookMessage;
/*    */ import club.minnced.discord.webhook.send.WebhookMessageBuilder;
/*    */ import discord4j.core.object.entity.Webhook;
/*    */ import discord4j.core.spec.MessageCreateSpec;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.ScheduledExecutorService;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.CheckReturnValue;
/*    */ import okhttp3.OkHttpClient;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import reactor.core.publisher.Mono;
/*    */ 
/*    */ public class D4JWebhookClient extends WebhookClient {
/*    */   public D4JWebhookClient(long id, String token, boolean parseMessage, OkHttpClient client, ScheduledExecutorService pool, AllowedMentions mentions) {
/* 20 */     super(id, token, parseMessage, client, pool, mentions);
/*    */   }
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
/*    */   @NotNull
/*    */   public static WebhookClient from(@NotNull Webhook webhook) {
/* 36 */     return WebhookClientBuilder.fromD4J(webhook).build();
/*    */   }
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
/*    */   @CheckReturnValue
/*    */   @NotNull
/*    */   public Mono<ReadonlyMessage> send(@NotNull Consumer<? super MessageCreateSpec> callback) {
/* 56 */     WebhookMessage message = WebhookMessageBuilder.fromD4J(callback).build();
/* 57 */     return Mono.fromFuture(() -> send(message));
/*    */   }
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
/*    */   @CheckReturnValue
/*    */   @NotNull
/*    */   public Mono<ReadonlyMessage> edit(long messageId, @NotNull Consumer<? super MessageCreateSpec> callback) {
/* 79 */     WebhookMessage message = WebhookMessageBuilder.fromD4J(callback).build();
/* 80 */     return Mono.fromFuture(() -> edit(messageId, message));
/*    */   }
/*    */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\external\D4JWebhookClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */