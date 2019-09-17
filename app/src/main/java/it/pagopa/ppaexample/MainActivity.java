package it.pagopa.ppaexample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.wallet.core.PpaPaymentVm;
import com.wallet.modules.ppa.PpaSessionCallback;

import java.util.ArrayList;
import java.util.UUID;

import it.pagopa.PagoPaCore;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {
ArrayAdapter<String> dataAdapter;
    private static  String serverUrl = "https://acardste.vaservices.eu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        MainFragment fragment = new MainFragment();
        FrameLayout flFrameContainer = findViewById(R.id.fl_fragment_container);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(flFrameContainer.getId(), fragment, "main");
        ft.commit();

        PagoPaCore.init(this,serverUrl);
    }

    @Override
    public void onFragmentInteraction(String idPayment) {

        PagoPaCore.getInstance().startPayment(this, idPayment,
                new PpaSessionCallback() {
                    @Override
                    public void onPaymentCompleted(PpaPaymentVm payment) {
                        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                        if (prev != null) {
                            ft.remove(prev);
                        }

                        // Create and show the dialog.
                        final PaymentDialogFragment newFragment = PaymentDialogFragment.newInstance(payment);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                newFragment.show(ft, "dialog");
                            }
                        }, 500);
                    }

                    @Override
                    public void onPaymentFailed() {
                    }

                    @Override
                    public void onPaymentAbortedByUser() {
                    }
                });
    }
}