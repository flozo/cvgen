package de.flozo.cvgen;

import de.flozo.common.content.Address;
import de.flozo.db.AddressDAO;
import de.flozo.db.AddressDAOImpl;
import de.flozo.db.Datasource2;

public class Main {

    public static void main(String[] args) {


        try {

            AddressDAO addressDAO = new AddressDAOImpl();

            System.out.println(addressDAO.get(1));


            for (Address address: addressDAO.getAll()) {
                System.out.println(address);
            }

            Address address = new Address(0,"new person", "Prof.","John", "", "Smith", "Main street", "1", "23570", "City", "Country",
                    "1357924680", "09876", "address@mail.com", "www.test.org");
            addressDAO.add(address);
            Address address1 = addressDAO.get(8);
            address1.setSecondName("Mark");
            addressDAO.update(address1);

        } finally {
            Datasource2.INSTANCE.closeConnection();
        }

    }
}
