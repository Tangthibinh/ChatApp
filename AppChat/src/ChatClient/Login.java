package ChatClient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {


    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl ="jdbc:sqlserver://DESKTOP-29FP0GC:3306;databaseName=Login";
            String userName = "sa";
            String password = "123456789";
            conn = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField Emailtext;
    private JPasswordField passtext;
    private JTextField usertext;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public boolean isValidPassword(String password) {
        return password.length() >= 8;
    }
    public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }


    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("LOGIN");
        lblNewLabel.setBounds(10, 10, 426, 31);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 27));
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Email");
        lblNewLabel_1.setForeground(SystemColor.desktop);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblNewLabel_1.setBounds(34, 127, 78, 25);
        contentPane.add(lblNewLabel_1);

        Emailtext = new JTextField();
        Emailtext.setToolTipText("");
        Emailtext.setBounds(174, 126, 235, 31);
        contentPane.add(Emailtext);
        Emailtext.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Password");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblNewLabel_2.setBounds(34, 168, 98, 24);
        contentPane.add(lblNewLabel_2);

        passtext = new JPasswordField();
        passtext.setBounds(174, 167, 235, 31);
        contentPane.add(passtext);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (!isValidEmail(Emailtext.getText())) {
                     JOptionPane.showMessageDialog(Login.this, "Please enter a valid email address");
                     Emailtext.setText("");
                 } else if (!isValidPassword(new String(passtext.getPassword()))) {
                     JOptionPane.showMessageDialog(Login.this, "Password must be at least 8 characters long");
                     passtext.setText(""); 
                 } else { 
                	 String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                     String connectionUrl = "jdbc:sqlserver://DESKTOP-29FP0GC:3306;databaseName=Login";
                     String user = "sa";
                     String password = "123456789";
                     Statement st=null;
                     ResultSet rs=null;
                    try {
                    	Class.forName(driver);
                    	Connection conn = DriverManager.getConnection(connectionUrl, user , password);
					
						String query = "SELECT * FROM Table_1 WHERE user=? AND email = ? AND pass=?";
						PreparedStatement ps  = conn.prepareStatement(query);
						ps.setString(1, usertext.getText());
						ps.setString(2, Emailtext.getText());
						ps.setString(3, new String(passtext.getPassword()));
					    rs = ps.executeQuery();
						if(rs.next()) {
							dispose();
							EventQueue.invokeLater(new Runnable( ) {
								public void run() {
									try {
										ClientChatter frame = new ClientChatter();
										frame.setVisible(true);
									} catch (Exception e) {}
								}
							});
						} else {
							JOptionPane.showMessageDialog(Login.this, "Login failed, please check your information");
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}
                 }
            }
            
        });
        btnNewButton.setBounds(24, 222, 98, 31);
        contentPane.add(btnNewButton);

        JButton btnNewButton_2 = new JButton("New account");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usertext.getText().isEmpty() || Emailtext.getText().isEmpty() || passtext.getPassword().length == 0) {
                	 JOptionPane.showMessageDialog(Login.this, "Please fill in all fields"); }
                else if (!isValidEmail(Emailtext.getText())) {
                         JOptionPane.showMessageDialog(Login.this, "Please enter a valid email address");
                         Emailtext.setText(""); 
                     } else if (!isValidPassword(new String(passtext.getPassword()))) {
                         JOptionPane.showMessageDialog(Login.this, "Password must be at least 8 characters long");
                         passtext.setText("");
                } else {
                    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                    String url = "jdbc:sqlserver://DESKTOP-29FP0GC:3306;databaseName=Login";
                    String user = "sa";
                    String password = "123456789";
                    int dk = JOptionPane.showConfirmDialog(Login.this, "Do you want to create a new account?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (dk == JOptionPane.YES_OPTION) {
                    	 try {
                             Class.forName(driver);
                             Connection conn = DriverManager.getConnection(url, user, password);

                             
                             String checkSql = "SELECT * FROM Table_1 WHERE user = ?";
                             PreparedStatement checkPs = conn.prepareStatement(checkSql);
                             checkPs.setString(1, usertext.getText());

                             if (checkPs.executeQuery().next()) {
                                 JOptionPane.showMessageDialog(Login.this, "User already exists");
                             } else {
                               
                                 String insertSql = "INSERT INTO Table_1 VALUES (?,?,?)";
                                 PreparedStatement insertPs = conn.prepareStatement(insertSql);
                                 insertPs.setString(1, usertext.getText());
                                 insertPs.setString(2, Emailtext.getText());
                                 String passwordText = new String(passtext.getPassword());
                                 insertPs.setString(3, passwordText);

                                 int n = insertPs.executeUpdate();

                                 if (n != 0) {
                                     JOptionPane.showMessageDialog(Login.this, "Account created successfully");
                                 } else {
                                     JOptionPane.showMessageDialog(Login.this, "Failed to create account");
                                 }
                             }
                        } catch (Exception e2) {
                            if (e2 instanceof SQLException) {
                                SQLException sqlException = (SQLException) e2;
                                if (sqlException.getSQLState().equals("23000") && sqlException.getErrorCode() == 2627) {
                                    JOptionPane.showMessageDialog(Login.this, "User already exists");
                                } else {
                                    e2.printStackTrace();
                                }
                            } else {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        btnNewButton_2.setBounds(168, 222, 109, 31);
        contentPane.add(btnNewButton_2);

        JLabel lblNewLabel_1_1 = new JLabel("User name");
        lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1_1.setForeground(SystemColor.desktop);
        lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblNewLabel_1_1.setBounds(34, 79, 98, 25);
        contentPane.add(lblNewLabel_1_1);

        usertext = new JTextField();
        usertext.setToolTipText("");
        usertext.setColumns(10);
        usertext.setBounds(174, 78, 235, 31);
        contentPane.add(usertext);
        
        JButton btnNewButton_3 = new JButton("Refresh");
        btnNewButton_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		usertext.setText("");
        		Emailtext.setText("");
        		passtext.setText("");
        	}
        });
        btnNewButton_3.setBounds(317, 222, 109, 31);
        contentPane.add(btnNewButton_3);
    }
}
 