package com.agjsj.mentality.ui.jt;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.agjsj.mentality.R;
import com.agjsj.mentality.bean.jt.JtBean;
import com.agjsj.mentality.network.FileNetwork;
import com.agjsj.mentality.network.JtNetWork;
import com.agjsj.mentality.network.UserNetwork;
import com.agjsj.mentality.popupwindow.ListPopup;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.utils.ImageZipUtils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分享鸡汤Activity
 *
 * @author chx
 * @date 2016/11/21
 */
public class ShareJtActivity extends ParentWithNaviActivity {
    @Bind(R.id.et_title)
    EditText etTitle;
    @Bind(R.id.et_url)
    EditText etUrl;
    @Bind(R.id.et_from)
    EditText etFrom;
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.et_jtinfo)
    EditText etJtinfo;

    private String imagePath;

    @Override
    protected String title() {
        return "分享美文";
    }

    @Override
    public Object right() {
        return "分享";
    }

    @Override
    public ToolBarListener setToolBarListener() {
        return new ToolBarListener() {
            @Override
            public void clickLeft() {
                finish();
            }

            @Override
            public void clickRight() {

                if (TextUtils.isEmpty(etTitle.getText())) {
                    toast("标题不能为空!");
                    return;
                } else if (TextUtils.isEmpty(etUrl.getText())) {
                    toast("文章链接不能为空!");
                    return;
                } else if (TextUtils.isEmpty(etFrom.getText())) {
                    toast("文章来源不能为空!");
                    return;
                }
                if (!TextUtils.isEmpty(imagePath)) {
                    sendImage();
                } else {
                    sendJt(null);
                }


            }
        };
    }


    /**
     * 上传图片
     */
    private void sendImage() {
        FileNetwork.getInstance().sendOneImageFiles(imagePath, new FileNetwork.SendOneImageFilesCallBack() {
            @Override
            public void response(int responseCode, String imgeUrl) {
                if (FileNetwork.SEND_IMAGE_ONE_FILE_YES == responseCode) {
                    sendJt(imgeUrl);
                } else if (FileNetwork.SEND_IMAGE_ONE_FILE_NO == responseCode) {
                    toast("上传图片失败!");

                }
            }
        });

    }

    /**
     * 分享美文
     */
    private void sendJt(String imageUrl) {

        JtBean jtBean = new JtBean(UserNetwork.getInstance().getCurrentUser().getUserType(),
                UserNetwork.getInstance().getCurrentUser().getId(),
                2, etJtinfo.getText().toString().trim(), imageUrl, etTitle.getText().toString().trim(),
                etUrl.getText().toString().trim(), etFrom.getText().toString().trim());
        JtNetWork.getInstance().sendJtBean(jtBean, new JtNetWork.SendJtBeanReplayCallBack() {
            @Override
            public void response(int responseCode) {
                if (JtNetWork.SEND_JTBEAN_YES == responseCode) {
                    toast("分享美文成功!");
                    finish();
                } else if (JtNetWork.SEND_JTBEAN_NO == responseCode) {
                    toast("分享美文失败!");
                }
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_jt);
        ButterKnife.bind(this);
        initNaviView();

        initListPopup();
    }

    @OnClick(R.id.iv_image)
    public void onClick() {
        listPopup.showPopupWindow();
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
                    imagePath = currentCameraPaht = ImageZipUtils.saveBitmap(this, currentCameraPaht);
                    ivImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));

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
                        imagePath = currentAlbumPath = ImageZipUtils.saveBitmap(this, currentAlbumPath);
                        ivImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
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
