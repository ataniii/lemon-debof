/*     */ package club.minnced.discord.webhook.receive;
/*     */ 
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.json.JSONObject;
/*     */ import org.json.JSONPropertyName;
/*     */ import org.json.JSONString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReadonlyUser
/*     */   implements JSONString
/*     */ {
/*     */   private final long id;
/*     */   private final short discriminator;
/*     */   private final boolean bot;
/*     */   private final String name;
/*     */   private final String avatar;
/*     */   
/*     */   public ReadonlyUser(long id, short discriminator, boolean bot, @NotNull String name, @Nullable String avatar) {
/*  36 */     this.id = id;
/*  37 */     this.discriminator = discriminator;
/*  38 */     this.bot = bot;
/*  39 */     this.name = name;
/*  40 */     this.avatar = avatar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/*  49 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDiscriminator() {
/*  59 */     return String.format("%04d", new Object[] { Short.valueOf(this.discriminator) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBot() {
/*  68 */     return this.bot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JSONPropertyName("username")
/*     */   @NotNull
/*     */   public String getName() {
/*  79 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JSONPropertyName("avatar_id")
/*     */   @Nullable
/*     */   public String getAvatarId() {
/*  90 */     return this.avatar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     return toJSONString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toJSONString() {
/* 105 */     return (new JSONObject(this)).toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\receive\ReadonlyUser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */