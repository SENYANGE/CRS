// Generated by view binder compiler. Do not edit!
package com.example.crs.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.crs.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySettingsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton cameraBtn;

  @NonNull
  public final CircleImageView profileImage1;

  @NonNull
  public final EditText status;

  @NonNull
  public final Button updateBtn;

  @NonNull
  public final EditText username;

  private ActivitySettingsBinding(@NonNull RelativeLayout rootView, @NonNull ImageButton cameraBtn,
      @NonNull CircleImageView profileImage1, @NonNull EditText status, @NonNull Button updateBtn,
      @NonNull EditText username) {
    this.rootView = rootView;
    this.cameraBtn = cameraBtn;
    this.profileImage1 = profileImage1;
    this.status = status;
    this.updateBtn = updateBtn;
    this.username = username;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_settings, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySettingsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.camera_btn;
      ImageButton cameraBtn = ViewBindings.findChildViewById(rootView, id);
      if (cameraBtn == null) {
        break missingId;
      }

      id = R.id.profile_image1;
      CircleImageView profileImage1 = ViewBindings.findChildViewById(rootView, id);
      if (profileImage1 == null) {
        break missingId;
      }

      id = R.id.status;
      EditText status = ViewBindings.findChildViewById(rootView, id);
      if (status == null) {
        break missingId;
      }

      id = R.id.update_btn;
      Button updateBtn = ViewBindings.findChildViewById(rootView, id);
      if (updateBtn == null) {
        break missingId;
      }

      id = R.id.username;
      EditText username = ViewBindings.findChildViewById(rootView, id);
      if (username == null) {
        break missingId;
      }

      return new ActivitySettingsBinding((RelativeLayout) rootView, cameraBtn, profileImage1,
          status, updateBtn, username);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
