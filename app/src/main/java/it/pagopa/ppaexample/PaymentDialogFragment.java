package it.pagopa.ppaexample;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.core.PpaPaymentVm;

public class PaymentDialogFragment extends DialogFragment {
    private String amount;
    private String email;
    private String name;
    private String receiver;
    private String subject;

    static PaymentDialogFragment newInstance(PpaPaymentVm payment) {
        PaymentDialogFragment f = new PaymentDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("amount", payment.getAmount());
        args.putString("email", payment.getEmail());
        args.putString("name", payment.getName());
        args.putString("receiver", payment.getReceiver());
        args.putString("subject", payment.getSubject());
        f.setArguments(args);

        return f;
    }


    public PaymentDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amount = getArguments().getString("amount");
        email = getArguments().getString("email");
        name = getArguments().getString("name");
        receiver = getArguments().getString("receiver");
        subject = getArguments().getString("subject");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_payment_dialog, container, false);
        TextView tvReceiver = root.findViewById(R.id.tv_receiver);
        TextView tvSubject = root.findViewById(R.id.tv_subject);
        TextView tvAmount = root.findViewById(R.id.tv_amount);
        TextView tvThanks = root.findViewById(R.id.tv_thanks);
        ImageView ivClose = root.findViewById(R.id.iv_close);

        tvReceiver.setText(receiver);
        tvSubject.setText(subject);
        tvAmount.setText(amount);
        if (name != null && !TextUtils.isEmpty(name) && email != null && !TextUtils.isEmpty(email)) {
            tvThanks.setText("Grazie " + name + " hai ricevuto una email all'indirizzo " + email);
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentDialogFragment.this.dismiss();
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 0.90);
        int height = (int) (displaymetrics.heightPixels * 0.90);
        getDialog().getWindow().setLayout(width, height);
    }

}
