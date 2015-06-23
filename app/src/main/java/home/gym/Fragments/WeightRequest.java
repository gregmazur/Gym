package home.gym.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import home.gym.R;

/**
 * Created by greg on 21.06.15.
 */
public class WeightRequest extends DialogFragment implements TextView.OnEditorActionListener {
    private EditText weight;
    public static final String CURRENT_WEIGHT = "WEIGHT";

    public interface EditNameDialogListener {
        void onFinishEditDialog(String input);
    }

    public WeightRequest() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weight_request, container);
        weight = (EditText) view.findViewById(R.id.weight_current);
        getDialog().setTitle(R.string.weight_request_dialog_title);

        weight.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        weight.setOnEditorActionListener(this);
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            EditNameDialogListener listener = (EditNameDialogListener) getActivity();
            listener.onFinishEditDialog(weight.getText().toString());
            this.dismiss();
        }
        return false;
    }
}