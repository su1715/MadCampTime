package com.example.tabhostgogo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class Tab2GalleryAdapter extends RecyclerView.Adapter<Tab2GalleryAdapter.PhotoViewHolder> {


    //    private Activity mActivity;
    private Activity mActivity;
//    private Fragment mFragment;
    private int itemLayout;
    private List<Tab2PhotoVO> mPhotoList;

    private Tab2OnItemClickListener onItemClickListener; //adapterview 지움

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgPhoto;
        public RelativeLayout layoutSelect;

        public PhotoViewHolder(View itemView) {
            super(itemView);

            imgPhoto = (ImageView) itemView.findViewById(R.id.imgPhoto);
            layoutSelect = (RelativeLayout) itemView.findViewById(R.id.layoutSelect);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition() ;
//                    if (pos != RecyclerView.NO_POSITION) {
//                        mData.set(pos, "item clicked. pos=" + pos) ;
//
//                        notifyItemChanged(pos) ;
//                    }
//                }
//            });
//
//


        }

    }

    /**
     * PhotoList 반환
     * @return
     */
    public List<Tab2PhotoVO> getmPhotoList() {
        return mPhotoList;
    }


    /**
     * 선택된 PhotoList 반환
     * @return
     */
    public List<Tab2PhotoVO> getSelectedPhotoList(){

        List<Tab2PhotoVO> mSelectPhotoList = new ArrayList<>();

        for (int i = 0; i < mPhotoList.size(); i++) {

            Tab2PhotoVO tab2PhotoVO = mPhotoList.get(i);
            if(tab2PhotoVO.isSelected()){
                mSelectPhotoList.add(tab2PhotoVO);
            }
        }

        return mSelectPhotoList;
    }

    /**
     * 아이템 선택시 호출되는 리스너
     * @param onItemClickListener
     */

    //클릭처리



    public void setOnItemClickListener(Tab2OnItemClickListener onItemClickListener) { //adapterview 지웠음
        this.onItemClickListener = onItemClickListener;
    }


    /**
     * 생성자
     * @param photoList
     * @param itemLayout
     */
//    public Tab2GalleryAdapter(Activity activity, List<Tab2PhotoVO> photoList, int itemLayout) {
//
//        mActivity = activity;
//        this.mPhotoList = photoList;
//        this.itemLayout = itemLayout;
//
//    }

    public Tab2GalleryAdapter(Activity activity, List<Tab2PhotoVO> photoList, int itemLayout) {

        mActivity = activity;
        this.mPhotoList = photoList;
        this.itemLayout = itemLayout;

    }



    /**
     * 레이아웃을 만들어서 Holer에 저장
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new PhotoViewHolder(view);
    }

    /**
     * listView getView 를 대체
     * 넘겨 받은 데이터를 화면에 출력하는 역할
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final PhotoViewHolder viewHolder, final int position) {

        final Tab2PhotoVO tab2PhotoVO = mPhotoList.get(position);
//        Glide.with(mFragment)
//                .load(tab2PhotoVO.)
        Glide.with(viewHolder.itemView.getContext())
                .load(tab2PhotoVO.getImgPath())
                .centerCrop()
                .crossFade()
                .into(viewHolder.imgPhoto);

        //선택
//        if(tab2PhotoVO.isSelected()){
//            viewHolder.layoutSelect.setVisibility(View.VISIBLE);
//        }else{
//            viewHolder.layoutSelect.setVisibility(View.INVISIBLE);
//        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                if (onItemClickListener != null) {

                    onItemClickListener.OnItemClick(viewHolder, position);

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }


    /**
     * 뷰 재활용을 위한 viewHolder
     */

}