package com.example.serafellochat.fragments;

import com.example.serafellochat.notifications.MyResponse;
import com.example.serafellochat.notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAb9rEzDQ:APA91bEd-hm6VWJIlnEKFGAVo000iHGr0dNjS8qzL2sQSJcE0KU0Lf3Ic9EhNFH9UcU9rIIOhRE14phHqh2OU0vtLwkhW2CON7hb7jysXbO5kiW1VZmbr3N7Bcg2SBBZ6VjlzZTRHVYQ"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
