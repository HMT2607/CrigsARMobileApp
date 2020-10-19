package com.example.crigsarmobileapp;

import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.ux.ArFragment;

public class CustomerArFragment extends ArFragment{

    @Override
    protected Config getSessionConfiguration(Session session) {
        Config config = new Config(session);
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        config.setFocusMode(Config.FocusMode.AUTO);
        session.configure(config);

        this.getArSceneView().setupSession(session);

        ((ArActivity) getActivity()).setUpDataBase(config, session);

        return config;
    }
}
