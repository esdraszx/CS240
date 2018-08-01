package layout;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.client.R;


import Request.LoginRequest;
import Request.RegisterRequest;

import Tasks.RegisterTask;
import Tasks.SignInTask;


public class LoginRegisterFragment extends Fragment {


    private LoginRequest myLoginRequest;
    private RegisterRequest myRegisterRequest;
    private EditText myServerHostEditField;
    private EditText myServerPortEditField;
    private EditText myUserNameEditField;
    private EditText myPasswordEditField;
    private EditText myFirstNameEditField;
    private EditText myLastNameEditField;
    private EditText myEmailEditField;
    private RadioGroup genderGroup;
    private Button mySignInButton;
    private Button myRegisterButton;
    private static Context whereICameFrom;
    private String serverHost;
    private String serverPort;

    //private OnFragmentInteractionListener mListener;

    public LoginRegisterFragment() {

    }

    public static LoginRegisterFragment newInstance(Context in) {
        whereICameFrom = in;
        LoginRegisterFragment fragment = new LoginRegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myLoginRequest = new LoginRequest();
        myRegisterRequest = new RegisterRequest();
    }

    private void checkLoginFieldsForEmptyValues(){
        String s1 = myServerHostEditField.getText().toString();
        String s2 = myServerPortEditField.getText().toString();
        String s3 = myUserNameEditField.getText().toString();
        String s4 = myPasswordEditField.getText().toString();


        if(s1.equals("")|| s2.equals("") || s3.equals("") || s4.equals("")){
            mySignInButton.setEnabled(false);
        } else {
            mySignInButton.setEnabled(true);
        }
    }

    private void checkRegisterFieldsForEmptyValues(){
        String s1 = myServerHostEditField.getText().toString();
        String s2 = myServerPortEditField.getText().toString();
        String s3 = myUserNameEditField.getText().toString();
        String s4 = myPasswordEditField.getText().toString();
        String s5 = myFirstNameEditField.getText().toString();
        String s6 = myLastNameEditField.getText().toString();
        String s7 = myEmailEditField.getText().toString();

        if(s1.equals("")|| s2.equals("") || s3.equals("") || s4.equals("") || s5.equals("") || s6.equals("") || s7.equals("")){
            myRegisterButton.setEnabled(false);
        } else {
            myRegisterButton.setEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myRegisterRequest.setGender("m");
        View v = inflater.inflate(R.layout.fragment_login_register, container, false);

        mySignInButton = (Button) v.findViewById(R.id.signInbutton);
        mySignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInButtonClicked();
            }
        });

        myRegisterButton = (Button) v.findViewById(R.id.registerButton);
        myRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterButtonClicked();
            }
        });


        myServerHostEditField = (EditText) v.findViewById(R.id.serverHostEditText);
        myServerHostEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                myLoginRequest.setServerHost(s.toString());
                myRegisterRequest.setServerHost(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginFieldsForEmptyValues();
                checkRegisterFieldsForEmptyValues();
            }
        });

        myServerPortEditField = (EditText) v.findViewById(R.id.serverPortEditText);
        myServerPortEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }


            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                myLoginRequest.setServerPort(s.toString());
                myRegisterRequest.setServerPort(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginFieldsForEmptyValues();
                checkRegisterFieldsForEmptyValues();
            }
        });


        myUserNameEditField = (EditText) v.findViewById(R.id.userNameEditText);
        myUserNameEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                myLoginRequest.setUserName(s.toString());
                myRegisterRequest.setUserName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginFieldsForEmptyValues();
                checkRegisterFieldsForEmptyValues();
            }
        });

        myPasswordEditField = (EditText) v.findViewById(R.id.passwordEditText);
        myPasswordEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                myLoginRequest.setPassword(s.toString());
                myRegisterRequest.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginFieldsForEmptyValues();
                checkRegisterFieldsForEmptyValues();
            }
        });

        checkLoginFieldsForEmptyValues();


        //From here on out it's only for register requests

        myFirstNameEditField = (EditText) v.findViewById(R.id.firstNameEditText);
        myFirstNameEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                myRegisterRequest.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkRegisterFieldsForEmptyValues();
            }
        });

        myLastNameEditField = (EditText) v.findViewById(R.id.lastNameEditText);
        myLastNameEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                myRegisterRequest.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkRegisterFieldsForEmptyValues();
            }
        });

        myEmailEditField = (EditText) v.findViewById(R.id.emailEditText);
        myEmailEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                myRegisterRequest.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkRegisterFieldsForEmptyValues();
            }
        });

        genderGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.femaleRadioButton) {
                    myRegisterRequest.setGender("f");
                }
                if (checkedId == R.id.maleRadioButton) {
                    myRegisterRequest.setGender("m");
                }
            }
        });

        checkRegisterFieldsForEmptyValues();



        return v;
    }


    private void onSignInButtonClicked() {
        SignInTask signInTask = new SignInTask(this,whereICameFrom);
        signInTask.execute(myLoginRequest);
    }

    private void onRegisterButtonClicked(){
        RegisterTask registerTask = new RegisterTask(this,whereICameFrom);
        registerTask.execute(myRegisterRequest);
    }
/*
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction();
    }
*/

}
































/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    */


/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginRegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */