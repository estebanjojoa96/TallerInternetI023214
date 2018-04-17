package com.example.estebanjojoa.tallerinterneti023214.Parser;

import com.example.estebanjojoa.tallerinterneti023214.Model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ESTEBAN JOJOA on 17/04/2018.
 */

public class Json {

    public static List<Users> getData(String content) throws JSONException {

        JSONArray jsonArray = new JSONArray(content);

        List<Users> UsersList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject item = jsonArray.getJSONObject(i);

            Users users = new Users();
            users.setCodigo(item.getString("codigo"));
            users.setNombre(item.getString("nombre"));
            users.setEdad(item.getString("edad"));
            users.setCorreo(item.getString("correo"));
            users.setPass(item.getString("pass"));

            UsersList.add(users);

        }

        return UsersList;


    }
}