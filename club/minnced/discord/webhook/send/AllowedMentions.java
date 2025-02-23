/*     */ package club.minnced.discord.webhook.send;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AllowedMentions
/*     */   implements JSONString
/*     */ {
/*     */   private boolean parseRoles;
/*     */   private boolean parseUsers;
/*     */   private boolean parseEveryone;
/*     */   
/*     */   public static AllowedMentions all() {
/*  71 */     return (new AllowedMentions())
/*  72 */       .withParseEveryone(true)
/*  73 */       .withParseRoles(true)
/*  74 */       .withParseUsers(true);
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
/*     */   public static AllowedMentions none() {
/*  91 */     return (new AllowedMentions())
/*  92 */       .withParseEveryone(false)
/*  93 */       .withParseRoles(false)
/*  94 */       .withParseUsers(false);
/*     */   }
/*     */ 
/*     */   
/*  98 */   private final Set<String> users = new HashSet<>();
/*  99 */   private final Set<String> roles = new HashSet<>();
/*     */ 
/*     */ 
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
/*     */   public AllowedMentions withUsers(@NotNull String... userId) {
/* 113 */     Collections.addAll(this.users, userId);
/* 114 */     this.parseUsers = false;
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
/*     */   @NotNull
/*     */   public AllowedMentions withRoles(@NotNull String... roleId) {
/* 130 */     Collections.addAll(this.roles, roleId);
/* 131 */     this.parseRoles = false;
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
/*     */   @NotNull
/*     */   public AllowedMentions withUsers(@NotNull Collection<String> userId) {
/* 147 */     this.users.addAll(userId);
/* 148 */     this.parseUsers = false;
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
/*     */   
/*     */   @NotNull
/*     */   public AllowedMentions withRoles(@NotNull Collection<String> roleId) {
/* 164 */     this.roles.addAll(roleId);
/* 165 */     this.parseRoles = false;
/* 166 */     return this;
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
/*     */   public AllowedMentions withParseEveryone(boolean allowEveryoneMention) {
/* 180 */     this.parseEveryone = allowEveryoneMention;
/* 181 */     return this;
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
/*     */   public AllowedMentions withParseUsers(boolean allowParseUsers) {
/* 196 */     this.parseUsers = allowParseUsers;
/* 197 */     if (this.parseUsers)
/* 198 */       this.users.clear(); 
/* 199 */     return this;
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
/*     */   public AllowedMentions withParseRoles(boolean allowParseRoles) {
/* 214 */     this.parseRoles = allowParseRoles;
/* 215 */     if (this.parseRoles)
/* 216 */       this.roles.clear(); 
/* 217 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toJSONString() {
/* 222 */     JSONObject json = new JSONObject();
/* 223 */     json.put("parse", new JSONArray());
/*     */     
/* 225 */     if (!this.users.isEmpty()) {
/* 226 */       json.put("users", this.users);
/* 227 */     } else if (this.parseUsers) {
/* 228 */       json.accumulate("parse", "users");
/*     */     } 
/* 230 */     if (!this.roles.isEmpty()) {
/* 231 */       json.put("roles", this.roles);
/* 232 */     } else if (this.parseRoles) {
/* 233 */       json.accumulate("parse", "roles");
/*     */     } 
/* 235 */     if (this.parseEveryone)
/* 236 */       json.accumulate("parse", "everyone"); 
/* 237 */     return json.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\send\AllowedMentions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */