package com.skynet.fish_shop.view.main.profile;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.skynet.fish_shop.App;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.db.User;
import com.skynet.fish_shop.view.extension.WarningDialogView;
import com.skynet.fish_shop.view.main.home.HomeFragment;

import java.util.List;

public class ProfileFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        bottomNavigationView = this.getActivity().findViewById(R.id.bottom_navigation);
        setConfirmButton();
        new CheckUserData(view).execute();
        return view;
    }

    private void setConfirmButton() {
        View confirmButton = view.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(view1 -> {
            String firstName = ((EditText) view.findViewById(R.id.first_name)).getText().toString();
            String lastName = ((EditText) view.findViewById(R.id.last_name)).getText().toString();
            String thirdName = ((EditText) view.findViewById(R.id.third_name)).getText().toString();
            String phoneNumber = ((EditText) view.findViewById(R.id.phone_input)).getText().toString();
            String email = ((EditText) view.findViewById(R.id.email)).getText().toString();
            if (!checkUserData(new String[]{firstName, lastName, thirdName, phoneNumber, email})) {
                new WarningDialogView("Ошибка", "Заполните все поля").show(getActivity().getSupportFragmentManager(), "");
            } else {
                User user = new User(1, firstName, lastName, thirdName, phoneNumber, email);
                new SaveUserToDbTask(this::openHomeFragment).execute(user);
            }
        });
    }

    private boolean checkUserData(String[] parameters) {
        for (String parameter : parameters) {
            if (parameter == null || parameter.equals("")) return false;
        }
        return true;
    }

    private void openHomeFragment() {
        Toast.makeText(view.getContext(), "Данные сохранены", Toast.LENGTH_SHORT).show();
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
        bottomNavigationView.setSelectedItemId(R.id.action_main);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.replace(R.id.main_relative_layout, homeFragment);
        ft.commit();
    }

    private interface BackToHome {
        void backToHome();
    }

    private static class SaveUserToDbTask extends AsyncTask<User, Void, Void> {

        private final BackToHome backToHome;

        private SaveUserToDbTask(BackToHome backToHome) {
            this.backToHome = backToHome;
        }

        @Override
        protected Void doInBackground(User... users) {
            App.getInstance().getDatabase().userDao().insert(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            backToHome.backToHome();
        }
    }

    private static class CheckUserData extends AsyncTask<Void, Void, Void> {

        private User user;
        private final View view;

        private CheckUserData(View view) {
            this.view = view;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<User> userList = App.getInstance().getDatabase().userDao().getAll();
            if (userList.size() != 0) {
                user = userList.get(0);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (user != null) {
                ((EditText) view.findViewById(R.id.first_name)).setText(user.firstName);
                ((EditText) view.findViewById(R.id.last_name)).setText(user.lastName);
                ((EditText) view.findViewById(R.id.third_name)).setText(user.thirdName);
                ((EditText) view.findViewById(R.id.phone_input)).setText(user.phoneNumber);
                ((EditText) view.findViewById(R.id.email)).setText(user.email);
            }
        }
    }
}