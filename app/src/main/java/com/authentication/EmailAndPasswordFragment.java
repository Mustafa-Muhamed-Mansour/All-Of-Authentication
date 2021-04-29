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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailAndPasswordFragment extends Fragment
{
    private EditText editTextEmail, editTextPassword;
    private Button buttonSave, buttonLogout;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_email_and_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.edit_email);
        editTextPassword = view.findViewById(R.id.edit_passowrd);
        buttonSave = view.findViewById(R.id.btn_save_email_and_password);
        buttonLogout = view.findViewById(R.id.btn_logout_email_and_password);

        firebaseAuth = FirebaseAuth.getInstance();


        buttonSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String emailField = editTextEmail.getText().toString();
                String passwordField = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(emailField))
                {
                    Toast.makeText(v.getContext(), "اكتب يعم الايميل", Toast.LENGTH_SHORT).show();
                    editTextEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(passwordField))
                {
                    Toast.makeText(v.getContext(), "اكتب يعم الباسورد", Toast.LENGTH_SHORT).show();
                    editTextEmail.requestFocus();
                    return;
                }
                else
                {
                    firebaseAuth
                            .createUserWithEmailAndPassword(emailField, passwordField)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(v.getContext(), "مبروك يا سيدي سحلنا إيميلك خلاص", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(v.getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

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
}