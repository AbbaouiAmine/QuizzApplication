package com.amineabbaoui.quizapp_o2.Rest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface API {

   String adresse="http://192.168.1.104:8085/";
   @GET("posts")
 Call<List<Post>> getPosts();

 @GET("verifyphoto")
 Call<Boolean> getVerifyFace();

    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET
    Call<List<Comment>> getComments(@Url String url);

    @POST("posts")
    Call<Post> createPost(@Body Post post);
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String, String> fields);

    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);

    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);


    /*=============================================================*/
    @GET("properties")
    Call<List<Produit>> getProduits();
    @GET("produits/{id}")
    Call<Produit> getProduit(@Path("id") int produitId);
    @POST("produits")
    Call<Produit> createProduit(@Body Produit produit);
    @PUT("produits/{id}")
    Call<Produit> putProduit(@Path("id") Integer id, @Body Produit produit);
    @DELETE("produits/{id}")
    Call<Void> deleteProduit(@Path("id") int id);
    /*========================================================================*/
    @POST("utilisateurs")
    Call<Utilisateur> createUser(@Body Utilisateur user);
    @GET("listutilisateurs")
    Call<List<Utilisateur>> getUtilisateurs();
    /*========================================================================*/
    @GET("listquestions")
    Call<List<Question_>> getQuestions_();
}
