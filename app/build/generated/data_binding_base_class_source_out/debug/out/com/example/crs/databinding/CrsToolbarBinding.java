// Generated by view binder compiler. Do not edit!
package com.example.crs.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import com.example.crs.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class CrsToolbarBinding implements ViewBinding {
  @NonNull
  private final Toolbar rootView;

  @NonNull
  public final Toolbar mainToolbar;

  private CrsToolbarBinding(@NonNull Toolbar rootView, @NonNull Toolbar mainToolbar) {
    this.rootView = rootView;
    this.mainToolbar = mainToolbar;
  }

  @Override
  @NonNull
  public Toolbar getRoot() {
    return rootView;
  }

  @NonNull
  public static CrsToolbarBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CrsToolbarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.crs_toolbar, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CrsToolbarBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    Toolbar mainToolbar = (Toolbar) rootView;

    return new CrsToolbarBinding((Toolbar) rootView, mainToolbar);
  }
}
