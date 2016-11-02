package com.agjsj.mentality.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.agjsj.mentality.R;
import com.agjsj.mentality.network.UserNetwork;
import com.agjsj.mentality.ui.MainActivity;
import com.agjsj.mentality.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void onClick() {

        if (TextUtils.isEmpty(etUsername.getText())) {
            toast("学号/工号不能为空");

        } else if (TextUtils.isEmpty(etPassword.getText())) {
            toast("请填写密码");

        } else {
            login();
        }


    }

    /**
     * 登录
     */
    private void login() {

        UserNetwork.getInstance().login(etUsername.getText().toString().trim(),
                etPassword.getText().toString().trim(), new UserNetwork.LoginCallBack() {
                    @Override
                    public void loginResponse(int responseCode) {
                        if (UserNetwork.LOGIN_YES == responseCode) {
                            startActivity(MainActivity.class, null, true);
                        } else if (UserNetwork.LOGIN_NO == responseCode) {

                            toast("抱歉，登录失败");
                        } else {
                            toast("抱歉，登录失败");
                        }
                    }
                });

    }
}
