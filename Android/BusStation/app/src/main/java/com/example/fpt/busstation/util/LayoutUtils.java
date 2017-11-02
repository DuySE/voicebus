package com.example.fpt.busstation.util;

import android.util.Log;
import android.widget.TextView;

/**
 * Created by Vi Nguyen on 26/10/2017.
 */

public class LayoutUtils {
    public static String handleDisplayTime(Double duration) {
        String timeUnit = "phút";
        if (duration >= 100) {
            duration = duration / 100;
            timeUnit = "giờ";
            return String.valueOf((double) Math.round(duration)) + "\n" + timeUnit;
        }
        return String.valueOf((int) Math.floor(duration)) + "\n" + timeUnit;
    }

    public static int setLinesForTextView(String content, int limit, TextView textView) {
        if (content != null || content.length() > 0) {
            if (content.length() > limit) {
                Log.d(">>>>>Content", "" + content);
                Log.d(">>>>>Content", "length" + content.length());
                int remainChar = content.length() % limit;
                int numberLine = content.length() / limit;
                Log.d(">>>>> numberline", "" + numberLine);
                Log.d(">>>>> remain char", "" + remainChar);
                if (remainChar > 0) {
                    return numberLine + 1;
                } else {
                    return numberLine;
                }
            }
        }
        return 1;
    }

}
