package com.khayyamapp.juveiran.api_service;

import com.khayyamapp.juveiran.data_model.Article;
import com.khayyamapp.juveiran.data_model.ArticleDetail;
import com.khayyamapp.juveiran.data_model.Catarticle;
import com.khayyamapp.juveiran.data_model.Comment;
import com.khayyamapp.juveiran.data_model.CreateArticle;
import com.khayyamapp.juveiran.data_model.GamesDataModel;
import com.khayyamapp.juveiran.data_model.GetVerifyCode;
import com.khayyamapp.juveiran.data_model.LeagueTableDataModel;
import com.khayyamapp.juveiran.data_model.Login;
import com.khayyamapp.juveiran.data_model.MatchesDataModel;
import com.khayyamapp.juveiran.data_model.PlayersListDataModel;
import com.khayyamapp.juveiran.data_model.Register;
import com.khayyamapp.juveiran.data_model.ResetPasswordResponse;
import com.khayyamapp.juveiran.data_model.SendComment;
import com.khayyamapp.juveiran.data_model.ShortArticle;
import com.khayyamapp.juveiran.data_model.ShortArticleComment;
import com.khayyamapp.juveiran.data_model.UpdateProfile;
import com.khayyamapp.juveiran.data_model.UserPosts;
import com.khayyamapp.juveiran.data_model.user_articles.UserArticles;
import com.khayyamapp.juveiran.data_model.user_commetns.UserComments;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("api/v1/shortarticles")
    Call<ArrayList<ShortArticle>> getShortArticles();

    @GET("api/v1/articles")
    Call<ArrayList<Article>> getArticles();


    @GET("api/v1/players")
    Call<ArrayList<PlayersListDataModel>> getPlayerList();


    @GET("api/v1/league_table")
    Call<ArrayList<LeagueTableDataModel>> getLeagueTable();

    @GET("api/v1/matches")
    Call<ArrayList<MatchesDataModel>> getMatchStats();

    @GET("api/v1/games")
    Call<ArrayList<GamesDataModel>> getGames();

    @GET("api/v1/comments/{id}")
    Call<ArrayList<Comment>> getComments(@Path("id") String id);

    @GET("api/v1/sacomments/{id}")
    Call<ArrayList<ShortArticleComment>> getShortArticleComments(@Path("id") String id);


    @GET("api/v1/article/{id}")
    Call<ArticleDetail> getArticleDetail(@Path("id") String id);

    @GET("api/v1/catarticles/{id}")
    Call<ArrayList<Catarticle>> getCatarticles(@Path("id") String id);


    //send comment for articles
    @FormUrlEncoded
    @Headers("Accept: application/json" +
            "Content-Type: application/json\n")
    @POST("api/v1/send_comment")
    Call<SendComment> sendComment(
            @Field("Article_ID") String articleId,
            @Field("Text") String text,
            @Header("Authorization") String auth
    );


    //send comment for short articles
    @FormUrlEncoded
    @Headers("Accept: application/json" +
            "Content-Type: application/json\n")
    @POST("api/v1/send_sacomment")
    Call<SendComment> sendSaComment(
            @Field("Article_ID") String articleId,
            @Field("Text") String text,
            @Header("Authorization") String auth
    );

    //send new article
    @Multipart
    @Headers("Accept: application/json")
    @POST("api/v1/send_post")
    Call<CreateArticle> createArticle(
            @Part("Title") RequestBody title,
            @Part("Text") RequestBody text,
            @Part MultipartBody.Part image,
            @Header("Authorization") String auth
    );


    //update user profile
    @Multipart
    @Headers("Accept: application/json")
    @POST("api/v1/update_profile")
    Call<UpdateProfile> updateProfile(
            @Part MultipartBody.Part file,
            @Part("Name") RequestBody name,
            @Header("Authorization") String auth);


    //login to userAccount
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("api/v1/signin")
    Call<Login> loginUser(
            @Field("Email") String email,
            @Field("Password") String password
    );


    //register for new account
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("api/v1/signup")
    Call<Register> registerUser(
            @Field("Email") String email,
            @Field("Password") String password,
            @Field("Name") String name
    );


    @GET("api/v1/user/comments")
    Call<UserComments> getUserComments(
            @Header("Authorization") String auth
    );

    @GET("api/v1/user/posts")
    Call<UserPosts> getUserPosts(
            @Header("Authorization") String auth
    );

    @GET("api/v1/user/sa_comments")
    Call<UserComments> getUserSaComments(
            @Header("Authorization") String auth
    );


    //forgot pass and reset pass apis
    @GET("api/v1/get_verify_code/{i}")
    Call<GetVerifyCode> getVerifyCode(
            @Path("i") String i
    );

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("api/v1/check_verify_code")
    Call<GetVerifyCode> checkVerifyCode(
            @Field("Code") String code,
            @Field("Email") String email
    );

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("api/v1/reset_pass")
    Call<ResetPasswordResponse> resetPassword(
            @Field("Code") String code,
            @Field("Email") String email,
            @Field("Password") String password
    );

    @GET("api/v1/user/posts")
    Call<UserArticles> getUserArticles(@Header("Authorization") String auth);

}
