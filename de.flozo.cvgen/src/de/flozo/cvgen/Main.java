package de.flozo.cvgen;

import de.flozo.db.Datasource;

public class Main {

    public static void main(String[] args) {

        Datasource.getInstance().open();
        try {

            System.out.println(Datasource.getInstance().queryLengthUnitById(3));
            System.out.println(Datasource.getInstance().queryNamedLengthById(2));
        } finally {
            Datasource.getInstance().close();
        }

    }
}
