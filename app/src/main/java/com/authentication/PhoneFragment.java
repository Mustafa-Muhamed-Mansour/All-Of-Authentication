package com.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneFragment extends Fragment
{

    private EditText editTextPhone, editTextVerification;
    private Button buttonCode, buttonSave, buttonLogout;

    private FirebaseAuth firebaseAuth;

    private String verificationID;

    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        editTextPhone = view.findViewById(R.id.edit_phone);
        editTextVerification = view.findViewById(R.id.edit_verification);
        buttonCode = view.findViewById(R.id.btn_code);
        buttonSave = view.findViewById(R.id.btn_save_phone);
        buttonLogout = view.findViewById(R.id.btn_logout_phone);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonCode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String phoneField = editTextPhone.getText().toString();
                if (TextUtils.isEmpty(phoneField))
                {
                    Toast.makeText(v.getContext(), "إكتب الموبايل هااه", Toast.LENGTH_SHORT).show();
                    editTextPhone.requestFocus();
                    return;
                }
                else
                {
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(firebaseAuth)
                                    .setPhoneNumber(phoneField)
                                    .setTimeout(15L, TimeUnit.SECONDS)
                                    .setActivity(getActivity())
                                    .setCallbacks(callbacks)
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String codeField = editTextVerification.getText().toString();
                if (TextUtils.isEmpty(codeField))
                {
                    Toast.makeText(getContext(), "الكود فين طيب؟", Toast.LENGTH_SHORT).show();
                    editTextVerification.requestFocus();
                    return;
                }
                else
                {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, codeField);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
            {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)
            {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token)
            {
                verificationID = verificationId;
                resendingToken = token;
            }
        };

        buttonLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                firebaseAuth.signOut();
                getActivity().onBackPressed();
            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getContext(), "تمام جدأ أنت كدة سجلت معانا بالرقم ده", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }
                        else
                        {
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}