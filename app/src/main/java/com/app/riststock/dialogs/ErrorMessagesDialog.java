package com.app.riststock.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.riststock.R;


public class ErrorMessagesDialog {
//        Activity activity;

    static ErrorDialog errorDialog;

    public static ErrorDialog with(Activity activity) {

        if (errorDialog == null) {
            errorDialog = new ErrorDialog(activity);
            return errorDialog;
        } else {
            return errorDialog;
        }

    }


    public static class ErrorDialog extends Dialog {

        private TextView messageTxt;
        private LinearLayout okBtn;
        static Activity activity;

        protected ErrorDialog(Activity activity) {
            super(activity);

            ErrorDialog.activity = activity;

            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            requestWindowFeature(Window.FEATURE_NO_TITLE); //before

            setContentView(R.layout.dialog_my_info);

            messageTxt = findViewById(R.id.messageTxt);
            okBtn = findViewById(R.id.okBtn);

            okBtn.setOnClickListener(view -> dismiss());

            show();

        }

        private ErrorDialog getDialog() {
            return this;
        }

        public static ErrorDialog setMessages(String... messages) {
            String msg = "";
            if (msg != null) {
                for (String message : messages) {
                    msg += message + "\n";
                }
                errorDialog.messageTxt.setText(msg);
            } else {
                errorDialog.messageTxt.setText(activity.getString(R.string.fail_to_get_data));
            }

//            if (customeDialog == null) {
//                return customeDialog;
//            } else {
            return errorDialog;
//            }

        }

        public static void build() {
            if (errorDialog != null) {
                errorDialog.show();
                errorDialog.setOnDismissListener(dialogInterface -> errorDialog = null);
            }

        }

    }
}
