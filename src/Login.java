import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    private JPanel login;
    private JTextField userT;
    private JPasswordField passT;
    private JButton logingB;

    public Connection conexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/libroconnect";
        String user = "root";
        String password = "123456";

        return DriverManager.getConnection(url, user, password);
    }


    public void logeo() throws SQLException {
        String user =userT.getText();
        String password = passT.getText();
        String tipo;

        Connection conex = conexion();

        String query = "SELECT * FROM Usuarios WHERE usuario = ? AND contraseña = ?";

        PreparedStatement pstm = conex.prepareStatement(query);
        pstm.setString(1, user);
        pstm.setString(2, password);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            user = rs.getString("usuario");
            password = rs.getString("contraseña");
            tipo=rs.getString("tipo");

            if(tipo.equals("administrador")) {
                JFrame frame = new JFrame("MENU");
                MENU menu = new MENU();
                frame.setContentPane(menu.getContentPane());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                dispose();
            }

            if(tipo.equals("usuario")) {
                JFrame frame = new JFrame("MENU");
                MENU2 menu2 = new MENU2();
                frame.setContentPane(menu2.getContentPane());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                dispose();
            }


        } else {
            JOptionPane.showMessageDialog(null, "USUARIO O CONTRASEÑA INCORRECTA");
        }

    }

    public Login() {
        setSize(300,300);
        setContentPane(login);


        logingB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    logeo();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }




}
