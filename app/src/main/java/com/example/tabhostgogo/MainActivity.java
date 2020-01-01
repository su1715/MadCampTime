package com.example.tabhostgogo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class PhoneBook{
    private String id;
    private String name;
    private String number;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    PhoneBook(String id, String name, String number){
        this.id=id;
        this.name=name;
        this.number=number;
    }
    PhoneBook(){

    }

}

class Loader{
    public static ArrayList<PhoneBook> getData(Context context){
        ArrayList<PhoneBook> datas=new ArrayList<>();
        ContentResolver resolver=context.getContentResolver();
        Uri phoneUri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String proj[]={ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        String sortOrder="case"+
                " when substr(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+", 1,1) BETWEEN 'ㄱ' AND '힣' then 1 "+
                " when substr(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+", 1,1) BETWEEN 'A' AND 'Z' then 2 "+
                " when substr(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+", 1,1) BETWEEN 'a' AND 'z' then 3 "+
                " else 4 end, " + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" COLLATE LOCALIZED ASC";
        Cursor cursor=resolver.query(phoneUri,proj,null,null,sortOrder);
        if(cursor!=null){
            while(cursor.moveToNext()){
                int index=cursor.getColumnIndex(proj[0]);
                String id=cursor.getString(index);

                index=cursor.getColumnIndex(proj[1]);
                String name=cursor.getString(index);

                index=cursor.getColumnIndex(proj[2]);
                String number=cursor.getString(index);


                PhoneBook book=new PhoneBook();
                book.setId(id);
                book.setName(name);
                book.setNumber(number);

                datas.add(book);

            }
        }
        cursor.close();
        return datas;
    }
}






public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host);

        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1) ;
        tabHost1.setup() ;

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.content1) ;



        ArrayList<PhoneBook> phoneBooks=new ArrayList<>();
        phoneBooks=Loader.getData(this);


        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler1) ;
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 Tab1TextAdapter 객체 지정.
        Tab1TextAdapter adapter = new Tab1TextAdapter(phoneBooks) ;
        recyclerView.setAdapter(adapter) ;

        ts1.setIndicator("PhoneBook") ;
        tabHost1.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.content2) ;
        ts2.setIndicator("Gallery") ;
        tabHost1.addTab(ts2) ;

        initLayout();
        init();


        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"content3")
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3") ;
        ts3.setContent(R.id.content3) ;
        ts3.setIndicator("TAB 3") ;
        tabHost1.addTab(ts3) ;


    }

    private Tab2GalleryManager mGalleryManager;
    private RecyclerView recyclerGallery;
    private Tab2GalleryAdapter galleryAdapter;


    //tab2필요한 부분
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //이게 뭘까?

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * 레이아웃 초기화
     */
    private void initLayout() {

        recyclerGallery = (RecyclerView) findViewById(R.id.recyclerGallery);
    }


    /**
     * 데이터 초기화
     */
    private void init() {

        //갤러리 리사이클러뷰 초기화
        initRecyclerGallery();
    }


    /**
     * 갤러리 아미지 데이터 초기화
     */
    private List<Tab2PhotoVO> initGalleryPathList() {

        mGalleryManager = new Tab2GalleryManager(getApplicationContext());
        //return mGalleryManager.getDatePhotoPathList(2015, 9, 19);
        return mGalleryManager.getAllPhotoPathList();
    }

    /**
     * 갤러리 리사이클러뷰 초기화
     */
    private void initRecyclerGallery() {

        galleryAdapter = new Tab2GalleryAdapter(MainActivity.this, initGalleryPathList(), R.layout.tab2_item_photo);
        galleryAdapter.setOnItemClickListener(mOnItemClickListener);
        recyclerGallery.setAdapter(galleryAdapter);
        recyclerGallery.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerGallery.setItemAnimator(new DefaultItemAnimator());
//        recyclerGallery.addItemDecoration(new GridDividerDecoration(getResources(), R.drawable.divider_recycler_gallery));
    }


    /**
     * 리사이클러뷰 아이템 선택시 호출 되는 리스너
     */
    private Tab2OnItemClickListener mOnItemClickListener = new Tab2OnItemClickListener() {

        @Override
        public void OnItemClick(Tab2GalleryAdapter.PhotoViewHolder photoViewHolder, int position) {

            Tab2PhotoVO photoVO = galleryAdapter.getmPhotoList().get(position);
            System.out.println(galleryAdapter.getmPhotoList().size());
            String[] paths=new String[galleryAdapter.getmPhotoList().size()];

            for (int i=0;i<galleryAdapter.getmPhotoList().size();i++){
                paths[i]=galleryAdapter.getmPhotoList().get(i).getImgPath();
            }

//            System.out.println(position);
//            while(galleryAdapter)

//            List<Tab2PhotoVO> images=galleryAdapter.getmPhotoList();

            Intent fullScreenIntent=new Intent(getApplicationContext(), Tab2FullScreenImageActivity.class);
            fullScreenIntent.putExtra("imgPath", photoVO.getImgPath());
            fullScreenIntent.putExtra("paths",paths );
            fullScreenIntent.putExtra("position", position);

//        Data(PhotoVO.getImgPath());
            photoViewHolder.imgPhoto.getContext().startActivity(fullScreenIntent);
            
        }
    };
}
