// Generated by view binder compiler. Do not edit!
package com.example.oya.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.oya.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ChatItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CardView cardviewofuser;

  @NonNull
  public final ImageView imageviewofuser;

  @NonNull
  public final LinearLayout layout;

  @NonNull
  public final TextView title;

  private ChatItemBinding(@NonNull LinearLayout rootView, @NonNull CardView cardviewofuser,
      @NonNull ImageView imageviewofuser, @NonNull LinearLayout layout, @NonNull TextView title) {
    this.rootView = rootView;
    this.cardviewofuser = cardviewofuser;
    this.imageviewofuser = imageviewofuser;
    this.layout = layout;
    this.title = title;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ChatItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ChatItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.chat_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ChatItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardviewofuser;
      CardView cardviewofuser = ViewBindings.findChildViewById(rootView, id);
      if (cardviewofuser == null) {
        break missingId;
      }

      id = R.id.imageviewofuser;
      ImageView imageviewofuser = ViewBindings.findChildViewById(rootView, id);
      if (imageviewofuser == null) {
        break missingId;
      }

      LinearLayout layout = (LinearLayout) rootView;

      id = R.id.title;
      TextView title = ViewBindings.findChildViewById(rootView, id);
      if (title == null) {
        break missingId;
      }

      return new ChatItemBinding((LinearLayout) rootView, cardviewofuser, imageviewofuser, layout,
          title);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}