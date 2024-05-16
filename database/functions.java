package com.tamik.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class functions {

    public void createTableFunction(Connection conn) throws SQLException {

        Statement stm = conn.createStatement();

        try {
            stm.executeUpdate("CREATE OR REPLACE FUNCTION CreateTable() \n" +
                    "RETURNS void \n" +
                    "AS \n" +
                    "$$ \n" +
                    "BEGIN \n" +
                    "EXECUTE 'CREATE TABLE IF NOT EXISTS flats( \n" +
                    "id int PRIMARY KEY CHECK (id > 0), \n" +
                    "name varchar(100) NOT NULL, \n" +
                    "is_bought boolean NOT NULL, \n" +
                    "rooms int CHECK (rooms > 0), \n" +
                    "price float \n" +
                    ")'; \n" +
                    "END; \n" +
                    "$$ LANGUAGE plpgsql;");
        }
        catch (Exception e){
            System.out.println(e);
        };

        try {
            stm.executeUpdate("CREATE OR REPLACE FUNCTION ViewTableContent()\n" +
                    "RETURNS TABLE (s_id INTEGER, s_name VARCHAR(100), s_is_bought BOOLEAN, s_rooms INTEGER, s_price FLOAT)\n" +
                    "AS \n" +
                    "$$\n" +
                    "BEGIN \n" +
                    "RETURN QUERY SELECT id, name, is_bought, rooms, price FROM flats;\n" +
                    "END; \n" +
                    "$$ LANGUAGE plpgsql;");
        }
        catch (Exception e){
            System.out.println(e);
        };

        try {
            stm.executeUpdate("CREATE OR REPLACE FUNCTION TruncateTable() \n" +
                    "RETURNS void \n" +
                    "AS \n" +
                    "$$ \n" +
                    "BEGIN \n" +
                    "EXECUTE 'TRUNCATE flats'; \n" +
                    "END; \n" +
                    "$$ LANGUAGE plpgsql;");
        }
        catch (Exception e){
            System.out.println(e);
        };

        try {
            stm.executeUpdate("CREATE OR REPLACE FUNCTION InsertData(\n" +
                    "id_value int,\n" +
                    "name_value varchar(100),\n" +
                    "is_bought_flag bool,\n" +
                    "rooms_value int,\n" +
                    "price_value float\n" +
                    ")\n" +
                    "RETURNS void \n" +
                    "AS\n" +
                    "$$\n" +
                    "BEGIN\n" +
                    "EXECUTE 'INSERT INTO flats (id, name, is_bought, rooms, price) VALUES ($1, $2, $3, $4, $5)' \n" +
                    "USING id_value, name_value, is_bought_flag, rooms_value, price_value;\n" +
                    "END;\n" +
                    "$$ LANGUAGE plpgsql;");
        }
        catch (Exception e){
            System.out.println(e);
        };

        try {
            stm.executeUpdate("CREATE OR REPLACE FUNCTION UpdateTuple(id_value int, name_value varchar(100),\n" +
                    "is_bought_flag bool, rooms_value int, price_value float) \n" +
                    "RETURNS void \n" +
                    "AS \n" +
                    "$$ \n" +
                    "BEGIN \n" +
                    "UPDATE flats SET name = name_value, \n" +
                    "is_bought = is_bought_flag, rooms = rooms_value, price = price_value \n" +
                    "WHERE id = id_value;\n" +
                    "END;\n" +
                    "$$ LANGUAGE plpgsql;");
        }
        catch (Exception e){
            System.out.println(e);
        };

        try {
            stm.executeUpdate("CREATE OR REPLACE FUNCTION SearchByName(name_value varchar(100))\n" +
                    "RETURNS TABLE (s_id INTEGER, s_name VARCHAR(100), s_is_bought BOOLEAN, s_rooms INTEGER, s_price FLOAT)\n" +
                    "AS \n" +
                    "$$\n" +
                    "BEGIN \n" +
                    "RETURN QUERY SELECT id, name, is_bought, rooms, price FROM flats WHERE name = name_value;\n" +
                    "END; \n" +
                    "$$ LANGUAGE plpgsql;");
        }
        catch (Exception e){
            System.out.println(e);
        };

        try {
            stm.executeUpdate("CREATE OR REPLACE FUNCTION DeleteByName(name_value varchar(100)) \n" +
                    "RETURNS void \n" +
                    "AS \n" +
                    "$$ \n" +
                    "BEGIN \n" +
                    "EXECUTE FORMAT('DELETE FROM flats WHERE name = %L', name_value); \n" +
                    "END; \n" +
                    "$$ LANGUAGE plpgsql;");
        }
        catch (Exception e){
            System.out.println(e);
        };
    }
}
