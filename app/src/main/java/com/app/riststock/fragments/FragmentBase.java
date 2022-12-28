package com.app.riststock.fragments;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FragmentBase extends Fragment {

    protected int visible = View.VISIBLE;
    protected int gone = View.GONE;

    public static void hideSoftKeyboard(Activity activity) {
        try {

            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void Toast(String... msg) {

        String msgs = "";
        if (msg != null)
            for (String s : msg) {
                msgs += s;
            }
        Toast.makeText(getActivity(), msgs, Toast.LENGTH_SHORT).show();
    }

    public void Toast(int resId) {

        try {

            Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
//                    System.out.println("Log event Action " + event.getAction());
                    if (event.getAction() != MotionEvent.ACTION_SCROLL) {
                        hideSoftKeyboard(getActivity());
                    }

                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }


//    public void saveCart() {
//        SharedPManger sharedPManger = new SharedPManger(getActivity());
//        String cartJson = new Gson().toJson(GlobalData.CartList);
//
//        sharedPManger.SetData(GlobalData.KEY_CART, cartJson);
//    }
}
