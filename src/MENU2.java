import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MENU2 extends JFrame{
    private JTextField idUSR;
    private JButton READButton;
    private JTextPane textPane1;
    private JPanel USUARIO;


    public Connection conexion() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/libroconnect";
        String user = "root";
        String password = "123456";

        return DriverManager.getConnection(url, user, password);

    }

    public void leer() throws SQLException{
        String idRead = idUSR.getText();

        Connection connex= conexion();

        String query = "SELECT * FROM libros WHERE Id_Libro = ?;";

        PreparedStatement pstm = connex.prepareStatement(query);
        pstm.setString(1, idRead);

        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            String id = rs.getString("Id_Libro");
            String name = rs.getString("Nombre_Libro");
            String autor = rs.getString("Autor");

            textPane1.setText("ID: "+id+"\n"+"Name: "+name+"\n"+"Autor: "+autor);

        }

        pstm.close();
        connex.close();

    }


    public MENU2() throws SQLException {
        setSize(1000,1000);
        setContentPane(USUARIO);

        READButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    leer();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }



}
