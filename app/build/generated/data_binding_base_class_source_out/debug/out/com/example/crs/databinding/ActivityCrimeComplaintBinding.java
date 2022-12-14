// Generated by view binder compiler. Do not edit!
package com.example.crs.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.crs.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCrimeComplaintBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView btnSendFile;

  @NonNull
  public final ImageView btnSendMessChat;

  @NonNull
  public final RecyclerView recyclerChatMessage;

  @NonNull
  public final LinearLayout sendMessChat;

  @NonNull
  public final EditText yourmessageChat;

  private ActivityCrimeComplaintBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageView btnSendFile, @NonNull ImageView btnSendMessChat,
      @NonNull RecyclerView recyclerChatMessage, @NonNull LinearLayout sendMessChat,
      @NonNull EditText yourmessageChat) {
    this.rootView = rootView;
    this.btnSendFile = btnSendFile;
    this.btnSendMessChat = btnSendMessChat;
    this.recyclerChatMessage = recyclerChatMessage;
    this.sendMessChat = sendMessChat;
    this.yourmessageChat = yourmessageChat;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCrimeComplaintBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCrimeComplaintBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_crime_complaint, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCrimeComplaintBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_send_file;
      ImageView btnSendFile = ViewBindings.findChildViewById(rootView, id);
      if (btnSendFile == null) {
        break missingId;
      }

      id = R.id.btn_send_mess_chat;
      ImageView btnSendMessChat = ViewBindings.findChildViewById(rootView, id);
      if (btnSendMessChat == null) {
        break missingId;
      }

      id = R.id.recycler_chat_message;
      RecyclerView recyclerChatMessage = ViewBindings.findChildViewById(rootView, id);
      if (recyclerChatMessage == null) {
        break missingId;
      }

      id = R.id.send_mess_chat;
      LinearLayout sendMessChat = ViewBindings.findChildViewById(rootView, id);
      if (sendMessChat == null) {
        break missingId;
      }

      id = R.id.yourmessage_chat;
      EditText yourmessageChat = ViewBindings.findChildViewById(rootView, id);
      if (yourmessageChat == null) {
        break missingId;
      }

      return new ActivityCrimeComplaintBinding((RelativeLayout) rootView, btnSendFile,
          btnSendMessChat, recyclerChatMessage, sendMessChat, yourmessageChat);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
