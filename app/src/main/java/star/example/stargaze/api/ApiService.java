package star.example.stargaze.api;



import star.example.stargaze.models.PartialPayResp;
import star.example.stargaze.models.request.ContactBody;
import star.example.stargaze.models.request.EventFilterBody;
import star.example.stargaze.models.request.EventOrderBody;
import star.example.stargaze.models.request.LiveUserCommentBody;
import star.example.stargaze.models.request.Otp;
import star.example.stargaze.models.request.Password;
import star.example.stargaze.models.request.Phone;
import star.example.stargaze.models.request.ProfileEdit;
import star.example.stargaze.models.request.RazorCaptureBody;
import star.example.stargaze.models.request.RazorOrderBody;
import star.example.stargaze.models.request.RazorSaveDataBody;
import star.example.stargaze.models.request.RegisterOTP;
import star.example.stargaze.models.request.SearchReqBody;
import star.example.stargaze.models.request.User;
import star.example.stargaze.models.request.UserCredential;
import star.example.stargaze.models.request.WalletAmountBody;
import star.example.stargaze.models.request.WalletSaveBody;
import star.example.stargaze.models.response.AddWalletDataResp;
import star.example.stargaze.models.response.ApplyCouponResp;
import star.example.stargaze.models.response.Banner;
import star.example.stargaze.models.response.BlogResp;
import star.example.stargaze.models.response.CelebrityResp;
import star.example.stargaze.models.response.CommentResp;
import star.example.stargaze.models.response.EventPaidCheckResp;
import star.example.stargaze.models.response.EventResp;
import star.example.stargaze.models.response.FAQResp;
import star.example.stargaze.models.response.LoginRespBean;
import star.example.stargaze.models.response.OrderHistoryResp;
import star.example.stargaze.models.response.OrderResp;
import star.example.stargaze.models.response.CapturePaymentSuccessResp;
import star.example.stargaze.models.response.PaymentDataSaveResp;
import star.example.stargaze.models.response.PrivacyResp;
import star.example.stargaze.models.response.ProfileImageResp;
import star.example.stargaze.models.response.ProfileUpdateResp;
import star.example.stargaze.models.response.RazorOrderResp;
import star.example.stargaze.models.response.ReferralResp;
import star.example.stargaze.models.response.WalletCreateResp;
import star.example.stargaze.models.response.WalletResp;
import star.example.stargaze.models.response.WalletVerifyResp;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @POST("user/register")
    Call<ResponseBody> createUser(@Body User user);

    @POST("user/login")
    Call<LoginRespBean> doLogin(@Body UserCredential credential);

    @POST("user/register/otp/verify")
    Call<ResponseBody> registerOtpVerify(@Body RegisterOTP otp);

    @POST("user/register/otp/resend")
    Call<ResponseBody> resendRegisterOtp(@Body Phone phone);

    @POST("user/otp")
    Call<ResponseBody> forgotPass(@Body Phone phone);

    @POST("user/otp/resend")
    Call<ResponseBody>reSendOtp(@Body Phone phone);

    @POST("user/otp/verify")
    Call<ResponseBody>verifyOtp(@Body Otp otp);

    @PUT("user/changepassword")
    Call<ResponseBody> changePassword(@Body Password password);

    @PUT("user")
    Call<ProfileUpdateResp> updateProfile(@Body ProfileEdit profile);

    @POST("event")
    Call<EventResp> getEvent(@Query("page") int page, @Query("limit") int limit, @Body EventFilterBody filterBody);

    @POST("order")
    Call<OrderResp> bookEvent(@Body EventOrderBody eventOrderBody);

    @POST("razorpay/order")
    Call<RazorOrderResp>createRazorOrder(@Body RazorOrderBody razorOrderBody);

    @GET("banner")
    Call<List<Banner>> getBanners();

    @POST("razorpay/capture")
    Call<CapturePaymentSuccessResp> razorCapturePayment(@Body RazorCaptureBody captureBody);

    @PUT("razorpay/saverazorpaydata")
    Call<PaymentDataSaveResp> razorDataSave(@Query("id")String orderId, @Body RazorSaveDataBody dataBody);

    @POST("razorpay/createrazorepayorderidforuserwallet")
    Call<WalletCreateResp> createWalletOrder(@Body WalletAmountBody amountBody);

    @PUT("razorpay/addmoneytowallet")
    Call<AddWalletDataResp> saveWalletData(@Body WalletSaveBody saveDataBody);

    @GET("wallet")
    Call<WalletResp>getWallet();

    @GET("order/all")
    Call<List<OrderHistoryResp>> getAllOrders();

    @POST("event/celebrity")
    Call<CelebrityResp> getCelebrity(@Query("page") int page, @Query("limit") int limit, @Body EventFilterBody filter);

    @GET("FAQ")
    Call<List<FAQResp>> getFAQ();

    @GET("user/video/sendotp")
    Call<ResponseBody> getVidOtp(@Query("eventId") String eventId);
    @GET("user/video/resend")
    Call<ResponseBody> resendVidOtp(@Query("eventId") String eventId);
    @GET("user/video/verify")
    Call<ResponseBody> verifyVideo(@Query("code") String code,@Query("eventId") String eventId);

    @GET("blog")
    Call<List<BlogResp>> getBlog();

    @POST("contactus")
    Call<ResponseBody> cantactUs(@Body ContactBody contactBody);

    @GET("order/iswalletseleted/sendotp")
    Call<ResponseBody> walletSelectedSendOtp(@Query("orderId")String orderId,@Query("isWalletSelected") boolean isWallet);

    @GET("order/iswalletseleted/resendotp")
    Call<ResponseBody> walletSelectedResendOtp(@Query("orderId")String orderId,@Query("isWalletSelected") boolean isWallet);

    @GET("order/iswalletseleted/verifyotp")
    Call<WalletVerifyResp> isWalletSelectedVerifyOtp(@Query("orderId")String orderId, @Query("otp") String otp);

    @GET("order/applydiscountcode")
    Call<ApplyCouponResp> applyDiscount(@Query("orderId") String orderId, @Query("code") String coupon, @Query("userId") String userId);

    @GET("order/partialpayment")
    Call<PartialPayResp> partialPayment(@Query("orderId") String orderId);
    @POST("event/searchevent")
    Call<EventResp> searchEvents(@Body SearchReqBody searchReqBody);

    @GET("user/referral")
    Call<ReferralResp> getReferralCode();

    @GET("blog/likeandlike")
    Call<ResponseBody> blogLikeDislike(@Query("blogId") String blogId,@Query("key") String key);

    @Multipart
    @PUT("user/profileimage")
    Call<ProfileImageResp> uploadPic(@Part MultipartBody.Part image);

    @GET("event/eventuserpaymentstatus")
    Call<EventPaidCheckResp> isEventPaid(@Query("eventId") String eventId,@Query("userId") String userId);

    @GET("event/comments")
    Call<CommentResp> getLiveComment(@Query("eventId") String eventId);

    @POST("event")
    Call<ResponseBody> postLiveComment(@Query("eventId") String eventId, @Body LiveUserCommentBody commentBody);
    @GET("about")
    Call<List<PrivacyResp>> getAboutUs();
    @POST("blog/searchblogs")
    Call<List<BlogResp>> blogSearch(@Body SearchReqBody searchReqBody);
}
