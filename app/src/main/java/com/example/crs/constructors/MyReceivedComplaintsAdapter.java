package com.example.crs.constructors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.crs.GetRoute;
import com.example.crs.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyReceivedComplaintsAdapter extends RecyclerView.Adapter<MyReceivedComplaintsAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Messages item);
    }


    final MyReceivedComplaintsAdapter.OnItemClickListener listener;
    final MyReceivedComplaintsAdapter.OnItemLongClickListener listener1;
    Context context;
    List<Messages> MainImageUploadInfoList;

    public MyReceivedComplaintsAdapter(Context context, List<Messages> TempList, OnItemClickListener listener, OnItemLongClickListener listener1) {

        this.MainImageUploadInfoList = TempList;
        this.context = context;
        this.listener=listener;
        this.listener1 = listener1;
    }

    @Override
    public MyReceivedComplaintsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recieved_complaint_row, parent, false);

        MyReceivedComplaintsAdapter.ViewHolder viewHolder = new MyReceivedComplaintsAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyReceivedComplaintsAdapter.ViewHolder holder, int position) {
        final Messages UploadInfo = MainImageUploadInfoList.get(position);
        holder.bind(UploadInfo, listener);
        holder.bind2(UploadInfo,listener1);
        //notifyItemRemoved(holder.getAdapterPosition());
        if(UploadInfo!=null){
            if(UploadInfo.getMessage()!=null){
                holder.Complaint.setText(UploadInfo.getMessage());
            }
            if(UploadInfo.senderImage!=null){
                //Loading image from Glide library.
                Picasso.get().load(UploadInfo.getSenderImage()).placeholder(R.drawable.profile1).into(holder.profileImage);

            }
            if(UploadInfo.getComplainant()!=null){
                holder.policeName.setText(UploadInfo.getComplainant());
            }
            if(UploadInfo.getDate()!=null&&UploadInfo.getTime()!=null){
               holder.Sentdate.setText(UploadInfo.getDate()+" "+UploadInfo.getTime());
            }
            if(UploadInfo.getSenderImage()!=null){
              holder.image_N.setText(UploadInfo.getSenderImage());
            }
            if(UploadInfo.isSeen()==true){
                Picasso.get().load(R.drawable.seen_eye).placeholder(R.drawable.not_seen).into(holder.Sn);

            }


        }

    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Messages item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView policeName, Complaint,Sentdate,user_idx,image_N,phn;
        ImageView profileImage,Sn;
        ImageView onlineIcon;
        String reciever_id;
        ImageButton btn_call,btn_locate;

        public ViewHolder(View itemView) {
            super(itemView);
            policeName = itemView.findViewById(R.id.police_name);
            Complaint = itemView.findViewById(R.id.compl);
            Sentdate=itemView.findViewById(R.id.date_sent);
            profileImage = itemView.findViewById(R.id.police_pic);
            onlineIcon = itemView.findViewById(R.id.onlineornot);
            user_idx=itemView.findViewById(R.id.user_id);
            image_N=itemView.findViewById(R.id.image_name);
            btn_call=itemView.findViewById(R.id.call_btn);
            btn_locate=itemView.findViewById(R.id.location_btn);
            Sn=itemView.findViewById(R.id.seen_or);




        }
        public void bind2(Messages messages,MyReceivedComplaintsAdapter.OnItemLongClickListener listener1){
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener1.onItemLongClick(messages);
                    return true;
                }
            });
        }
        public void bind(Messages messages, MyReceivedComplaintsAdapter.OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(messages);

                }

            });

            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog alertDialog = new AlertDialog.Builder(context.getApplicationContext())
//set icon
                            .setIcon(R.drawable.crs)
//set title
                            .setTitle("CSR CALL")
//set message
                            .setMessage("Call Complainant"+messages.getPhone()+"?")
//set positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //calling complainant
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setData(Uri.parse("tel:" +messages.getPhone()));
                                    // Here, thisActivity is the current activity

                                }
                            })
//set negative button
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //alertDialog.dismiss();
                                }
                            })
                            .show();

                }
            });
            btn_locate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String lati=messages.getLati();
                    String longi=messages.getLongi();
                    String crime=messages.getMessage();
                    Intent route=new Intent(context.getApplicationContext(), GetRoute.class);
                    route.putExtra("longitude",longi);
                    route.putExtra("latitude",lati);
                    route.putExtra("crime",crime);

                    context.startActivity(route);
                }
            });
            /**
            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   AlertDialog alertDialog = new AlertDialog.Builder(context.getApplicationContext())
//set icon
                            .setIcon(R.drawable.crs)
//set title
                            .setTitle("CSR CALL")
//set message
                            .setMessage("Call Complainant"+messages.getPhone()+"?")
//set positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //calling complainant
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setData(Uri.parse("tel:" +messages.getPhone()));
                                    // Here, thisActivity is the current activity
                                    if (ContextCompat.checkSelfPermission(context.getApplicationContext(),
                                            Manifest.permission.CALL_PHONE)
                                            != PackageManager.PERMISSION_GRANTED) {

                                       requestPermissions(context.getApplicationContext(),
                                                new String[]{Manifest.permission.CALL_PHONE},1);

                                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    } else {
                                        //You already have permission
                                        try {
                                            context.startActivity(intent);
                                        } catch(SecurityException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            })
//set negative button
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //alertDialog.dismiss();
                                }
                            })
                            .show();

                }
            });
            **/

        }
    }
}