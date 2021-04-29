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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;

public class GithubFragment extends Fragment
{

    private EditText editTextGithub;
    private Button buttonSave, buttonLogout;

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_github, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        editTextGithub = view.findViewById(R.id.edit_email_github);
        buttonSave = view.findViewById(R.id.btn_save_github);
        buttonLogout = view.findViewById(R.id.btn_logout_github);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String githubField = editTextGithub.getText().toString();
                if (TextUtils.isEmpty(githubField))
                {
                    Toast.makeText(v.getContext(), "فين الإيميل يافندم هاه", Toast.LENGTH_SHORT).show();
                    editTextGithub.requestFocus();
                    return;
                }
                else
                {
                    signInWithGithub
                            (
                                    OAuthProvider.newBuilder("github.com")
                                    .addCustomParameter("login", githubField)
                                    .setScopes
                                            (
                                                    new ArrayList<String>()
                                                    {
                                                        {
                                                            add("user:email");
                                                        }
                                                    }
                                            )
                                    .build()
                            );
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

    private void signInWithGithub(OAuthProvider login)
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
                    .startActivityForSignInWithProvider(getActivity(), login)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getContext(), "عاااااش يا باشا تم التسجيل", Toast.LENGTH_SHORT).show();
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