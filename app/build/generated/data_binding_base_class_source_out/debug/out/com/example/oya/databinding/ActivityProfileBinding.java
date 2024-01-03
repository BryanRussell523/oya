// Generated by view binder compiler. Do not edit!
package com.example.oya.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.oya.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProfileBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton backbuttonofviewprofile;

  @NonNull
  public final TextView editprofile;

  @NonNull
  public final TextView myapptext;

  @NonNull
  public final ImageView profilepanel;

  @NonNull
  public final ProgressBar setprofileprogressbar;

  @NonNull
  public final Toolbar toolbarofviewprofile;

  @NonNull
  public final ImageView userimage;

  @NonNull
  public final CardView viewuserimage;

  @NonNull
  public final ImageView viewuserimageimageview;

  @NonNull
  public final EditText viewusername;

  private ActivityProfileBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton backbuttonofviewprofile, @NonNull TextView editprofile,
      @NonNull TextView myapptext, @NonNull ImageView profilepanel,
      @NonNull ProgressBar setprofileprogressbar, @NonNull Toolbar toolbarofviewprofile,
      @NonNull ImageView userimage, @NonNull CardView viewuserimage,
      @NonNull ImageView viewuserimageimageview, @NonNull EditText viewusername) {
    this.rootView = rootView;
    this.backbuttonofviewprofile = backbuttonofviewprofile;
    this.editprofile = editprofile;
    this.myapptext = myapptext;
    this.profilepanel = profilepanel;
    this.setprofileprogressbar = setprofileprogressbar;
    this.toolbarofviewprofile = toolbarofviewprofile;
    this.userimage = userimage;
    this.viewuserimage = viewuserimage;
    this.viewuserimageimageview = viewuserimageimageview;
    this.viewusername = viewusername;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backbuttonofviewprofile;
      ImageButton backbuttonofviewprofile = ViewBindings.findChildViewById(rootView, id);
      if (backbuttonofviewprofile == null) {
        break missingId;
      }

      id = R.id.editprofile;
      TextView editprofile = ViewBindings.findChildViewById(rootView, id);
      if (editprofile == null) {
        break missingId;
      }

      id = R.id.myapptext;
      TextView myapptext = ViewBindings.findChildViewById(rootView, id);
      if (myapptext == null) {
        break missingId;
      }

      id = R.id.profilepanel;
      ImageView profilepanel = ViewBindings.findChildViewById(rootView, id);
      if (profilepanel == null) {
        break missingId;
      }

      id = R.id.setprofileprogressbar;
      ProgressBar setprofileprogressbar = ViewBindings.findChildViewById(rootView, id);
      if (setprofileprogressbar == null) {
        break missingId;
      }

      id = R.id.toolbarofviewprofile;
      Toolbar toolbarofviewprofile = ViewBindings.findChildViewById(rootView, id);
      if (toolbarofviewprofile == null) {
        break missingId;
      }

      id = R.id.userimage;
      ImageView userimage = ViewBindings.findChildViewById(rootView, id);
      if (userimage == null) {
        break missingId;
      }

      id = R.id.viewuserimage;
      CardView viewuserimage = ViewBindings.findChildViewById(rootView, id);
      if (viewuserimage == null) {
        break missingId;
      }

      id = R.id.viewuserimageimageview;
      ImageView viewuserimageimageview = ViewBindings.findChildViewById(rootView, id);
      if (viewuserimageimageview == null) {
        break missingId;
      }

      id = R.id.viewusername;
      EditText viewusername = ViewBindings.findChildViewById(rootView, id);
      if (viewusername == null) {
        break missingId;
      }

      return new ActivityProfileBinding((ConstraintLayout) rootView, backbuttonofviewprofile,
          editprofile, myapptext, profilepanel, setprofileprogressbar, toolbarofviewprofile,
          userimage, viewuserimage, viewuserimageimageview, viewusername);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}