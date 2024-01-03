// Generated by view binder compiler. Do not edit!
package com.example.oya.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.airbnb.lottie.LottieAnimationView;
import com.example.oya.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignUpBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final EditText OTP;

  @NonNull
  public final RelativeLayout activitySignUp;

  @NonNull
  public final LottieAnimationView buttonAnimation;

  @NonNull
  public final TextView buttonText;

  @NonNull
  public final RelativeLayout getOTP;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final EditText phonenumber;

  @NonNull
  public final RelativeLayout signuppanel;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final AppCompatButton verifyOTPBtn;

  private ActivitySignUpBinding(@NonNull RelativeLayout rootView, @NonNull EditText OTP,
      @NonNull RelativeLayout activitySignUp, @NonNull LottieAnimationView buttonAnimation,
      @NonNull TextView buttonText, @NonNull RelativeLayout getOTP, @NonNull ImageView imageView4,
      @NonNull EditText phonenumber, @NonNull RelativeLayout signuppanel,
      @NonNull TextView textView3, @NonNull AppCompatButton verifyOTPBtn) {
    this.rootView = rootView;
    this.OTP = OTP;
    this.activitySignUp = activitySignUp;
    this.buttonAnimation = buttonAnimation;
    this.buttonText = buttonText;
    this.getOTP = getOTP;
    this.imageView4 = imageView4;
    this.phonenumber = phonenumber;
    this.signuppanel = signuppanel;
    this.textView3 = textView3;
    this.verifyOTPBtn = verifyOTPBtn;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.OTP;
      EditText OTP = ViewBindings.findChildViewById(rootView, id);
      if (OTP == null) {
        break missingId;
      }

      RelativeLayout activitySignUp = (RelativeLayout) rootView;

      id = R.id.button_animation;
      LottieAnimationView buttonAnimation = ViewBindings.findChildViewById(rootView, id);
      if (buttonAnimation == null) {
        break missingId;
      }

      id = R.id.buttonText;
      TextView buttonText = ViewBindings.findChildViewById(rootView, id);
      if (buttonText == null) {
        break missingId;
      }

      id = R.id.getOTP;
      RelativeLayout getOTP = ViewBindings.findChildViewById(rootView, id);
      if (getOTP == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = ViewBindings.findChildViewById(rootView, id);
      if (imageView4 == null) {
        break missingId;
      }

      id = R.id.phonenumber;
      EditText phonenumber = ViewBindings.findChildViewById(rootView, id);
      if (phonenumber == null) {
        break missingId;
      }

      id = R.id.signuppanel;
      RelativeLayout signuppanel = ViewBindings.findChildViewById(rootView, id);
      if (signuppanel == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.verifyOTPBtn;
      AppCompatButton verifyOTPBtn = ViewBindings.findChildViewById(rootView, id);
      if (verifyOTPBtn == null) {
        break missingId;
      }

      return new ActivitySignUpBinding((RelativeLayout) rootView, OTP, activitySignUp,
          buttonAnimation, buttonText, getOTP, imageView4, phonenumber, signuppanel, textView3,
          verifyOTPBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}