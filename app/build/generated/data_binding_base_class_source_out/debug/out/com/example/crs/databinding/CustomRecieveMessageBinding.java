// Generated by view binder compiler. Do not edit!
package com.example.crs.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.crs.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CustomRecieveMessageBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final CircleImageView imageChat;

  @NonNull
  public final CircleImageView messageReceiverImageView;

  @NonNull
  public final TextView receiveChat;

  private CustomRecieveMessageBinding(@NonNull RelativeLayout rootView,
      @NonNull CircleImageView imageChat, @NonNull CircleImageView messageReceiverImageView,
      @NonNull TextView receiveChat) {
    this.rootView = rootView;
    this.imageChat = imageChat;
    this.messageReceiverImageView = messageReceiverImageView;
    this.receiveChat = receiveChat;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CustomRecieveMessageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CustomRecieveMessageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.custom_recieve_message, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CustomRecieveMessageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.image_chat;
      CircleImageView imageChat = ViewBindings.findChildViewById(rootView, id);
      if (imageChat == null) {
        break missingId;
      }

      id = R.id.message_receiver_image_view;
      CircleImageView messageReceiverImageView = ViewBindings.findChildViewById(rootView, id);
      if (messageReceiverImageView == null) {
        break missingId;
      }

      id = R.id.receive_chat;
      TextView receiveChat = ViewBindings.findChildViewById(rootView, id);
      if (receiveChat == null) {
        break missingId;
      }

      return new CustomRecieveMessageBinding((RelativeLayout) rootView, imageChat,
          messageReceiverImageView, receiveChat);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
