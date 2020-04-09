package chat.soumyadip.project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ClientEnd {

	public JFrame frame;
	private JTextField textField;
    private static JTextArea textArea;
    private static Socket con;
    DataInputStream input;
    DataOutputStream output;
    private JScrollPane scrollPane;
  
	public static void main(String[] args) throws UnknownHostException, IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientEnd window = new ClientEnd();
					window.frame.setVisible(true);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		con = new Socket("127.0.0.1", 8080);
		 while (true) {
			try {
				
				DataInputStream input = new DataInputStream(con.getInputStream());
				String string = input.readUTF();
				textArea.setText(textArea.getText() + "\n" + "Server: " + string);
			} catch (Exception ev) {
				 textArea.setText(textArea.getText()+" \n" +"Network issues ");

				 try {
						Thread.sleep(2000);
						System.exit(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			
				

		}
		
	}

	
	public ClientEnd() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(UIManager.getColor("MenuBar.highlight"));
		frame.setBounds(100, 100, 605, 378);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Soumyadip Networking Project : Client Chat");
		
		textField = new JTextField();
		textField.setFont(new Font("Lato Medium", Font.PLAIN, 22));
		textField.setForeground(Color.ORANGE);
		textField.setBackground(Color.DARK_GRAY);
		textField.setBounds(12, 45, 344, 38);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please write some text !");
				}else  {
					textArea.setText(textArea.getText() + "\n" + "Client : " + textField.getText());
					try {
						DataOutputStream output = new DataOutputStream(con.getOutputStream());
						output.writeUTF(textField.getText());
					} catch (IOException e1) {
						textArea.setText(textArea.getText() + "\n " + " Network issues");
						try {
							Thread.sleep(2000);
							System.exit(0);
						} catch (InterruptedException e2) {
							e2.printStackTrace();
						}

					}
					textField.setText("");
				}
			
			
			}
					
		});
		btnNewButton.setFont(new Font("Georgia", Font.BOLD, 22));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBounds(398, 45, 164, 38);
		frame.getContentPane().add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 149, 557, 157);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
		textArea.setForeground(new Color(255, 255, 255));
		textArea.setBackground(new Color(0, 128, 128));
	}
}
