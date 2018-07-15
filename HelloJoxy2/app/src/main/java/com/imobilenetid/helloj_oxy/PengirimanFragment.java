package com.imobilenetid.helloj_oxy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class PengirimanFragment extends Fragment {

    private DatabaseReference mDatabase;

    private EditText mDisplayName;

    private EditText mdataAlamat;

    private EditText mdataAlamatLengkap;

    private EditText mPhoneNumber;



    public PengirimanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_pengiriman, container, false);

        /*
        Initialize Firebase Real-time DB reference
         */
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDisplayName = (EditText)rootView.findViewById(R.id.nama_penerima);
        mdataAlamat = (EditText)rootView.findViewById(R.id.data_alamat);
        mdataAlamatLengkap = (EditText)rootView.findViewById(R.id.data_alamatlengkap);
        mPhoneNumber = (EditText)rootView.findViewById(R.id.data_no_hp);
        Button buttonSelesai = (Button) rootView.findViewById(R.id.button_selesai);

        /*
        Show current profile information
         */
        mDisplayName.setText(AppSharedPreferences.getUserDisplayName(getActivity()));
        mdataAlamat.setText(AppSharedPreferences.getUserDataAlamat(getActivity()));
        mdataAlamatLengkap.setText(AppSharedPreferences.getUserDataAlamatLengkap(getActivity()));
        mPhoneNumber.setText(AppSharedPreferences.getUserPhoneNumber(getActivity()));

        buttonSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDisplayName.getText().toString().trim().equals("") || mdataAlamat.getText().toString().trim().equals("") || mdataAlamatLengkap.getText().toString().trim().equals("") || mPhoneNumber.getText().toString().trim().equals("")) {
                    mDisplayName.setError("Nama Penerima is required!");
                    mdataAlamat.setError("Alamat is required!");
                    mdataAlamatLengkap.setError("Alamat Lengkap is required!");
                    mPhoneNumber.setError("Nomor Handphone is required!");
                }
                else {

                    Toast toast = Toast.makeText(
                            //getActivity(),"Custom Toast From Fragment",Toast.LENGTH_LONG
                            getActivity().getApplicationContext(), "Air Galon akan dikirimkan secepatnya ke alamat anda", Toast.LENGTH_LONG
                    );
                    // Set the Toast display position layout center
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    // Finally, show the toast
                    toast.show();


                }
            }
        });
        return rootView;
    }

}
