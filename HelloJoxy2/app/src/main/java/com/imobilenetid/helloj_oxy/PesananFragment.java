package com.imobilenetid.helloj_oxy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.imobilenetid.helloj_oxy.MainActivity.mViewPager;


/**
 * A simple {@link Fragment} subclass.
 */
public class PesananFragment extends Fragment {

    TextView txtData;
    TextView txtDataRO;
    TextView txtDataTotalBelanja;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pesanan, container, false);

/*
        txtData = (TextView)rootView.findViewById(R.id.jumlah_galonhx);
        txtDataRO = (TextView)rootView.findViewById(R.id.jumlah_galonro);
        txtDataTotalBelanja = (TextView)rootView.findViewById(R.id.total_belanja);

        Bundle bundle=getArguments();
        txtData.setText(Integer.valueOf(bundle.getInt("intData")));
        txtDataRO.setText(Integer.valueOf(bundle.getInt("intDataRO")));
        txtDataTotalBelanja.setText(Integer.valueOf(bundle.getInt("intData"))*8000+Integer.valueOf(bundle.getInt("intDataRO"))*6000);



 */
        Button btnPembayaran = (Button) rootView.findViewById(R.id.button_pembayaran);

        btnPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mViewPager.setCurrentItem(3, true);
                //getActivity().getSupportFragmentManager().beginTransaction().remove(new PesananFragment());
                mViewPager.setCurrentItem(2, true);
                //enterPengirimanFragment();


            }
        });

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtData = (TextView)view.findViewById(R.id.jumlah_galonhx);
        txtDataRO = (TextView)view.findViewById(R.id.jumlah_galonro);
        txtDataTotalBelanja = (TextView)view.findViewById(R.id.total_belanja);


    }
/**/
    private void enterPengirimanFragment() {

        FragmentTransaction trans = getFragmentManager()
                .beginTransaction();

        trans.replace(R.id.fragment_mainLayout, new PengirimanFragment());



        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(null);

        trans.commit();
        //PengirimanFragment newFragment  = new PengirimanFragment();
        //FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //PesananFragment fragment = new PesananFragment();
        //FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Store the Fragment in stack
        //transaction.addToBackStack(null);
        //transaction.replace(R.id.container, newFragment).commit();
        //mViewPager.setCurrentItem(3, true);

    }





    protected void displayReceivedData(String message, String messageRO)
    {
        //Bundle bundle=getArguments();
        txtData.setText(message);
        txtDataRO.setText(messageRO);

        int txt1Value = Integer.parseInt(txtData.getText().toString());
        txt1Value = 8000 * txt1Value;
        int txt1ValueRO = Integer.parseInt(txtDataRO.getText().toString());
        txt1ValueRO = 6000 * txt1ValueRO;
        int txt1ValueROhx = txt1Value + txt1ValueRO;
        String messageTotal = "Rp." + String.valueOf(txt1ValueROhx);
        txtDataTotalBelanja.setText(messageTotal);


    }




}
