package com.hochtechnology.skeleton.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class AndyUtils {
    private static ProgressDialog progressDialog;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private static Dialog dialog;

    public static void removeSimpleProgressDialog() {
        try {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void openAppInfoSettings(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    public static void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void openActivity(Context context, Class<?> cls, boolean shouldFinish) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
        if (shouldFinish) {
            Activity activity = (Activity) context;
            activity.finish();
        }
    }

    public static void openActivityAndClearPreviousStack(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        clearActivityStack(intent);
        context.startActivity(intent);
    }

    private static void clearActivityStack(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public static void hideSatatusbar(Activity c) {
        c.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    public static boolean eMailValidation(String emailstring) {
        if (null == emailstring || emailstring.length() == 0) {
            return false;
        }
        Pattern emailPattern = Pattern
                .compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();
    }

    public static void hideKeyboardFromFragment(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

//    public static void showErrorToastMessage(Context context, float time, String message, EditText editText) {
//        AndyUtils.vibratePhone(context, time);
//        AndyUtils.showToast(message, context);
//        AndyUtils.setFocusOnEditText(context, editText);
//    }
//
//    public static void showErrorToastMessage(Context context, float time, String message) {
//        AndyUtils.vibratePhone(context, time);
//        AndyUtils.showToast(message, context);
//    }

    public static void setFocusOnEditText(Context context, EditText editText) {
        editText.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void ShowKeybord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 1);
    }

    public static RequestBody makeTextRequestBody(String text) {
        if (!TextUtils.isEmpty(text)) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("text/plain"), text);
            return requestFile;
        } else {
            return null;
        }
    }

    public static MultipartBody.Part makeImageMultipartParam(String picture, String parameter) {
        if (!TextUtils.isEmpty(picture)) {
            File file = new File(picture);
            RequestBody requestFile;
            try {
                if ((picture.substring(picture.length() - 3, picture.length())).equalsIgnoreCase("jpg")) {
                    requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
                } else if ((picture.substring(picture.length() - 3, picture.length())).equalsIgnoreCase("png")) {
                    requestFile = RequestBody.create(MediaType.parse("image/png"), file);
                } else if ((picture.substring(picture.length() - 4, picture.length())).equalsIgnoreCase("jpeg")) {
                    requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
                } else {
                    requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
                }
            } catch (Exception e) {
                requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            }
            MultipartBody.Part profile_picture_body = MultipartBody.Part.createFormData(parameter, file.getName(), requestFile);
            return profile_picture_body;
        } else {
            return null;
        }
    }

    public static String NumberFormateAmount(String s) {
        try {
            return NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(s)) + "";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return s;
        }
    }
}
