package com.agjsj.mentality.ui.jt;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;

import com.agjsj.mentality.R;
import com.agjsj.mentality.adapter.base.OnRecyclerViewListener;
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
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 发布鸡汤activity
 *
 * @author chx
 * @date 2016/11/21
 */
public class SendJtActivity extends ParentWithNaviActivity implements OnRecyclerViewListener {


    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;

    private SendJtAdapter adapter;
    private GridLayoutManager layoutManager;
    //压缩之后图片地址，及要显示在页面和上传服务器的图片
    private List<String> images;



    @Override
    protected String title() {
        return "发布鸡汤";
    }

    @Override
    public Object right() {
        return "发表";
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
                toast("发表鸡汤");
                sendJtImages();
            }
        };
    }

    /**
     * 发布鸡汤
     */
    private void sendJtImages() {
        if (TextUtils.isEmpty(etContent.getText())) {
            toast("鸡汤内容不能为空!");
            return;
        }
        //先上传图片
        FileNetwork.getInstance().sendImageFiles(images, new FileNetwork.SendImageFilesCallBack() {

            @Override
            public void response(int responseCode, List<String> imgeUrls) {
                if (FileNetwork.SEND_IMAGE_FILES_YES == responseCode) {
                    //上传图片成功，开始上传文字信息
                    JtBean jtBean = new JtBean(UserNetwork.getInstance().getCurrentUser().getUserType(),
                            UserNetwork.getInstance().getCurrentUser().getId(),
                            1, etContent.getText().toString(),
                            new Gson().toJson(imgeUrls));
                    sendJtBean(jtBean);

                } else if (FileNetwork.SEND_IMAGE_FILES_NO == responseCode) {
                    toast("上传图片失败");
                }
            }
        });


        //图片上传成功后 才是真正的发布鸡汤


    }

    /**
     * 发送鸡汤文字内容
     *
     * @param jtBean
     */
    private void sendJtBean(JtBean jtBean) {
        JtNetWork.getInstance().sendJtBean(jtBean, new JtNetWork.SendJtBeanReplayCallBack() {
            @Override
            public void response(int responseCode) {
                if (JtNetWork.SEND_JTBEAN_YES == responseCode) {
                    //鸡汤发布成功
                    finish();
                } else if (JtNetWork.SEND_JTBEAN_NO == responseCode) {
                    toast("鸡汤发布失败!");
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_jt);
        ButterKnife.bind(this);
        initNaviView();

        initRecyclerView();

        initListPopup();

    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        images = new ArrayList<>();

//        images.add("http://life.people.com.cn/NMediaFile/2015/0618/MAIN201506181420187740456986383.jpg");
//        images.add("http://img5q.duitang.com/uploads/item/201407/13/20140713142348_VNVBr.jpeg");
//        images.add("http://img3.3lian.com/2013/s4/17/d/43.jpg");

        layoutManager = new GridLayoutManager(this, 3);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new SendJtAdapter(this, images);
        adapter.setOnRecyclerViewListener(this);
        recyclerview.setAdapter(adapter);

    }

    /**
     * 单击了最后一个图标
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {

        if (position == images.size()) {
            listPopup.showPopupWindow();
        }

    }

    @Override
    public void onItemClick(int position, int id) {

    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
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
                    images.add(currentCameraPaht);
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
                        images.add(currentAlbumPath);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    toast("获取相册图片失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();

    }


}
