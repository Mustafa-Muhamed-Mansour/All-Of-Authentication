package com.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;

public class AppleFragment extends Fragment
{

    private Button buttonApple, buttonLogout;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_apple, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        buttonApple = view.findViewById(R.id.btn_save_apple);
        buttonLogout = view.findViewById(R.id.btn_logout_apple);
        firebaseAuth = FirebaseAuth.getInstance();

        buttonApple.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signInWithApple
                        (
                                OAuthProvider
                                        .newBuilder("apple.com")
                                        .addCustomParameter("prompt", "login")
                                        .addCustomParameter("locale", "en")
                                        .setScopes
                                                (
                                                        new ArrayList<String>()
                                                        {
                                                            {
                                                                add("email");
                                                                add("name");
                                                            }
                                                        }
                                                )
                                        .build()
                        );
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

    private void signInWithApple(OAuthProvider build)
    {
        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        if (pendingResultTask != null)
        {
            pendingResultTask
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getContext(), "موجود يافندم", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            firebaseAuth
                    .startActivityForSignInWithProvider(getActivity(), build)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getContext(), "أنت أكيد غني غنا فاااااحش", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}