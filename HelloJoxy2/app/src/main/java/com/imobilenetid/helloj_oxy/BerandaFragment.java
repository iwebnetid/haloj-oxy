package com.imobilenetid.helloj_oxy;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static com.imobilenetid.helloj_oxy.MainActivity.mViewPager;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment{

    private EditText quantityHx;
    private EditText quantityRo;

    private int qtyhx, qtyro = 0;

    SendMessage SM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        /*
        Initiate fragment layout
         */
        View rootView = inflater.inflate(R.layout.fragment_beranda, container, false);



        quantityHx = (EditText) rootView.findViewById(R.id.jumlah_galon_hx);


        Button actionMinus = (Button) rootView.findViewById(R.id.button_minushx);
        actionMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qtyhx > 0) {
                    qtyhx--;
                }
                quantityHx.setText(qtyhx + "");
            }
        });

        Button actionPlus = (Button) rootView.findViewById(R.id.button_plushx);
        actionPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qtyhx++;
                quantityHx.setText(qtyhx + "");
            }
        });


        quantityRo = (EditText) rootView.findViewById(R.id.jumlah_galon_ro);


        Button actionMinusRo = (Button) rootView.findViewById(R.id.button_minusro);
        actionMinusRo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qtyro > 0) {
                    qtyro--;
                }
                quantityRo.setText(qtyro + "");
            }
        });

        Button actionPlusRo = (Button) rootView.findViewById(R.id.button_plusro);
        actionPlusRo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qtyro++;
                quantityRo.setText(qtyro + "");
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnPassData = (Button) view.findViewById(R.id.button_pesan);
        final EditText inData = (EditText) view.findViewById(R.id.jumlah_galon_hx);
        final EditText inDataRO = (EditText) view.findViewById(R.id.jumlah_galon_ro);
        btnPassData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SM.sendData(inData.getText().toString().trim(),inDataRO.getText().toString().trim());
                mViewPager.setCurrentItem(1, true);
                //enterNextFragment();


            }
        });

    }

    private void enterNextFragment() {
        PesananFragment a2Fragment = new PesananFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_mainLayout, a2Fragment).commit();
    }

    interface SendMessage {
        PesananFragment sendData(String message, String messageRO);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }




}
