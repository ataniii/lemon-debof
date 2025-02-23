/*     */ package club.minnced.discord.webhook.external;
/*     */ import club.minnced.discord.webhook.WebhookClient;
/*     */ import club.minnced.discord.webhook.receive.ReadonlyMessage;
/*     */ import club.minnced.discord.webhook.send.AllowedMentions;
/*     */ import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
/*     */ import club.minnced.discord.webhook.send.WebhookMessageBuilder;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import okhttp3.OkHttpClient;
/*     */ import org.javacord.api.entity.message.Message;
/*     */ import org.javacord.api.entity.message.embed.Embed;
/*     */ import org.javacord.api.entity.webhook.Webhook;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class JavacordWebhookClient extends WebhookClient {
/*     */   public JavacordWebhookClient(long id, String token, boolean parseMessage, OkHttpClient client, ScheduledExecutorService pool, AllowedMentions mentions) {
/*  17 */     super(id, token, parseMessage, client, pool, mentions);
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
/*     */   public static WebhookClient from(@NotNull Webhook webhook) {
/*  33 */     return WebhookClientBuilder.fromJavacord(webhook).build();
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> send(@NotNull Message message) {
/*  52 */     return send(WebhookMessageBuilder.fromJavacord(message).build());
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
/*     */   @NotNull
/*     */   public CompletableFuture<ReadonlyMessage> send(@NotNull Embed embed) {
/*  71 */     return send(WebhookEmbedBuilder.fromJavacord(embed).build(), new club.minnced.discord.webhook.send.WebhookEmbed[0]);
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
/*     */   public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull Message message) {
/*  92 */     return edit(messageId, WebhookMessageBuilder.fromJavacord(message).build());
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
/*     */   public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull Embed embed) {
/* 113 */     return edit(messageId, WebhookEmbedBuilder.fromJavacord(embed).build(), new club.minnced.discord.webhook.send.WebhookEmbed[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\external\JavacordWebhookClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */