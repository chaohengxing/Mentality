package com.agjsj.mentality.ui.menu;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.agjsj.mentality.R;
import com.agjsj.mentality.myview.CircleImageView;
import com.agjsj.mentality.network.FileNetwork;
import com.agjsj.mentality.network.UserNetwork;
import com.agjsj.mentality.popupwindow.ListPopup;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.ui.photo.ViewPagerActivity;
import com.agjsj.mentality.utils.ImageZipUtils;
import com.agjsj.mentality.utils.PicassoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentInfoActivity extends ParentWithNaviActivity {

    @Bind(R.id.iv_icon)
    CircleImageView ivIcon;
    @Bind(R.id.tv_nickname)
    EditText tvNickname;
    @Bind(R.id.tv_sex)
    EditText tvSex;
    @Bind(R.id.tv_useclass)
    EditText tvUseclass;
    @Bind(R.id.tv_major)
    EditText tvMajor;
    @Bind(R.id.btn_edit)
    Button btnEdit;
    @Bind(R.id.btn_cancle)
    Button btnCancle;

    private boolean canEdit = false;

    private String currentIconPath;

    @Override
    protected String title() {
        return "我的资料";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        ButterKnife.bind(this);
        initNaviView();
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        if (TextUtils.isEmpty(UserNetwork.getInstance().getCurrentUser().getStuIcon())) {
            ivIcon.setImageResource(R.drawable.default_pic);
        } else {
            PicassoUtils.loadImage(UserNetwork.getInstance().getCurrentUser().getStuIcon(), ivIcon);
        }

        tvNickname.setText(UserNetwork.getInstance().getCurrentUser().getNickName());
        tvSex.setText(UserNetwork.getInstance().getCurrentUser().getSex());
        tvUseclass.setText(UserNetwork.getInstance().getCurrentUser().getUserClass());
        tvMajor.setText(UserNetwork.getInstance().getCurrentUser().getUserMajor());

        btnCancle.setVisibility(View.GONE);

    }


    @OnClick({R.id.iv_icon, R.id.btn_edit, R.id.btn_cancle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_icon:
                if (canEdit) {
                    //如果是可编辑状态则 选择照片或者拍照
                    listPopup.showPopupWindow();

                } else {
                    //进入大图

                    Bundle bundle = new Bundle();
                    ArrayList<String> images = new ArrayList<>();
                    images.add(UserNetwork.getInstance().getCurrentUser().getUserIcon());
                    bundle.putSerializable("imageUrls", images);
                    bundle.putInt("position", 0);
                    startActivity(ViewPagerActivity.class, bundle, false);
                }


                break;
            case R.id.btn_edit:

                if (canEdit) {
                    //提交
                    if (TextUtils.isEmpty(tvNickname.getText().toString().trim()) ||
                            TextUtils.isEmpty(tvSex.getText().toString().trim()) ||
                            TextUtils.isEmpty(tvUseclass.getText().toString().trim()) ||
                            TextUtils.isEmpty(tvMajor.getText().toString().trim())) {
                        toast("信息不能有空，请完整填写!");
                        return;
                    }
                    commint();
                } else {
                    //变为可编辑状态
                    changeCanEdit();
                }

                break;

            case R.id.btn_cancle:
                currentIconPath = "";
                canEdit = false;
                btnEdit.setText("修改");
                btnCancle.setVisibility(View.GONE);
                initView();
                break;
        }
    }

    /**
     * 切换为可编辑状态
     */
    private void changeCanEdit() {
        canEdit = true;
        btnEdit.setText("提交");
        btnCancle.setVisibility(View.VISIBLE);
        initListPopup();
    }

    /**
     * 提交编辑后的资料
     */
    private void commint() {
        /**
         * 如果突破为空，则直接上传文字内容
         */
        if (TextUtils.isEmpty(currentIconPath)) {
            commintStudentInfo(null);
        } else {
            //先上传图片
            //再上传文字内容
            commintImage();
        }


    }

    /**
     * 上传图片
     */
    private void commintImage() {
        FileNetwork.getInstance().sendOneImageFiles(currentIconPath, new FileNetwork.SendOneImageFilesCallBack() {
            @Override
            public void response(int responseCode, String imgeUrl) {
                if (FileNetwork.SEND_IMAGE_ONE_FILE_YES == responseCode) {
                    commintStudentInfo(imgeUrl);
                } else if (FileNetwork.SEND_IMAGE_ONE_FILE_NO == responseCode) {
                    toast("上传图片失败，请重新提交");
                }
            }
        });

    }

    /**
     * 上传学生信息
     */
    private void commintStudentInfo(String imageUrl) {


        UserNetwork.getInstance().updateStudentInfo(
                UserNetwork.getInstance().getCurrentUser().getId(),
                TextUtils.isEmpty(imageUrl) ? UserNetwork.getInstance().getCurrentUser().getStuIcon() : imageUrl,
                tvNickname.getText().toString(),
                tvSex.getText().toString(),
                tvUseclass.getText().toString(),
                tvMajor.getText().toString(),
                new UserNetwork.updateStudentInfoCallBack() {

                    @Override
                    public void response(int responseCode) {

                        if (UserNetwork.UPDATE_STUDENTINFO_YES == responseCode) {
                            toast("提交成功");
                            finish();
                        } else if (UserNetwork.UPDATE_STUDENTINFO_NO == responseCode) {
                            toast("提交失败!");
                        }
                    }
                }
        );


    }

    //---------------------------以下是获取照片代-------------------------
    private ListPopup listPopup;

    /**
     * 初始化对话框
     */
    private void initListPopup() {

        listPopup = new ListPopup.Builder(this)
                .addItem(RESULT_CAMERA, "拍照")
                .addItem(RESULT_ALBUM, "打开相册")
                .build();

        listPopup.setOnListPopupItemClickListener(new ListPopup.OnListPopupItemClickListener() {
            @Override
            public void onItemClick(int what) {
                switch (what) {
                    case RESULT_CAMERA:
                        toCamera();
                        break;
                    case RESULT_ALBUM:
                        toAlbum();
                        break;
                }
                listPopup.dismiss();
            }
        });
    }

    private final int RESULT_CAMERA = 0;
    private final int RESULT_ALBUM = 1;

    private String currentAlbumPath;

    //去相册中选择
    private void toAlbum() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        //设置拿出数据的网址和类型
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, RESULT_ALBUM);
    }


    private String currentCameraPaht;

    //去拍照
    private void toCamera() {
        //这个常量就是对应的上面的action
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //由于我们需要调用完Camera后，可以返回Camera获取到的图片，
        //所以，我们使用startActivityForResult来启动Camera
        currentCameraPaht = Environment.getExternalStorageDirectory() + File.separator + new Date().getTime() + ".jpg";
        //设置拍照之后的存储路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(currentCameraPaht)));
        startActivityForResult(intent, RESULT_CAMERA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_CAMERA) {
                //从拍照回来的
                if (!TextUtils.isEmpty(currentCameraPaht)) {
                    currentCameraPaht = ImageZipUtils.saveBitmap(this, currentCameraPaht);
                    currentIconPath = currentCameraPaht;
                    ivIcon.setImageBitmap(BitmapFactory.decodeFile(currentIconPath));
                }
            } else if (requestCode == RESULT_ALBUM) {
                ContentResolver contentResolver = getContentResolver();
                //获得照片资源的原始地址
                Uri originalUri = data.getData();
                if (originalUri != null) {
                    String[] proj = {MediaStore.Images.Media.DATA};

                    //好像是Android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    currentAlbumPath = cursor.getString(column_index);
//                originalUrls.add(currentAlbumPath);
                    if (!TextUtils.isEmpty(currentAlbumPath)) {
                        currentAlbumPath = ImageZipUtils.saveBitmap(this, currentAlbumPath);
                        currentIconPath = currentAlbumPath;
                        ivIcon.setImageBitmap(BitmapFactory.decodeFile(currentIconPath));
                    }
                } else {
                    toast("获取相册图片失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
