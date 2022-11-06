package com.example.crs.constructors;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crs.EditCrime;
import com.example.crs.GetRoute;
import com.example.crs.MyMessages;
import com.example.crs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MySentComplaintsAdapter extends RecyclerView.Adapter<MySentComplaintsAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Messages item);

    }


    final MySentComplaintsAdapter.OnItemClickListener listener;
    Context context;
    List<Messages> MainImageUploadInfoList;

    public MySentComplaintsAdapter(Context context, List<Messages> TempList, MySentComplaintsAdapter.OnItemClickListener listener) {

        this.MainImageUploadInfoList = TempList;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public MySentComplaintsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_complaint_row, parent, false);

        MySentComplaintsAdapter.ViewHolder viewHolder = new MySentComplaintsAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MySentComplaintsAdapter.ViewHolder holder, int position) {
        final Messages UploadInfo = MainImageUploadInfoList.get(position);
        holder.bind(UploadInfo, listener);
        if(UploadInfo!=null){
            //add data to fields
            if(UploadInfo.getMessage()!=null){
                holder.Complaint.setText(UploadInfo.getMessage());
            }
            if(UploadInfo.getDate()!=null&&UploadInfo.getTime()!=null){
                holder.Sentdate.setText(UploadInfo.getDate()+""+UploadInfo.getTime());

            }
            if(UploadInfo.getPoliceName()!=null){
                holder.policeName.setText(UploadInfo.getPoliceName());
            }
            if(UploadInfo.getImage()!=null){
                holder.police_I.setText(UploadInfo.getImage());
            }
            if(UploadInfo.getReceiver()!=null){
                holder.police_idx.setText(UploadInfo.getReceiver());
            }
            if(UploadInfo.getImage()!=null){
                if (UploadInfo.getImage().equalsIgnoreCase("default")) {
                    Picasso.get().load(R.drawable.profile1).into(holder.profileImage);
                } else {
                    Picasso.get().load(UploadInfo.getImage()).into(holder.profileImage);
                }



            }
        }

    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView policeName, Complaint,Sentdate,police_idx,police_I;
        ImageView profileImage;
        ImageView onlineIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            policeName = itemView.findViewById(R.id.police_name);
            Complaint = itemView.findViewById(R.id.compl);
            Sentdate=itemView.findViewById(R.id.date_sent);
            profileImage = itemView.findViewById(R.id.police_pic);
            onlineIcon = itemView.findViewById(R.id.onlineornot);
            police_idx=itemView.findViewById(R.id.police_id);
            police_I=itemView.findViewById(R.id.police_image);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });





        }
        public void bind(Messages messages, MySentComplaintsAdapter.OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(messages);
               /**
                    Dialog witness_dialog = new Dialog(context.getApplicationContext());
                    witness_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    witness_dialog.setContentView(R.layout.witness_dialog);
                    witness_dialog.show();


                    TextView cx_message = (TextView) witness_dialog .findViewById(R.id.c_message);
                    ImageButton c_edit=(ImageButton) witness_dialog.findViewById(R.id.btn_edit);
                    ImageButton c_delete=(ImageButton)witness_dialog.findViewById(R.id.btn_delete);

                    cx_message.setText(Complaint.getText());

                    c_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent c= new Intent(context.getApplicationContext(), EditCrime.class);
                            c.putExtra("message_id",messages_id);
                            c.putExtra("comp",Complaint.getText().toString());
                            c.putExtra("police_id",reciever_id);
                            Toast.makeText(context.getApplicationContext(),messages_id,Toast.LENGTH_SHORT).show();
                            witness_dialog.dismiss();
                            context.startActivity(c);


                        }
                    });
                    c_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDatabase.child(messages_id).removeValue();
                            witness_dialog.dismiss();
                        }
                    });
**/
                }
            });



        }
    }
}