package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;



public class view {
	
	String versao= "1.2";
	
	public JFrame frame;
	private JTextField selecionado;
	private JTextField saida;
	private JTextField codevento;
	private JTextField data;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					view window = new view();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public view() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		JFileChooser fc = new JFileChooser();
		frame.setBounds(100, 100, 834, 484);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Fechar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System. exit(0);
			}
		});
		btnNewButton.setBounds(648, 360, 115, 29);
		frame.getContentPane().add(btnNewButton);
		
		selecionado = new JTextField();
		selecionado.addMouseListener(new MouseAdapter() {

		});
		selecionado.setBounds(44, 92, 549, 26);
		frame.getContentPane().add(selecionado);
		selecionado.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Selecione o Arquivo");
		btnNewButton_1.setBounds(608, 91, 186, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblCaminhoEArquivo = new JLabel("Caminho e arquivo origem");
		lblCaminhoEArquivo.setBounds(44, 70, 238, 20);
		frame.getContentPane().add(lblCaminhoEArquivo);
		
		JLabel lblNomeDoArquivo = new JLabel("Nome do arquivo destino");
		lblNomeDoArquivo.setBounds(44, 158, 226, 20);
		frame.getContentPane().add(lblNomeDoArquivo);
		
		saida = new JTextField();
		saida.setBounds(44, 182, 549, 26);
		frame.getContentPane().add(saida);
		saida.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Gerador de arquivo UNIMED x folha RMLabore");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(83, 16, 534, 30);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Codigo do Evento (4 digitos)");
		lblNewLabel_1.setBounds(44, 240, 210, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
	
		JLabel lblNewLabel_3 = new JLabel("By Carlos Magno, SEBRAE-MA, UTIC, Vers\u00E3o "+versao);
		lblNewLabel_3.setBounds(15, 392, 381, 20);
		frame.getContentPane().add(lblNewLabel_3);
		
		codevento = new JTextField();
		codevento.setBounds(44, 276, 210, 26);
		frame.getContentPane().add(codevento);
		codevento.setColumns(10);
		codevento.setText("7003");
		
		JLabel lblNewLabel_2 = new JLabel("Data (ddMMyyyy)");
		lblNewLabel_2.setBounds(296, 240, 180, 20);
		frame.getContentPane().add(lblNewLabel_2);
		
		
		data = new JTextField();
		data.setBounds(296, 276, 180, 26);
		frame.getContentPane().add(data);
		data.setColumns(10);
		data.setText(dataHoje());
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.showOpenDialog(frame);
				File f = fc.getSelectedFile();
				String caminho = f.getPath();
				String[] ar= caminho.split("\\.");
				
				selecionado.setText(caminho);
				
		
				saida.setText(ar[0]+"MOV_RM.CSV");
				
			}
		});
		
		JButton btnNewButton_2 = new JButton("Salvar");
		btnNewButton_2.setBounds(608, 181, 186, 29);
		frame.getContentPane().add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Arquivo arquivo = new Arquivo();
				Scanner scanner;
				try {
					scanner = new Scanner(fc.getSelectedFile());
					String resultado = arquivo.converte(scanner, saida.getText(),data.getText(),codevento.getText());
					JOptionPane.showMessageDialog(fc, "Operação concluída com sucesso!, "+resultado);
					
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(fc, "Operação concluída com erro "+ e1.getMessage());
				}
				
				
			}


		});
	
		
	}
	
	private String dataHoje() { 
		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("ddMMyyyy").format(dataHoraAtual); 
		return data; // carrega a data atual para o formato solicitado pela  
	}
	
	private class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "gerador de arquivo para RH");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
