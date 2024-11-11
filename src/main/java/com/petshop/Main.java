package com.petshop;
import com.petshop.core.pages.Login;
import com.petshop.db.InsertDB;
import com.petshop.db.QueryDB;
import com.petshop.db.SetDB;
import com.petshop.models.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] in) throws SQLException {
        new SetDB();
        Login.start();
//        InsertDB.insertTutor(
//                "49598299821",
//                "Gabriel Siqueira",
//                "2004-05-18",
//                "Rua Marijó",
//                "+55 11 910518642",
//                "rsiqueira@barbie.com"
//        );
        InsertDB.insertVeterinario(
                "49598299822",
                "Carlos Manuel",
                "2004-07-02",
                "Rua Kamelo",
                "+55 11 950873306",
                "cman@lan.com",
                "Dentista",
                "13251361"
        );
//
//        InsertDB.insertPet(
//                "toto",
//                "2022-04-01",
//                "spitz",
//                "cachorro",
//                "49598299821"
//        );
//        InsertDB.insertCirurgia(
//                "Uretra",
//                "Cirurgia complexa",
//                "2024-12-23",
//                "49598299822",
//                1
//        );
//        InsertDB.insertConsulta(
//                "Emergencial",
//                "Emergencia camelo",
//                "2024-11-08",
//                "49598299822",
//                1
//        );
//        InsertDB.insertEvento(
//                "2024-12-10",
//                "Confraternização",
//                "Confra",
//                "49598299822"
//        );

        ArrayList<Vet> vet = QueryDB.getAllVet();
        ArrayList<Pet> pet = QueryDB.getAllPet();
        ArrayList<Evento> evento = QueryDB.getAllEvento();
        ArrayList<Consulta> consulta = QueryDB.getAllConsulta();
        ArrayList<Cirurgia> cirurgia = QueryDB.getAllCirurgia();
        ArrayList<Tutor> tutor = QueryDB.getAllTutor();

        for (Vet v : vet) {
            System.out.println("vet");
            System.out.println(v.getName());
            System.out.println(v.getBorn());
        }

        for (Pet p : pet) {
            System.out.println("pet");
            System.out.println(p.getName());
            System.out.println(p.getTutor().getName());
        }

        for (Evento e : evento) {
            System.out.println("evento");
            System.out.println(e.getDescricao());
            System.out.println(e.getResponsavel().getName());
        }

        for (Consulta c : consulta) {
            System.out.println("consulta");
            System.out.println(c.getTipo());
            System.out.println(c.getVeterinario().getName());
        }

        for (Cirurgia c : cirurgia) {
            System.out.println("cirurgia");
            System.out.println(c.getTipo());
            System.out.println(c.getVeterinario().getName());
        }

        for (Tutor t : tutor) {
            System.out.println("tutor");
            System.out.println(t.getName());
            System.out.println(t.getEmail());
        }

//        InsertDB.insertTutor("123123456", "tes", "2003-02-10", "rua", "1212121", "@");
//        InsertDB.insertPet("teste", "2024-03-03", "spitz", "seila", "123123456");
//        ArrayList<Pet> pets = QueryDB.getAllPet();
//        for (Pet p : pets) {
//            System.out.println(p.getTutor().getBorn());
//        }
    }
}