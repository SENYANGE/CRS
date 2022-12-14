// Generated by view binder compiler. Do not edit!
package com.example.crs.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.crs.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class WitnessDialogBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton btnDelete;

  @NonNull
  public final ImageButton btnEdit;

  @NonNull
  public final TextView cMessage;

  @NonNull
  public final ImageButton callPolice;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView txt;

  private WitnessDialogBinding(@NonNull RelativeLayout rootView, @NonNull ImageButton btnDelete,
      @NonNull ImageButton btnEdit, @NonNull TextView cMessage, @NonNull ImageButton callPolice,
      @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3,
      @NonNull TextView txt) {
    this.rootView = rootView;
    this.btnDelete = btnDelete;
    this.btnEdit = btnEdit;
    this.cMessage = cMessage;
    this.callPolice = callPolice;
    this.textView = textView;
    this.textView2 = textView2;
    this.textView3 = textView3;
    this.txt = txt;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static WitnessDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static WitnessDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.witness_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static WitnessDialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_delete;
      ImageButton btnDelete = ViewBindings.findChildViewById(rootView, id);
      if (btnDelete == null) {
        break missingId;
      }

      id = R.id.btn_edit;
      ImageButton btnEdit = ViewBindings.findChildViewById(rootView, id);
      if (btnEdit == null) {
        break missingId;
      }

      id = R.id.c_message;
      TextView cMessage = ViewBindings.findChildViewById(rootView, id);
      if (cMessage == null) {
        break missingId;
      }

      id = R.id.call_police;
      ImageButton callPolice = ViewBindings.findChildViewById(rootView, id);
      if (callPolice == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.txt;
      TextView txt = ViewBindings.findChildViewById(rootView, id);
      if (txt == null) {
        break missingId;
      }

      return new WitnessDialogBinding((RelativeLayout) rootView, btnDelete, btnEdit, cMessage,
          callPolice, textView, textView2, textView3, txt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
