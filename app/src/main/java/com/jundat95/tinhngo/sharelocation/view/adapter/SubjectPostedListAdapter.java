package com.jundat95.tinhngo.sharelocation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.jundat95.tinhngo.sharelocation.R;
import com.jundat95.tinhngo.sharelocation.repository.local.fakeData.AttendFake;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by tinhngo on 1/8/17.
 */

public class SubjectPostedListAdapter extends RecyclerView.Adapter<SubjectPostedListAdapter.SubjectPostedViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private AdapterCallback adapterCallback;
    private JsonObject stateSubject = new JsonObject();

    public SubjectPostedListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SubjectPostedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_subject_posted_list,parent,false);
        return new SubjectPostedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectPostedViewHolder holder, int position) {

        holder.tvTitle.setText(AttendFake.getAttendModels().get(position).getTitle());
        holder.tvDescription.setText(AttendFake.getAttendModels().get(position).getDescription());
        holder.tvDate.setText(AttendFake.getAttendModels().get(position).getTime());

        String type = "Bắt đầu";

        if(AttendFake.getAttendModels().get(position).getType().equals("1")) {
            type = "Kết thúc";
        }
        holder.tvType.setText(type);

        holder.tvLocation.setText(AttendFake.getAttendModels().get(position).getLocationName());
        holder.imgPhoto.setImageBitmap(AttendFake.getAttendModels().get(position).getImageSrc().get(0));

        if(AttendFake.getAttendModels().get(position).getLatLng() != null) {
            String px = AttendFake.getAttendModels().get(position).getLatLng().latitude + "";
            String py = AttendFake.getAttendModels().get(position).getLatLng().longitude + "";
            holder.tvPosition.setText("Vị Trí: " + px + " - " +py);
        }



    }

    @Override
    public int getItemCount() {
        return AttendFake.getAttendModels().size();
    }


    class SubjectPostedViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgPhoto) ImageView imgPhoto;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvDescription) TextView tvDescription;
        @BindView(R.id.tvDate) TextView tvDate;
        @BindView(R.id.tvType) TextView tvType;
        @BindView(R.id.tvPosition) TextView tvPosition;
        @BindView(R.id.tvLocation) TextView tvLocation;

        @OnClick(R.id.btnDelete) public void deleteItemClick(){

        }


        public void delete(int position){
            notifyItemRemoved(position);
        }

        public SubjectPostedViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    public static interface AdapterCallback{
        public void sendData(int i);
    }

}
