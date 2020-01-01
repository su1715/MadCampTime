package com.example.tabhostgogo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

//import com.example.tabtwo.PhotoVO;

public class Tab2GalleryManager { //사진정보를 가져옴
    private Context mContext;

    public Tab2GalleryManager(Context context) {
        mContext = context;
    }
    //      * 갤러리 이미지 반환
//     *
//             * @return
//             */
    public List<Tab2PhotoVO> getAllPhotoPathList() {

        ArrayList<Tab2PhotoVO> photoList = new ArrayList<>();

        Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 안드로이스 시스템에서 제공하는 미디어 데이터 DB

        String[] projection = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.DATE_ADDED // 파일과 관련된 컬럼을 나타내는 상수값 가져옴
        };

        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        // 그 안에서의 이동 및 쿼리, 다 가져옴

        int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {

            Tab2PhotoVO tab2PhotoVO = new Tab2PhotoVO(cursor.getString(columnIndexData),false);
            photoList.add(tab2PhotoVO); //채워줌
        }

        cursor.close();

        return photoList;
    }
    //getdatephotopathlist는 나중에 추가하기로.

}
