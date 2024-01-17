package com.msa.supervisor.model.api

import com.msa.supervisor.model.data.database.entity.BranchesEntity
import com.msa.supervisor.model.data.database.entity.CustomerEntity
import com.msa.supervisor.model.data.database.entity.CustomerapprovalEntity
import com.msa.supervisor.model.data.database.entity.ProductGroupEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHistoryEntity
import com.msa.supervisor.model.data.database.entity.UserLoginEntity
import com.msa.supervisor.model.data.database.entity.VisitorsEntity
import com.msa.supervisor.model.data.request.ConfirmOrderRequest
import com.msa.supervisor.model.data.request.DataConfrimOrderReguest
import com.msa.supervisor.model.data.request.GpsTrackingRequestModel
import com.msa.supervisor.model.data.request.OrderStatusRequestModel
import com.msa.supervisor.model.data.request.PinCodeApproveReguestModel
import com.msa.supervisor.model.data.request.SyncQuestionnaire
import com.msa.supervisor.model.data.request.VisitorVisitInfoRequest
import com.msa.supervisor.model.data.response.*
import com.msa.supervisor.model.data.response.customer.CustomerPinModel
import com.msa.supervisor.model.data.response.map.GpsTrackingJsonModel
import com.msa.supervisor.model.data.response.news.NewsModel
import com.msa.supervisor.model.data.response.order.OrderConfirmModel
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireHeaderModel
import com.msa.supervisor.model.data.response.report.CustomerNoSaleReportModel
import com.msa.supervisor.model.data.response.report.CustomerSalesSummaryReportModel
import com.msa.supervisor.model.data.response.report.OrderStatusReportModel
import com.msa.supervisor.model.data.response.report.ProductInvoiveBalanceReportModel
import com.msa.supervisor.model.data.response.report.ProductSalesSummaryReportModel
import com.msa.supervisor.model.data.response.report.ReturnDealerModel
import com.msa.supervisor.model.data.response.tour.TourIdModel
import com.msa.supervisor.model.data.response.userlogin.UserToken
import com.msa.supervisor.model.data.response.visitor.VisitorVisitInfoModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.UUID


/**
 * create by Ali Soleymani.
 */

interface ApiSupervisor {

    companion object {
        const val api = "/ApiSupervisor"
        const val v1 = "$api/V1"
        const val user = "$v1/User"
    }


//    @POST("$")
//    suspend fun requestLogin(
//        @Body login: LoginRequestModel,
//    ): Response<GeneralResponse<String?>>


    @GET("")
    suspend fun getOwnerKeys(): Response<List<BranchesEntity>?>

    @FormUrlEncoded
    @POST("")
    suspend fun requestUserToken(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("grant_type") grantType: String?,
        @Field("scope") scope: String?,
        @Field("DeviceId") deviceId: String?,
        @Field("Token") token: String?,
        @Field("vpnUser") vpnUser: String?,
        @Field("SystemTypeId") SystemTypeId: String?
    ): Response<UserToken?>?

    @GET("")
    suspend fun requestLogin(
        @Query("UserName") userName: String?,
        @Query("Password") password: String?,
        @Query("Token") token: String?,
        @Query("DeviceId") deviceId: String?,
    ): Response<UserLoginEntity?>?


    @GET("")
    suspend fun getVisitors(@Query("Ids") ids: String, @Query("type") type: String):
            Response<List<VisitorsEntity>?>


    @GET("")
    suspend fun getCustomerapproval(@Query("SupervisorId") supervisorId: String):
            Response<List<CustomerapprovalEntity>?>

    @GET("")
    suspend fun getQuestionnaireHeaders(
        @Query("date") date: String,
        @Query("SubSystemType") subSystemType: String
    ):
            Response<List<QuestionnaireHeaderModel>?>

    @GET("")
    suspend fun getQuestionnaireHistory(@Query("date") date: String?):
            Response<List<QuestionnaireHistoryEntity>?>

    @GET("")
    suspend fun getTourId(@Query("SupervisorId") supervisorId: String):
            Response<TourIdModel?>

    @POST("")
    suspend fun getsupervisorCustomers(@Body dealersId: List<String>):
            Response<List<CustomerEntity>?>


    @GET("")
    suspend fun getProductGroup(@Query("Data") deteAfter: String):
            Response<List<ProductGroupEntity>?>


    @GET("")
    suspend fun getPinCodes(): Response<List<CustomerPinModel>?>

    @GET("")
    suspend fun tourSend(@Query("id") userId: String?): Response<Void?>?


    @POST("")
    suspend fun getGpsTrackingJson(@Body gpsTrackingJsonModel: GpsTrackingRequestModel):
            Response<List<GpsTrackingJsonModel>?>


    @POST("")
    suspend fun getVisitorsVisitInfo(@Body visitorVisitInfo: VisitorVisitInfoRequest?):
            Response<List<VisitorVisitInfoModel>?>


    @GET("")
    suspend fun invoiceRemain(
        @Query("DealersId") dealersId: List<String?>?,
        @Query("StartDate") startDate: String?,
        @Query("EndDate") endDate: String?
    ): Response<List<ProductInvoiveBalanceReportModel>?>

    @GET("")
    suspend fun customerGroupSales(
        @Query("DealersId") dealersId: List<String?>?,
        @Query("StartDate") startDate: String?,
        @Query("EndDate") endDate: String?
    ): Response<List<CustomerSalesSummaryReportModel>?>


    @GET("")
    suspend fun productSalesSummaryReport(
        @Query("DealersId") dealersId: List<String?>?,
        @Query("StartDate") startDate: String?,
        @Query("EndDate") endDate: String?
    ): Response<List<ProductSalesSummaryReportModel>?>


    @GET("")
    suspend fun customerNoSaleReport(
        @Query("DealersId") dealersId: List<String?>?,
        @Query("StartDate") startDate: String?,
        @Query("EndDate") endDate: String?,
        ): Response<List<CustomerNoSaleReportModel>?>


    @POST("")
    suspend fun requestOrderConfirm(@Body confirmOrderRequest: ConfirmOrderRequest?):
            Response<List<OrderConfirmModel>?>


    @POST("")
    suspend fun sendDataConfrimOrder(@Body dataOrderConfirm: DataConfrimOrderReguest?): Response<ResponseBody?>?


    @POST("")
    suspend fun sendQuestionnaire(@Body syncGetTourModel: SyncQuestionnaire?): Response<ResponseBody?>?

    @GET("")
    suspend fun requestSendCustomerapproval(
        @Query("id") id: UUID?,
        @Query("State") State: Boolean?
    ): Response<ResponseBody?>?

    @POST("")
    suspend fun requestSendPinrapprove(
        @Body pinCodeApproveReguest: PinCodeApproveReguestModel?
    ): Response<String?>?

    @GET("")
    suspend fun tourreceived(@Query("id") id: String?): Response<Void?>?

    @POST("")
    suspend fun requestOrderStatusReport(@Body param: OrderStatusRequestModel?):
            Response<List<OrderStatusReportModel>?>

    @POST("")
    suspend fun requestReturnReport(@Body param: OrderStatusRequestModel?):
            Response<List<ReturnDealerModel>?>

    @GET("")
    suspend fun requestNews(): Response<List<NewsModel>?>
}