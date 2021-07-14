package com.aoslec.androidproject.KakaoSetting;

import android.content.Intent;
import android.util.Log;

import com.aoslec.androidproject.Activity.MainActivity;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

import android.app.Activity;
import android.widget.Toast;

import com.kakao.auth.ApiErrorCode;

public class SessionCallback implements ISessionCallback {
    private static final String TAG = "SESSION KAKAO";
    private Activity activity;


    // 로그인에 성공한 상태

    public SessionCallback(Activity activity) {
        this.activity = activity;
    }

    @Override

    public void onSessionOpened() {      //세션이 성공적으로 열림
        Log.e(TAG, "onSessionOpened: ");
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                int result = errorResult.getErrorCode(); //오류 코드를 받아온다.

                if (result == ApiErrorCode.CLIENT_ERROR_CODE) { //클라이언트 에러인 경우: 네트워크 오류
                    Toast.makeText(activity, "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                } else { //클라이언트 에러가 아닌 경우: 기타 오류
                    Toast.makeText(activity, "로그인 도중 오류가 발생했습니다: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSuccess(MeV2Response result) {  //유저 정보를 가져오는데 성공한 경우


                String needsScopeAutority = ""; //이메일, 성별, 연령대, 생일 정보 가져오는 권한 체크용
                if (result.getKakaoAccount().needsScopeAccountEmail()) { //이메일 정보를 가져오는 데 사용자가 동의하지 않은 경우
                    needsScopeAutority = needsScopeAutority + "이메일";
                }

                Log.e(TAG, "onSuccess: profile : " + result.getKakaoAccount().getProfile().getNickname());
                Log.e(TAG, "onSuccess: profile : " + result.getKakaoAccount().getProfile().getProfileImageUrl());
                Log.e(TAG, "onSuccess: profile : " + result.getKakaoAccount().getEmail());
                Log.e(TAG, "onSuccess: 로그인 여부 : " + SaveSharedPreferences.getPrefIsLogin(activity));

                String email = result.getKakaoAccount().getEmail();
                String name = result.getKakaoAccount().getProfile().getNickname();
                String imageUrl = result.getKakaoAccount().getProfile().getProfileImageUrl();


                SaveSharedPreferences.setPrefIsLogin(activity, "y");
                SaveSharedPreferences.setPrefAutoLogin(activity, "y");
                SaveSharedPreferences.setPrefEmail(activity, email);
                SaveSharedPreferences.setPrefName(activity, name);
                SaveSharedPreferences.setPrefImage(activity, imageUrl);
                SaveSharedPreferences.setLoginMethod(activity, "Kakao");



                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);


            }
        });

    }


    // 로그인에 실패한 상태
    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        if (exception != null) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
        }
//        if (LoginPage.loginPage.progressDialog != null)
//            LoginPage.loginPage.progressDialog.dismiss();
        Log.e(TAG, "onSessionOpenFailed: 카카오 로그인 취소" );
    }


    public void redirectLoginActivity() {
        Log.e(TAG, "redirectLoginActivity: ");
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);

    }


    // 사용자 정보 요청
    public void requestMe() {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                super.onFailure(errorResult);
                int result = errorResult.getErrorCode();

                if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                    Toast.makeText(activity, "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "로그인 도중 오류가 발생했습니다: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSuccess(MeV2Response result) {
                Log.e("SessionCallback :: ", "onSuccess");

                if (result.getKakaoAccount().hasEmail() == OptionalBoolean.TRUE) {
                    Log.e(TAG, "onSuccess: 이메일 : " + result.getKakaoAccount().getEmail());
                }

                String nickname = result.getNickname();
                String email = result.getKakaoAccount().getEmail();
                String phone = result.getKakaoAccount().getPhoneNumber();
                long id = result.getId();


                Log.e(TAG, "onSuccess: 이메일 동의 : " + result.getKakaoAccount().emailNeedsAgreement());
                Log.e("Profile : ", nickname + "");
                Log.e("Profile : ", email + "");
                Log.e("Profile : ", phone + "");
                Log.e("Profile : ", id + "");
                Log.e(TAG, "onSuccess: 프로필  " + result.getKakaoAccount().getDisplayId());
                Log.e(TAG, "onSuccess: 프로필  " + result.getKakaoAccount().getProfile());
                Log.e(TAG, "onSuccess: 프로필  " + result.getKakaoAccount().getLegalName());

//                if (LoginPage.loginPage.progressDialog != null)
//                    LoginPage.loginPage.progressDialog.dismiss();
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);


            }
        });


    }
}