import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MENU extends JFrame{

    private JPanel menupanel;
    private JTabbedPane tabbedPane1;
    private JPanel create;
    private JPanel read;
    private JPanel update;
    private JPanel delete;
    private JTextField idC;
    private JTextField nameC;
    private JTextField autorC;
    private JButton CREATEButton;
    private JTextField readR;
    private JTextPane textPane1;
    private JTextField nameU;
    private JTextField idU;
    private JTextField autorU;
    private JTextField idD;
    private JButton DELETEButton;
    private JButton UPDATEButton;
    private JButton READButton;

    public Connection conexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/libroconnect";
        String user = "root";
        String password = "123456";

        return DriverManager.getConnection(url, user, password);
    }

    public void insertar() throws SQLException{
        String idCreate = idC.getText();
        String nameCreate = nameC.getText();
        String autorCreate = autorC.getText();

        Connection connex = conexion();

        String query = "INSERT INTO libros (Id_Libro,Nombre_Libro,Autor) VALUES (?,?,?);";

        PreparedStatement pstm = connex.prepareStatement(query);
        pstm.setString(1,idCreate);
        pstm.setString(2,nameCreate);
        pstm.setString(3,autorCreate);

        int rowsAffected = pstm.executeUpdate();

        if(rowsAffected >0){
            JOptionPane.showMessageDialog(null,"Created!");
        }

        pstm.close();
        connex.close();

    }

    public void leer() throws SQLException{
        String idRead = readR.getText();

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

    public void actualizar() throws SQLException{
        String id = idU.getText();
        String nameUpdate = nameU.getText();
        String autorUpdate = autorU.getText();


        Connection connex = conexion();
        String query = "UPDATE libros SET Nombre_Libro = ?, Autor = ? WHERE Id_Libro = ?;";

        PreparedStatement pstm = connex.prepareStatement(query);
        pstm.setString(1, nameUpdate);
        pstm.setString(2, autorUpdate);
        pstm.setString(3,id);

        pstm.executeUpdate();

        JOptionPane.showMessageDialog(null,"Updated!");

        pstm.close();
        connex.close();

    }


    public void borrar() throws SQLException{
        String id = idD.getText();

        Connection connex = conexion();

        String query = "DELETE FROM libros WHERE Id_Libro = ?;";

        PreparedStatement pstm = connex.prepareStatement(query);
        pstm.setString(1,id);

        pstm.executeUpdate();

        JOptionPane.showMessageDialog(null,"Deleted!");


    }


    public MENU(){
        setSize(500,500);
        setContentPane(menupanel);

        CREATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    insertar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


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


        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    actualizar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    borrar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}
