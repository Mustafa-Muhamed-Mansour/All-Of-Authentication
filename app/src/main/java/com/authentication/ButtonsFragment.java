package com.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ButtonsFragment extends Fragment
{
    private Button buttonEmailAndPassword, buttonPhone, buttonGoogle;
    private Button buttonFacebook, buttonTwitter, buttonGithub;
    private Button buttonYahoo, buttonMicrosoft, buttonApple;
    private Button buttonAnonymous;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_buttons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        buttonEmailAndPassword = view.findViewById(R.id.btn_email_and_password);
        buttonPhone = view.findViewById(R.id.btn_phone);
        buttonGoogle = view.findViewById(R.id.btn_google);
        buttonFacebook = view.findViewById(R.id.btn_facebook);
        buttonTwitter = view.findViewById(R.id.btn_twitter);
        buttonGithub = view.findViewById(R.id.btn_github);
        buttonYahoo = view.findViewById(R.id.btn_yahoo);
        buttonMicrosoft = view.findViewById(R.id.btn_microsoft);
        buttonApple = view.findViewById(R.id.btn_apple);
        buttonAnonymous = view.findViewById(R.id.btn_anonymous);

        buttonEmailAndPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.action_buttonsFragment_to_emailAndPasswordFragment);
            }
        });

        buttonPhone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.action_buttonsFragment_to_phoneFragment);
            }
        });

        buttonGoogle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.action_buttonsFragment_to_googleFragment);
            }
        });

        buttonFacebook.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.action_buttonsFragment_to_facebookFragment);
            }
        });

        buttonTwitter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.action_buttonsFragment_to_twitterFragment);
            }
        });

        buttonGithub.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.action_buttonsFragment_to_githubFragment);
            }
        });

        buttonYahoo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.action_buttonsFragment_to_yahooFragment);
            }
        });

        buttonMicrosoft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.action_buttonsFragment_to_microsoftFragment);
            }
        });

        buttonApple.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.action_buttonsFragment_to_appleFragment);
            }
        });

        buttonAnonymous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.action_buttonsFragment_to_anonymousFragment);
            }
        });


    }
}