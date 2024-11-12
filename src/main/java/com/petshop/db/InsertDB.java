package com.petshop.db;

import com.petshop.models.Pet;
import com.petshop.models.Tutor;
import com.petshop.models.Vet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertDB extends MethodDB{
    public static void insertTutor(String cpf, String name, String born, String address, String phone, String email) {
        String insertCommand = "INSERT INTO tutor (cpf, name, born, address, phone, email) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertCommand)) {
            // Set parameters for the query
            stmt.setString(1, cpf);
            stmt.setString(2, name);
            stmt.setString(3, born);
            stmt.setString(4, address);
            stmt.setString(5, phone);
            stmt.setString(6, email);

            // Execute the insert command
            stmt.executeUpdate();
            System.out.println("Tutor " + name + " successfully inserted.");
        } catch (SQLException e) {
            System.out.println("Error inserting " + name + ": " + e.getMessage());
        }
    }

    public static void insertVeterinario(String cpf, String name, String born, String address,
                                         String phone, String email, String especialidade, String crmv) {
        String insertCommand = "INSERT INTO veterinario (cpf, name, born, address, phone, email, especialidade, crmv) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertCommand)) {
            // Set parameters for the query
            stmt.setString(1, cpf);
            stmt.setString(2, name);
            stmt.setString(3, born);
            stmt.setString(4, address);
            stmt.setString(5, phone);
            stmt.setString(6, email);
            stmt.setString(7, especialidade);
            stmt.setString(8, crmv);

            // Execute the insert command
            stmt.executeUpdate();
            System.out.println("Vet " + name + " successfully inserted.");
        } catch (SQLException e) {
            System.out.println("Error inserting " + name + ": " + e.getMessage());
        }
    }

    public static int insertPet(String name, String born, String raca, String especie, String filepath, String tutor) {
        String insertCommand = "INSERT INTO pet (name, born, raca, especie, filepath, tutor) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertCommand)) {
            // Set parameters for the query
            stmt.setString(1, name);
            stmt.setString(2, born);
            stmt.setString(3, raca);
            stmt.setString(4, especie);
            stmt.setString(5, filepath);
            stmt.setString(6, tutor);

            Tutor tutorInsert = QueryDB.getAllTutor().stream().filter(tutorQuery -> {
                return tutorQuery.getCpf().equals(tutor);
            }).toList().getFirst();

            if (tutorInsert != null) {
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int newId = generatedKeys.getInt(1); // Or .getInt(1) if the ID is an int
                            return newId;
                        }
                    }
                }
                System.out.println("Pet " + name + " successfully inserted.");
            } else {
                throw new Exception("Tutor" + tutor + " not found");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting " + name + ": " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static void insertEvento(String date, String datahora, String descricao, String responsavel) {
        String insertCommand = "INSERT INTO evento (date, datahora, descricao, responsavel) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertCommand)) {
            // Set parameters for the query
            stmt.setString(1, date);
            stmt.setString(2, datahora);
            stmt.setString(3, descricao);
            stmt.setString(4, responsavel);

            Vet vetInsert = QueryDB.getAllVet().stream().filter(vetQuery -> {
                return vetQuery.getCpf().equals(responsavel);
            }).toList().getFirst();

            if (vetInsert != null) {
                stmt.executeUpdate();
            } else {
                throw new Exception("Veterinario" + responsavel + " not found");
            }

            System.out.println("Event " + descricao + " successfully inserted.");
        } catch (SQLException e) {
            System.out.println("Error inserting " + descricao + ": " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertConsulta(String tipo, String descricao, String date, String datahora, String veterinario, int pet) {
        String insertCommand = "INSERT INTO consulta (tipo, descricao, date, datahora, veterinario, pet) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertCommand)) {
            // Set parameters for the query
            stmt.setString(1, tipo);
            stmt.setString(2, descricao);
            stmt.setString(3, date);
            stmt.setString(4, datahora);
            stmt.setString(5, veterinario);
            stmt.setInt(6, pet);

            Vet vetInsert = QueryDB.getAllVet().stream().filter(vetQuery -> {
                return vetQuery.getCpf().equals(veterinario);
            }).toList().getFirst();

            Pet petInsert = QueryDB.getAllPet().stream().filter(petQuery -> {
                return petQuery.getId() == pet;
            }).toList().getFirst();

            if (vetInsert != null && petInsert != null) {
                stmt.executeUpdate();
                System.out.println("Cirurgia " + tipo + " successfully inserted.");
            } else if (vetInsert != null) {
                throw new Exception("Veterinario" + veterinario + " not found");
            } else if (petInsert != null) {
                throw new Exception("Pet" + pet + " not found");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting " + tipo + ": " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertCirurgia(String tipo, String descricao, String date, String datahora, String veterinario, int pet) {
        String insertCommand = "INSERT INTO cirurgia (tipo, descricao, date, datahora, veterinario, pet) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertCommand)) {
            // Set parameters for the query
            stmt.setString(1, tipo);
            stmt.setString(2, descricao);
            stmt.setString(3, date);
            stmt.setString(4, datahora);
            stmt.setString(5, veterinario);
            stmt.setInt(6, pet);

            Vet vetInsert = QueryDB.getAllVet().stream().filter(vetQuery -> {
                return vetQuery.getCpf().equals(veterinario);
            }).toList().getFirst();

            Pet petInsert = QueryDB.getAllPet().stream().filter(petQuery -> {
                return petQuery.getId() == pet;
            }).toList().getFirst();

            if (vetInsert != null && petInsert != null) {
                stmt.executeUpdate();
                System.out.println("Cirurgia " + tipo + " successfully inserted.");
            } else if (vetInsert != null) {
                throw new Exception("Veterinario" + veterinario + " not found");
            } else if (petInsert != null) {
                throw new Exception("Pet" + pet + " not found");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting  " + tipo + ": " + e.getMessage());;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
