package com.example.assignmentiwizards.utils;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.example.assignmentiwizards.R;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/23/03 10:38 PM
 * */
public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void visibleGone(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("imageSrc")
    public static void imageUrl(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.loading_anim)
                .into(view);
    }

    @BindingAdapter({"text", "type"})
    public static void text(TextView view, String text, String type) {
        view.setMovementMethod(LinkMovementMethod.getInstance());
        switch (type) {
            case "createdAt":
                String[] split = text.split("\\s+");
                view.setText(Html.fromHtml("<b>Posted: </b>"
                        + split[2] + " " + split[1] + " " + split[5]));
                break;

            case "job_desc":
                view.setText(Html.fromHtml("<b> Job Description: </b>\n" + text));
                break;

            case "company_url":
                String url = "<b>Company Url:</b>" + "<a href= " + text + "> " + text + "</a>";
                view.setText(Html.fromHtml(url));
                break;

            case "how_to_apply":
                view.setText(Html.fromHtml("<b>How to apply: </b>" + text));
                break;

            default:
                view.setText(text);
        }
    }
}
