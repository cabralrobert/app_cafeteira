package br.ufc.robertcabral.menulateral;

import java.io.Serializable;

/**
 * Created by robertcabral on 11/09/16.
 */
public class User implements Serializable {

    public String id = null;
    public String nome = null;

    public User(String id, String nome){
        this.nome = nome;
        this.id = id;
    }

}
