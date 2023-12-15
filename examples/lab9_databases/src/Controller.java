import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;

import java.sql.*;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = model.getConnection();
                    String usernameString = view.getUsername();
                    String passwordString = view.getPassword();

                    HashMap<Integer, String> possibleTables = new HashMap<>();
                    possibleTables.put(1, "superadministrator");
                    possibleTables.put(2, "administrator");
                    possibleTables.put(3, "profesor");
                    possibleTables.put(4, "student");
                    boolean found = false;

                    for(int idRol : new int[]{1, 2, 3, 4})
                    {
                        String table = possibleTables.get(idRol);
                        boolean connected = Model.validateUser(connection, table, usernameString, passwordString, idRol);
                        if(connected)
                        {
                            System.out.println(usernameString + " has connected as " + table + "!");
                            found = true;
                            break;
                        }
                    }

                    if(!found)
                    {
                        System.out.println(usernameString + " does not exist!");
                    }
                    else
                    {
                        view.showLoggedInView(usernameString);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    view.resetFields();
                }
            }
        });
    }
}

